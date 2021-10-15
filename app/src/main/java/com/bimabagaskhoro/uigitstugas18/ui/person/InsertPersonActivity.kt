package com.bimabagaskhoro.uigitstugas18.ui.person

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityInsertPersonBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseGambar
import com.bimabagaskhoro.uigitstugas18.model.person.ResponsePerson
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class InsertPersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertPersonBinding
    private var imageUri: Uri? = null
    private var imageName: String? = ""
    companion object{
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.add)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonUpload.setOnClickListener{
                pickImage()
            }
        }
    }

    private fun pickImage() {
        if(EasyPermissions.hasPermissions(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
        }else{
            // Menampilkan permission request saat belum mendapat permission dari user
            EasyPermissions.requestPermissions(
                this,
                "This application need your permission to access photo gallery.",
                991,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_IMAGE_PICKER ->{
                    imageUri = data?.data
                    binding.imgUser.setImageURI(imageUri)
                    binding.buttonSave.setOnClickListener {
                        insertData(imageUri!!)
                    }
                }
            }
        }
    }

    private fun insertData(contentURI: Uri) {
        val filePath = getPathFromURI(this, contentURI)
        val file = File(filePath)
        val mFile = RequestBody.create("multipart".toMediaTypeOrNull(), file)
        imageName = file.name
        val body: MultipartBody.Part = createFormData("file", imageName, mFile)

        RetrofitClient().apiInstance().uploadImage(body)
                .enqueue(object : Callback<ResponseGambar>{
                    override fun onResponse(call: Call<ResponseGambar>, response: Response<ResponseGambar>) {
                        if (response!!.isSuccessful){
                            if (response.body()?.status == 1){
                                RetrofitClient().apiInstance().insertPerson(
                                        binding.edtIdPerson.text.toString().trim(),
                                        binding.edtNamePerson.text.toString().trim(),
                                        binding.edtEmailPerson.text.toString().trim(),
                                        binding.edtTittlePerson.text.toString().trim(),
                                        imageName.toString().trim(),
                                        "insert_person"
                                ).enqueue(object : Callback<ResponseStatusPerson>{
                                    override fun onResponse(call: Call<ResponseStatusPerson>, response: Response<ResponseStatusPerson>) {
                                        if (response!!.isSuccessful){
                                            if (response.body()?.status == 1){
                                                binding.edtIdPerson.setText("")
                                                binding.edtNamePerson.setText("")
                                                binding.edtEmailPerson.setText("")
                                                binding.edtTittlePerson.setText("")
                                                Toast.makeText(this@InsertPersonActivity, "Respon sukses", Toast.LENGTH_SHORT).show()
                                                finish()
                                            }
                                        } else {
                                            Toast.makeText(this@InsertPersonActivity, "respon gagal", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<ResponseStatusPerson>, t: Throwable) {
                                        Toast.makeText(this@InsertPersonActivity, "respon gagal $t", Toast.LENGTH_SHORT).show()
                                    }

                                })

                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseGambar>, t: Throwable) {
                        Toast.makeText(this@InsertPersonActivity, "respon gagal $t", Toast.LENGTH_SHORT).show()
                    }

                })

    }

    private fun getPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, filePathColumn, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e(TAG, "getPathFromURI Exception : ${e.toString()}")
            return ""
        } finally {
            cursor?.close()
        }
    }

}