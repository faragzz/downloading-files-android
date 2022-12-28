package com.udacity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val name = intent.getStringExtra("name").toString()
        val checkData = intent.getStringExtra("check").toString()

        print("nameeeeeeeeeeeeeeeeee  "+name)
        print("Dataaaaaaaaaaaaaaaaaaaaaa "+checkData)
        filename.setText(name)
        check.setText(checkData)


        ok.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
