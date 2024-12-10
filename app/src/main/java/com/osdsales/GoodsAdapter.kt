package com.osdsales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoodsAdapter (private val GoodsList: ArrayList<GoodsModel>): RecyclerView.Adapter<GoodsAdapter.ViewHolder>()
{
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        val GoodsCode = itemView.findViewById<TextView>(R.id.tvGoodsCode)
        val GoodsName = itemView.findViewById<TextView>(R.id.tvGoodsName)
        val GoodsUnit = itemView.findViewById<TextView>(R.id.tvGoodsUnit)
        val GoodsQTY = itemView.findViewById<TextView>(R.id.tvGoodsQTY)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false))
    }

    override fun getItemCount(): Int {
        return GoodsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Goods = GoodsList[position]

        holder.GoodsCode.text = Goods.GoodsCode
        holder.GoodsName.text = Goods.GoodsName
        holder.GoodsUnit.text = Goods.GoodsUnit
        holder.GoodsQTY.text = Goods.GoodsQTY

    }
}