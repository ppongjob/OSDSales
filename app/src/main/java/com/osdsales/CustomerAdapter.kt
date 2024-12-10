package com.osdsales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter(
    private val customerList: ArrayList<CustomerModel>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {


        val customerCode = itemView.findViewById<TextView>(R.id.ivCustomerCode)
        val customerName = itemView.findViewById<TextView>(R.id.tvCustomerName)
        val customerShop = itemView.findViewById<TextView>(R.id.ivCustomerShop)
        val customerProvince = itemView.findViewById<TextView>(R.id.tvCustomerProvince)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false))
    }

    override fun onBindViewHolder(holder: CustomerAdapter.ViewHolder, position: Int) {
        val customer = customerList[position]

        holder.customerCode.text = customer.customerCode
        holder.customerName.text = customer.customerName
        holder.customerShop.text = customer.customerShop
        holder.customerProvince.text = customer.customerProvince
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

}