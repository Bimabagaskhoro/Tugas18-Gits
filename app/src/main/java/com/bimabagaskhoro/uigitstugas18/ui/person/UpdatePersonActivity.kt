package com.bimabagaskhoro.uigitstugas18.ui.person

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdatePersonBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseGambar
import com.bimabagaskhoro.uigitstugas18.model.person.DataItemPerson
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class UpdatePersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePersonBinding
    private var imageUri: Uri? = null
    private var imageName: String? = ""
    companion object{
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_LINK = "http://192.168.43.225/tugasGitsApi/uploadgambar/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.update)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonUpload.setOnClickListener{
                pickImage()
            }
        }

        val intent = intent
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("nama")
        val email = intent.getStringExtra("email")
        val tittle = intent.getStringExtra("tittle")
        binding.apply {
            edtIdPersonUpdate.setText(id)
            edtNamePersonUpdate.setText(name)
            edtEmailPersonUpdate.setText(email)
            edtTittlePersonUpdate.setText(tittle)
        }

        getData()
    }

    private fun getData() {
        val imgPath: ImageView = findViewById(R.id.img_update)
        val item = intent.getParcelableExtra<DataItemPerson>(EXTRA_DATA) as DataItemPerson
        Glide.with(this)
            .load(EXTRA_LINK +item.gambar)
            .into(imgPath)
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
                    binding.imgUpdate.setImageURI(imageUri)
                    binding.buttonSave.setOnClickListener {
                        insertData(imageUri!!)
                    }
                }
            }
        }
    }

    private fun insertData(imageUri: Uri) {
        val filePath = getPathFromURI(this, imageUri)
        val file = File(filePath)
        imageName = file.name
        val mFile = RequestBody.create("multipart".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", imageName, mFile)

        RetrofitClient().apiInstance().uploadImage(body)
                .enqueue(object : Callback<ResponseGambar>{
                    override fun onResponse(call: Call<ResponseGambar>, response: Response<ResponseGambar>) {
                        if (response!!.isSuccessful){
                            if (response.body()?.status == 1){
                                RetrofitClient().apiInstance().updatePerson(
                                        binding.edtNamePersonUpdate.text.toString().trim(),
                                        binding.edtEmailPersonUpdate.text.toString().trim(),
                                        binding.edtTittlePersonUpdate.text.toString().trim(),
                                        imageName.toString().trim(),
                                        binding.edtIdPersonUpdate.text.toString().trim(),
                                ).enqueue(object : Callback<ResponseStatusPerson>{
                                    override fun onResponse(call: Call<ResponseStatusPerson>, response: Response<ResponseStatusPerson>) {
                                        if (response!!.isSuccessful){
                                            if (response.body()?.status == 1){
                                                binding.edtNamePersonUpdate.setText("")
                                                binding.edtEmailPersonUpdate.setText("")
                                                binding.edtTittlePersonUpdate.setText("")
                                                binding.edtIdPersonUpdate.setText("")
                                                Toast.makeText(this@UpdatePersonActivity, "Respon sukses", Toast.LENGTH_SHORT).show()
                                                finish()
                                            }
                                        }else {
                                            Toast.makeText(this@UpdatePersonActivity, "respon gagal", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<ResponseStatusPerson>, t: Throwable) {
                                        Toast.makeText(this@UpdatePersonActivity, "respon gagal", Toast.LENGTH_SHORT).show()
                                    }

                                })
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseGambar>, t: Throwable) {
                        Toast.makeText(this@UpdatePersonActivity, "respon gagal", Toast.LENGTH_SHORT).show()
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
            Log.e(ContentValues.TAG, "getPathFromURI Exception : ${e.toString()}")
            return ""
        } finally {
            cursor?.close()
        }
    }
}