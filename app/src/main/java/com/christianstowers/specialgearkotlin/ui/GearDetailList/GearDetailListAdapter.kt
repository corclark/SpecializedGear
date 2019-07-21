package com.christianstowers.specialgearkotlin.ui.GearDetailList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.christianstowers.specialgearkotlin.R
import com.christianstowers.specialgearkotlin.data.entities.SubGearsItem

class GearDetailListAdapter(val context: Context) : RecyclerView.Adapter<GearDetailListAdapter.MyViewHolder>() {

    var subGearList : List<SubGearsItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_gear_detail,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subGearList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.subGearName.text = subGearList[position].name
//        holder.subGearDesc.text = subGearList[position].description

        val descSum: String
        val descLength = subGearList[position].description.length
        if (descLength >= 36) {
            descSum = subGearList[position].description.substring(0, 36)
            holder.subGearDesc.setText("$descSum...")
        } else if (descLength in 1..35) {
            holder.subGearDesc.text = subGearList[position].description
        } else {
            holder.subGearDesc.text = subGearList[position].description
        }

        holder.subGearPrice.text = subGearList[position].price
        Glide.with(context).load(subGearList[position].image)
            .into(holder.subGearImage)


    }

    fun setSubGearListItems(subGearList: List<SubGearsItem>) {
        this.subGearList = subGearList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val subGearName: TextView = itemView!!.findViewById(R.id.gear_name)
        val subGearImage: ImageView = itemView!!.findViewById(R.id.gear_img)
        val subGearDesc: TextView = itemView!!.findViewById(R.id.gear_desc)
        val subGearPrice: TextView = itemView!!.findViewById(R.id.gear_price)

    }

}