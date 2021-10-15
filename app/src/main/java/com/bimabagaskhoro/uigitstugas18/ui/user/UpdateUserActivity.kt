package com.bimabagaskhoro.uigitstugas18.ui.user

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdateUser2Binding
import com.bimabagaskhoro.uigitstugas18.model.ResponseGambar
import com.bimabagaskhoro.uigitstugas18.model.login.DataItem
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseStatusLogin
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
class UpdateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUser2Binding
    private var imageUri: Uri? = null
    private var imageName: String? = ""
    companion object {
        const val EXTRA_LINK = "http://192.168.43.225/tugasGitsApi/uploadgambar/"
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUser2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.update)
        val intent = intent
        val idUser = intent.getStringExtra("id")
        val name = intent.getStringExtra("nama")
        val email = intent.getStringExtra("email")
        val passwd = intent.getStringExtra("passwd")
        val idDevice = intent.getStringExtra("id_device")
        binding.apply {
            edtIdUpdateUser.setText(idUser)
            edtNameUpdateUser.setText(name)
            editTextTextEmailAddressUpdateUser.setText(email)
            editTextTextPasswordUpdateUser.setText(passwd)
            tvIdDevice.text = idDevice
            getData()
            fabUpload.setOnClickListener{
                pickImage()
            }
            buttonLoginBiometric.setOnClickListener {
                addBiometric()
            }
        }
    }

    private fun getData() {
        binding.edtNameUpdateUser.text
        val tvId: TextView = findViewById(R.id.edt_id_update_user)
        val tvName: TextView = findViewById(R.id.tv_name_update_user)
        val tvPrice: TextView = findViewById(R.id.tv_email_update_user)
        val imgPath: ImageView = findViewById(R.id.img_update_user_lg)

        val item = intent.getParcelableExtra<DataItem>(EXTRA_DATA) as DataItem
        tvId.text = item.id
        tvName.text = item.nama
        tvPrice.text = item.email
        Glide.with(this)
            .load(EXTRA_LINK +item.avatar)
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
                    binding.imgUpdateUserLg.setImageURI(imageUri)
                    binding.button.setOnClickListener {
                        updateData(imageUri!!)
                    }
                }
            }
        }
    }

    private fun updateData(imageUri: Uri) {
        val filePath = getPathFromURI(this, imageUri)
        val file = File(filePath)
        val mFile = RequestBody.create("multipart".toMediaTypeOrNull(), file)
        imageName = file.name
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", imageName, mFile)

        val edtId: EditText = findViewById(R.id.edt_id_update_user)
        val edtName: EditText = findViewById(R.id.edt_name_update_user)
        val edtEmail: EditText = findViewById(R.id.editTextTextEmailAddress_update_user)
        val edtPassword: EditText = findViewById(R.id.editTextTextPassword_update_user)

        RetrofitClient().apiInstance().uploadImage(body)
            .enqueue(object : Callback<ResponseGambar>{
                override fun onResponse(
                    call: Call<ResponseGambar>,
                    response: Response<ResponseGambar>,
                ) {
                    if (response.body()?.status == 1){
                        RetrofitClient().apiInstance().updateUser(
                            edtName.text.toString().trim(),
                            edtEmail.text.toString().trim(),
                            edtPassword.text.toString().trim(),
                            imageName.toString(),
                            edtId.text.toString().trim(),
                        ).enqueue(object : Callback<ResponseStatusLogin>{
                            override fun onResponse(
                                call: Call<ResponseStatusLogin>,
                                response: Response<ResponseStatusLogin>,
                            ) {
                                if (response!!.isSuccessful){
                                    if (response.body()?.status == 1){
                                        edtName.setText("")
                                        edtEmail.setText("")
                                        edtPassword.setText("")
                                        edtId.setText("")
                                        Toast.makeText(this@UpdateUserActivity, "Respon sukses", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ResponseStatusLogin>, t: Throwable) {
                                Toast.makeText(this@UpdateUserActivity, "Respon gagal", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<ResponseGambar>, t: Throwable) {
                    Toast.makeText(this@UpdateUserActivity, "Respon gagal", Toast.LENGTH_SHORT).show()
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


    @SuppressLint("HardwareIds", "SetTextI18n")
    private fun addBiometric() {
        val edtId: EditText = findViewById(R.id.edt_id_update_user)
        val idDevice: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val textView: TextView = findViewById(R.id.tv_id_device)
        textView.text = idDevice
        RetrofitClient().apiInstance().updateDeviceId(
            idDevice,
            edtId.text.toString().trim(),
        ).enqueue(object : Callback<ResponseGambar>{
            override fun onResponse(
                call: Call<ResponseGambar>,
                response: Response<ResponseGambar>,
            ) {
                if (response!!.isSuccessful) {
                    Toast.makeText(this@UpdateUserActivity, " update sukses", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseGambar>, t: Throwable) {
                Toast.makeText(this@UpdateUserActivity, "update id gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }


}