package com.osdsales

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class OrderActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var rvGoods: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnSend = findViewById<Button>(R.id.btnSend)

        resultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
            result->
            if (result.resultCode == RESULT_OK) {
                val customerCode = result.data?.getStringExtra("customerCode")
                val customerName = result.data?.getStringExtra("customerName")
                val customerShop = result.data?.getStringExtra("customerShop")
                val customerProvince = result.data?.getStringExtra("customerProvince")
                val tvCustomerCode = findViewById<TextView>(R.id.tvCustomerCode)
                val tvCustomerName = findViewById<TextView>(R.id.tvCustomerName)
                val tvCustomerCompanyName = findViewById<TextView>(R.id.tvCustomerCompanyName)
                val tvCustomerProvince = findViewById<TextView>(R.id.tvCustomerProvince)
                tvCustomerCode.text = customerCode
                tvCustomerName.text = customerName
                tvCustomerCompanyName.text = customerShop
                tvCustomerProvince.text = customerProvince
            }
        }

        val cvCustomer = findViewById<CardView>(R.id.cvCustomer)
        val UName =intent.getStringExtra("UName")
        val SLCode =intent.getStringExtra("SLCode")
        cvCustomer.setOnClickListener {
            val intent = Intent(this, CustomerSearchActivity::class.java)
            intent.putExtra("UName",UName)
            intent.putExtra("SLCode",SLCode)
            resultLauncher.launch(intent)

        }

        rvGoods=findViewById(R.id.rvGoods)

        rvGoods.layoutManager = LinearLayoutManager(this)

        var GoodsList = ArrayList<GoodsModel>()
        GoodsList.add(GoodsModel("1","1","โหล","1"))
        GoodsList.add(GoodsModel("2","2","กระสอบ","2"))
        GoodsList.add(GoodsModel("3","3","ลัง","3"))
        GoodsList.add(GoodsModel("4","4","ถุง","4"))

        val Goodsadapter = GoodsAdapter(GoodsList)
        rvGoods.adapter = Goodsadapter

        btnAdd.setOnClickListener {
            val intent = Intent(this, GoodsSearchActivity::class.java)
            startActivity(intent)
        }

    }

}