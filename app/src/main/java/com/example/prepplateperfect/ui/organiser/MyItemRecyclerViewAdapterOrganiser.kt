package com.example.prepplateperfect.ui.organiser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.R

class MyItemRecyclerViewAdapterOrganiser :
    ListAdapter<MealReminder, MyItemRecyclerViewAdapterOrganiser.ViewHolder>(
        MealReminderDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_organiser_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.mealNameTextView.text = item.mealName
        holder.mealTimeTextView.text = item.mealTime
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealNameTextView: TextView = view.findViewById(R.id.mealNameTextView)
        val mealTimeTextView: TextView = view.findViewById(R.id.mealTimeTextView)
    }
}

class MealReminderDiffCallback : DiffUtil.ItemCallback<MealReminder>() {
    override fun areItemsTheSame(oldItem: MealReminder, newItem: MealReminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealReminder, newItem: MealReminder): Boolean {
        return oldItem == newItem
    }
}