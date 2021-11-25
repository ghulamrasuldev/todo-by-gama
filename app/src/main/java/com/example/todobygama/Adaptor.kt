package com.example.todobygama

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*

class Adaptor(var data: List<CardInfo>): RecyclerView.Adapter<Adaptor.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.title
        var seletedCategory = itemView.category
        var date = itemView.date
        var time = itemView.time
        var description = itemView.task_description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context ).inflate(R.layout.card_view,parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.title.text = data[position].CardTaskTitle
        holder.date.text = data[position].CardTaskDate
        holder.time.text = data[position].CardTaskTime
        holder.seletedCategory.text = data[position].CardTaskCategory
        holder.description.text = data[position].CardTaskDescription

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateTask::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}