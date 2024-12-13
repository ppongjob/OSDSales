package com.osdsales

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoodsOrderActivity : AppCompatActivity() {

    private lateinit var rvGoodsOrder: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goods_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var goodsGroup = ArrayList<GoodsDept>()
        val btnOK = findViewById<Button>(R.id.btnOK)

        goodsGroup=GetData().GetGoodsGroup()

        val GroupAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,goodsGroup)
        GroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spGoodsGroup=findViewById<Spinner>(R.id.spGoodsGroup)
        spGoodsGroup.adapter = GroupAdapter

        spGoodsGroup.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define you actions as you want
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long)
            {
                val goodsDept = spGoodsGroup.selectedItem as GoodsDept
                //Toast.makeText(this@GoodsSearchActivity,goodsDept.Name,Toast.LENGTH_LONG).show()
                var GoodsOrderList = ArrayList<GoodsOrderModel>()

                GoodsOrderList=GetData().GetGoodsByDept(goodsDept.key)

                val GoodsOrderAdapter = GoodsOrderAdapter(GoodsOrderList)
                rvGoodsOrder.adapter = GoodsOrderAdapter

            }
        }

        rvGoodsOrder = findViewById(R.id.rvGoodsOrder)
        rvGoodsOrder.layoutManager = LinearLayoutManager(this)

        btnOK.setOnClickListener {
            val resultIntent = Intent()
            var GoodsSKUList = ArrayList<String>()
            var adapter = rvGoodsOrder.adapter as GoodsOrderAdapter
            var c=adapter.itemCount
            for(i in 0 until c-1){
                val child = rvGoodsOrder.getChildAt(i)
                if (child!=null ){
                    val tvGoodsOrderCode = child.findViewById<TextView>(R.id.tvGoodsOrderCode).text
                    val tvGoodsOrderName = child.findViewById<TextView>(R.id.tvGoodsOrderName).text
                    val enGoodsQty = child.findViewById<EditText>(R.id.enGoodsQty).text
                    val spQtyUnit = child.findViewById<Spinner>(R.id.spQtyUnit).selectedItem.toString()
                    val enGoodsQTYFree = child.findViewById<EditText>(R.id.enGoodsQTYFree).text
                    val spQtyUnitFree = child.findViewById<Spinner>(R.id.spQtyUnitFree).selectedItem.toString()
                    if ((enGoodsQty.toString().toDouble() >0 ) ||(enGoodsQTYFree.toString().toDouble()>0) ){
                        GoodsSKUList.add(tvGoodsOrderCode.toString())
                        var GoodsOrderDetail = ArrayList<String>()
                        GoodsOrderDetail.add(tvGoodsOrderCode.toString())
                        GoodsOrderDetail.add(tvGoodsOrderName.toString())
                        GoodsOrderDetail.add(enGoodsQty.toString())
                        GoodsOrderDetail.add(spQtyUnit)
                        GoodsOrderDetail.add(enGoodsQTYFree.toString())
                        GoodsOrderDetail.add(spQtyUnitFree)
                        resultIntent.putStringArrayListExtra(tvGoodsOrderCode.toString(),GoodsOrderDetail)
                    }
                }
            }
            resultIntent.putStringArrayListExtra("GoodsSKUList",GoodsSKUList)

            //resultIntent.putExtra("customerCode", customer.customerCode)
            //resultIntent.putExtra("customerName", customer.customerName)
            //resultIntent.putExtra("customerShop", customer.customerShop)
            //resultIntent.putExtra("customerProvince", customer.customerProvince)
            setResult(RESULT_OK, resultIntent)
            finish()
        }


    }
}