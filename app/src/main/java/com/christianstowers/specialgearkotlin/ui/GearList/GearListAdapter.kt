package com.christianstowers.specialgearkotlin.ui.GearList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.christianstowers.specialgearkotlin.R
import android.support.design.widget.Snackbar
import com.christianstowers.specialgearkotlin.data.entities.GearModel
import com.christianstowers.specialgearkotlin.ui.GearDetailList.GearDetailListActivity

class GearListAdapter(val context: Context) : RecyclerView.Adapter<GearListAdapter.MyViewHolder>() {


    var gearList : List<GearModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_gear,parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return gearList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.gearCard.setOnClickListener { v ->
//            Snackbar.make(v, "ORDER #"+ gearList[position].order + " CLICKED", Snackbar.LENGTH_SHORT).show()
            val intent = Intent(v.context, GearDetailListActivity::class.java)
            intent.putExtra("gearID", gearList[position].id)
            v.context.startActivity(intent)
        }

        holder.gearName.text = gearList[position].name
        Glide.with(context).load(gearList[position].image)
//            .apply(RequestOptions().)
            .into(holder.gearImage)
    }

    fun setGearListItems(gearList: List<GearModel>) {
        this.gearList = gearList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val gearName: TextView = itemView!!.findViewById(R.id.gear_name)
        val gearImage: ImageView = itemView!!.findViewById(R.id.gear_img)
        val gearDesc: TextView = itemView!!.findViewById(R.id.gear_desc)
        val gearCard: CardView = itemView!!.findViewById(R.id.GearListItem)


    }

}