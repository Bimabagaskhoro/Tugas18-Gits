package com.bimabagaskhoro.uigitstugas18.ui.user

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
import android.view.View
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityRegisterBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseGambar
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseStatusLogin
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bimabagaskhoro.uigitstugas18.ui.person.InsertPersonActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private var imageUri: Uri? = null
    private var imageName: String? = ""
    companion object{
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvLogin.setOnClickListener(this@RegisterActivity)
            fabUpload.setOnClickListener{
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
                    binding.imageView.setImageURI(imageUri)
                    binding.buttonRegister.setOnClickListener {
                        insertData(imageUri!!)
                    }
                }
            }
        }
    }

    private fun insertData(imageUri: Uri) {
        val filePath = getPathFromURI(this, imageUri)
        val file = File(filePath)
        val mFile = RequestBody.create("multipart".toMediaTypeOrNull(), file)
        imageName = file.name
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", imageName, mFile)

        RetrofitClient().apiInstance().uploadImage(body)
                .enqueue(object : Callback<ResponseGambar>{
                    override fun onResponse(call: Call<ResponseGambar>, response: Response<ResponseGambar>) {
                       if (response!!.isSuccessful){
                           if (response.body()?.status == 1){
                               RetrofitClient().apiInstance().regist(
                                       "",
                                        binding.edtName.text.toString().trim(),
                                        binding.editTextTextEmailAddress.text.toString().trim(),
                                        binding.editTextTextPassword.text.toString().trim(),
                                        imageName.toString().trim(),
                                       "insert_user")
                                       .enqueue(object : Callback<ResponseStatusLogin>{
                                           override fun onResponse(call: Call<ResponseStatusLogin>, responseStatus: Response<ResponseStatusLogin>) {
                                               if (responseStatus!!.isSuccessful){
                                                   if (responseStatus.body()?.status == 1){
                                                       binding.edtName.setText("")
                                                       binding.editTextTextEmailAddress.setText("")
                                                       binding.editTextTextPassword.setText("")
                                                       Toast.makeText(this@RegisterActivity, "Respon sukses", Toast.LENGTH_SHORT).show()
                                                       finish()
                                                   }
                                               } else {
                                                   Toast.makeText(this@RegisterActivity, "respon gagal", Toast.LENGTH_SHORT).show()
                                               }
                                           }

                                           override fun onFailure(call: Call<ResponseStatusLogin>, t: Throwable) {
                                               Toast.makeText(this@RegisterActivity, "respon gagal $t", Toast.LENGTH_SHORT).show()
                                           }

                                       })
                           }
                       }
                    }

                    override fun onFailure(call: Call<ResponseGambar>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "respon gagal $t", Toast.LENGTH_SHORT).show()
                    }

                })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_login -> {
                val tvLogin = Intent(this, LoginActivity::class.java)
                startActivity(tvLogin)
            }
        }
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
            Log.e(ContentValues.TAG, "Exception : ${e.toString()}")
            return ""
        } finally {
            cursor?.close()
        }
    }
}