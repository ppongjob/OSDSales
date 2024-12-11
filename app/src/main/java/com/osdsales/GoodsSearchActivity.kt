package com.osdsales

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
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
        val goodsGroup = ArrayList<String>()

        goodsGroup.add("A")
        goodsGroup.add("B")
        goodsGroup.add("C")
        goodsGroup.add("D")
        goodsGroup.add("E")

        val GroupAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,goodsGroup)
        GroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spGoodsGroup=findViewById<Spinner>(R.id.spGoodsGroup)
        spGoodsGroup.adapter = GroupAdapter
    }
}