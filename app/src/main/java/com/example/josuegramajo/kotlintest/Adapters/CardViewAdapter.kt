package com.example.josuegramajo.kotlintest.Adapters

import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.example.josuegramajo.kotlintest.Objects.Activities
import com.example.josuegramajo.kotlintest.R

/**
 * Created by josuegramajo on 5/31/17.
 */

class CardViewAdapter(val list: List<Activities>, val itemClick : (Activities) -> Unit)
    : RecyclerView.Adapter<CardViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.utils_card_view_row,parent,false)
        return ViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, viewType: Int) = holder.bind(list[viewType])

    override fun getItemCount(): Int = list.count()

    class ViewHolder(itemView: View, val itemClick : (Activities) -> Unit) : RecyclerView.ViewHolder(itemView){
        fun bind(activities : Activities) = with(itemView){
            val tv_nombre = itemView.findViewById(R.id.card_view_name) as TextView
            val tv_descripcion = itemView.findViewById(R.id.card_view_description) as TextView
            tv_nombre.setText(activities.name)
            tv_descripcion.setText(activities.description)
            with(activities){
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}