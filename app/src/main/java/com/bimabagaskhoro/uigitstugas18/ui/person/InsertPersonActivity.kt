package com.bimabagaskhoro.uigitstugas18.ui.person

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityInsertPersonBinding
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class InsertPersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertPersonBinding
    private val pathFoto: String? = ""
    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1000
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.add)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonSave.setOnClickListener{
                insertData()
            }
            buttonUpload.setOnClickListener{
                uploadGambar()
            }
        }
    }

    private fun insertData() {
        if ("" == pathFoto) {
            Toast.makeText(this, "Masukkan Foto dengan Benar", Toast.LENGTH_SHORT).show()
        } else {
            val file = File(pathFoto)
            val requestFile = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    file
            )
            val part = MultipartBody.Part.createFormData("gambar", file.toString(), requestFile)
            val edtId: EditText = findViewById(R.id.edt_id_person)
            val edtName: EditText = findViewById(R.id.edt_name_person)
            val edtEmail: EditText = findViewById(R.id.edt_email_person)
            val edtTittle: EditText = findViewById(R.id.edt_tittle_person)
            RetrofitClient().apiInstance().insertPerson(
                    edtId.text.toString().trim(),
                    edtName.text.toString().trim(),
                    edtEmail.text.toString().trim(),
                    edtTittle.text.toString().trim(),
                    part,
                    "insert_person"
            ).enqueue(object : Callback<ResponseStatusPerson>{
                override fun onResponse(call: Call<ResponseStatusPerson>, response: Response<ResponseStatusPerson>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@InsertPersonActivity, "Behasil Tambah Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseStatusPerson>, t: Throwable) {
                    Toast.makeText(this@InsertPersonActivity, "Gagal Tambah Data : $t", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    private fun uploadGambar() {
        if (ActivityCompat.checkSelfPermission(
                        this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            intent.putExtra("crop", "true")
            intent.putExtra("scale", "true")
            intent.putExtra("aspectX", "16")
            intent.putExtra("aspectY", "9")
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView : ImageView = findViewById(R.id.img_user)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                return
            }
            val uri = data?.data
            if (uri != null) {
                val imageFile = uriToImageFile(uri)
                val bitmapImage = BitmapFactory.decodeFile(imageFile.toString())
                imageView.setImageBitmap(bitmapImage)
            }
            if (uri != null) {
                val imageBitmap = uriToBitmap(uri)
                imageView.setImageBitmap(imageBitmap)
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadGambar()
                }
            }
        }
    }

    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                var urlImage = filePath
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }
    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

    }
}