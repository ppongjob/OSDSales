package com.osdsales

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnOK = findViewById<Button>(R.id.btnLogin)
        val teUser = findViewById<EditText>(R.id.teUser)
        val tePassword = findViewById<EditText>(R.id.tePassword)

        btnOK.setOnClickListener {
            val myData =GetData()
            val uName:String =myData.chkSales(teUser.text.toString(),tePassword.text.toString())
            teUser.setText("")
            tePassword.setText("")
            if (uName   !=""){
                //Toast.makeText(this,uName, Toast.LENGTH_SHORT).show()
                val intent= Intent(this,MenuActivity::class.java)
                intent.putExtra("UName",uName)

                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"Invalid User Name or Password", Toast.LENGTH_SHORT).show()
            }
        }


    }
}