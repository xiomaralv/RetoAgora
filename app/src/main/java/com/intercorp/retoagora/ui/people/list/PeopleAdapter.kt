package com.intercorp.retoagora.ui.people.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intercorp.retoagora.R
import com.intercorp.retoagora.data.response.Result

class PeopleAdapter(private val peopletList: List<Result>) :
        RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    private var onItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.row_people, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return peopletList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name?.text = peopletList[position].name
        viewHolder.birthday?.text = "Año de nacimiento: "+peopletList[position].birthYear
        viewHolder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(viewHolder.itemView, position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvName)
        val birthday = itemView.findViewById<TextView>(R.id.tvBirthday)
    }


    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}