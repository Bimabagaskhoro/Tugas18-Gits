package com.bimabagaskhoro.uigitstugas18.ui.person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityPersonBinding
import com.bimabagaskhoro.uigitstugas18.model.person.DataItemPerson

class DetailPersonActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_person)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.detail)
        actionbar.setDisplayHomeAsUpEnabled(true)
        getDetail()
    }

    private fun getDetail() {
        val tvIdPerson: TextView = findViewById(R.id.tv_id_detail_person)
        val tvNamePerson: TextView = findViewById(R.id.tv_name_detail_person)
        val tvEmailPerson: TextView = findViewById(R.id.tv_email_detail_person)

        val item = intent.getParcelableExtra<DataItemPerson>(EXTRA_PERSON) as DataItemPerson

        tvIdPerson.text = item.id
        tvNamePerson.text = item.nama
        tvEmailPerson.text = item.email
    }
}