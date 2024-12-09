package com.osdsales

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomerSearchActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customer_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val UName =intent.getStringExtra("UName")
        val SLCode =intent.getStringExtra("SLCode")
        val se = findViewById<EditText>(R.id.searchEditText)

        se.setOnEditorActionListener { v, actionId, event ->
            //Toast.makeText(this, "ค้นหา: OK", Toast.LENGTH_SHORT).show()
            recyclerView=findViewById(R.id.rvCustomer)

            recyclerView.layoutManager = LinearLayoutManager(this)
            var customerList = ArrayList<CustomerModel>()
            val myData =GetData()
            val Stext =se.text.toString()
            customerList = myData.GetCustomer("$SLCode","$Stext")

            val adapter = CustomerAdapter(customerList, object : CustomerAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                    var customer = customerList[position]
                    val resultIntent = Intent()
                    resultIntent.putExtra("customerCode", customer.customerCode)
                    resultIntent.putExtra("customerName", customer.customerName)
                    resultIntent.putExtra("customerShop", customer.customerShop)
                    resultIntent.putExtra("customerProvince", customer.customerProvince)
                    setResult(RESULT_OK, resultIntent)
                    finish()

                }

            }
            )

            recyclerView.adapter = adapter
            true
        }

    }
}