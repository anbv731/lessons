package com.example.lessons

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private  val context: Context, private val list: List<ContactModel>) :
    RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.textViewName.text = list.get(position).name
        holder.textNumber.text=list.get(position).number
        holder.layout.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textName)
        val textNumber:TextView=itemView.findViewById(R.id.textNumber)
        val layout:ConstraintLayout=itemView.findViewById(R.id.constraintlay)
    }
}