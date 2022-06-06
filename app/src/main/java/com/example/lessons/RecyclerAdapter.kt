package com.example.lessons

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lessons.databinding.ItemBinding


class RecyclerAdapter(
    private val context: Context,
    private val list: List<ContactModel>,
    private val toItem: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)

        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.textViewName.text = list.get(position).name
        holder.textNumber.text = list.get(position).number
        holder.textSecondName.text = list.get(position).secondName
        println(position)
        Glide.with(context).load("https://picsum.photos/seed/${position + 1}/100")
            .into(holder.image)
        holder.card.setOnClickListener { toItem.invoke(position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ContactViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewName: TextView = binding.textName
        val textNumber: TextView = binding.textNumber
        val textSecondName: TextView = binding.textSecondName
        val card = binding.cardView
        val image: ImageView = binding.image
    }
}