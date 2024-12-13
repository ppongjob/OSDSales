package com.osdsales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoodsOrderAdapter(private val GoodsOrderList: ArrayList<GoodsOrderModel>): RecyclerView.Adapter<GoodsOrderAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var GoodsOrderCode = itemView.findViewById<TextView>(R.id.tvGoodsOrderCode)
        var GoodsOrderName = itemView.findViewById<TextView>(R.id.tvGoodsOrderName)
        var GoodsQTY = itemView.findViewById<EditText>(R.id.enGoodsQty)
        var GoodsFreeQTY = itemView.findViewById<EditText>(R.id.enGoodsQTYFree)
        var GoodsFreeUnit = itemView.findViewById<Spinner>(R.id.spQtyUnitFree)
        var GoodsQtyUnit =  itemView.findViewById<Spinner>(R.id.spQtyUnit)
        val GoodsOrderDelete = itemView.findViewById<TextView>(R.id.tvGoodsOrderDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_goods_order, parent, false))

    }

    override fun getItemCount(): Int {
        return GoodsOrderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goodsOrder = GoodsOrderList[position]
        val UnitAdapter = ArrayAdapter(holder.itemView.context,android.R.layout.simple_spinner_item,goodsOrder.GoodsUnitFree)
        UnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.GoodsOrderCode.text = goodsOrder.GoodsCode
        holder.GoodsOrderName.text = goodsOrder.GoodsName
        holder.GoodsQTY.setText(goodsOrder.GoodsQTY.toString())
        holder.GoodsFreeQTY.setText(goodsOrder.GoodsQTYFree.toString())
        holder.GoodsFreeUnit.adapter =UnitAdapter
        holder.GoodsQtyUnit.adapter = UnitAdapter

    }
}