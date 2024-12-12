package com.osdsales

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GoodsSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goods_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var goodsGroup = ArrayList<GoodsDept>()

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

            }


        }
    }
}