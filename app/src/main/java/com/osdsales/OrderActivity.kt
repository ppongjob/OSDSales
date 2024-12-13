package com.osdsales

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class OrderActivity : AppCompatActivity() {

    private lateinit var customerResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var goodsResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var rvGoods: RecyclerView
    private var GoodsList = ArrayList<GoodsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnSend = findViewById<Button>(R.id.btnSend)

        customerResultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
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
            customerResultLauncher.launch(intent)

        }

        goodsResultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
            result->
                if (result.resultCode == RESULT_OK) {
                    val goodsCodes = result.data?.getStringArrayListExtra("GoodsSKUList")

                    rvGoods=findViewById(R.id.rvGoods)

                    rvGoods.layoutManager = LinearLayoutManager(this)
                    //val goodsCode= result.data?.getStringArrayListExtra("GoodsSKUList")
                    for(i in 0 until goodsCodes!!.size){
                        val GoodsDetail=result.data?.getStringArrayListExtra(goodsCodes[i].toString())
                        if (GoodsDetail!=null){
                            GoodsList.add(GoodsModel(GoodsDetail[0],GoodsDetail[1],GoodsDetail[2],GoodsDetail[3],GoodsDetail[4],GoodsDetail[5]))
                        }
                        //GoodsList.add(GoodsModel(goodsCodes[i].toString(),i.toString(),"โหล","1"))
                    }

                    val Goodsadapter = GoodsAdapter(GoodsList)
                    rvGoods.adapter = Goodsadapter
                }
        }

        btnAdd.setOnClickListener {
            val intent = Intent(this, GoodsOrderActivity::class.java)
            goodsResultLauncher.launch(intent)
        }
    }
}