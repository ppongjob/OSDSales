package com.osdsales

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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
    var customerCode :String=""
    var customerName :String=""
    var customerShop :String=""
    var customerProvince:String =""
    var customerADDBKey :Int=0




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnGoodsOrderDelete = findViewById<TextView>(R.id.tvGoodsOrderDelete)


        customerResultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
            result->
            if (result.resultCode == RESULT_OK) {
                customerCode = result.data?.getStringExtra("customerCode").toString()
                customerName = result.data?.getStringExtra("customerName").toString()
                customerShop = result.data?.getStringExtra("customerShop").toString()
                customerProvince = result.data?.getStringExtra("customerProvince").toString()
                customerADDBKey = result.data?.getIntExtra("customerADDBKey", 0).toString().toInt()
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

        btnSend.setOnClickListener {
            SaveOrder()
        }
    }

    private fun SaveOrder() {


    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("ยังไม่ได้จัดเก็บข้อมูล ")
                .setMessage("ยืนยันการย้อนกลับ ยังไม่ได้จัดเก็บข้อมูล ")
                .setPositiveButton("ยืนยัน") { _, _ ->
                    finish()
                }
                .setNegativeButton("ยกเลิก") { _, _ ->
                    // ไม่ทำอะไรเมื่อผู้ใช้กดปุ่มยกเลิก
                }
            builder.show()
    }
}