package com.osdsales

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val btnMenuOrder = findViewById<Button>(R.id.btnMenuOrder)

        btnMenuOrder.setOnClickListener {
            val intent= Intent(this,OrderActivity::class.java)
            startActivity(intent)
        }
    }
}