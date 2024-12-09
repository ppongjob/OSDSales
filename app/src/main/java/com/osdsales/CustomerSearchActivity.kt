package com.osdsales

import android.os.Bundle
import android.widget.EditText
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

        val se = findViewById<EditText>(R.id.searchEditText)
        se.setOnEditorActionListener { v, actionId, event ->
            //Toast.makeText(this, "ค้นหา: OK", Toast.LENGTH_SHORT).show()
            recyclerView=findViewById(R.id.rvCustomer)

            recyclerView.layoutManager = LinearLayoutManager(this)

            val customerList = ArrayList<CustomerModel>()

            customerList.add(CustomerModel("1", "A", "A", "A"))
            customerList.add(CustomerModel("2", "B", "B", "B"))
            customerList.add(CustomerModel("3", "C", "C", "C"))
            customerList.add(CustomerModel("4", "D", "D", "D"))

            val adapter = CustomerAdapter(customerList)

            recyclerView.adapter = adapter
            true
        }

    }
}