package com.example.prepplateperfect.ui.organiser

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentOrganiserItemBinding

class MealReminderDiffCallback(
    private val oldList: List<MealReminder>,
    private val newList: List<MealReminder>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}

class AdapterOrganiser(
    private var items: List<MealReminder>,
    private val onDelete: (MealReminder) -> Unit,
    private val onItemRename: (MealReminder, String, String) -> Unit,
    var isEditMode: Boolean
) : RecyclerView.Adapter<AdapterOrganiser.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = FragmentOrganiserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<MealReminder>) {
        val diffResult = DiffUtil.calculateDiff(MealReminderDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun toggleEditMode(editMode: Boolean) {
        isEditMode = editMode
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: FragmentOrganiserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (isEditMode) {
                    showEditDialog(items[bindingAdapterPosition])
                }
            }
            binding.deleteButton.setOnClickListener {
                if (isEditMode) {
                    binding.mealNameTextView.setTextColor(Color.RED)
                    binding.mealTimeTextView.setTextColor(Color.RED)
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onDelete(items[position])
                    }
                }
            }
        }

        fun bind(item: MealReminder) {
            binding.mealNameTextView.text = item.mealName
            binding.mealTimeTextView.text = item.mealTime
            binding.mealNameTextView.setTextColor(Color.BLACK)
            binding.mealTimeTextView.setTextColor(Color.BLACK)
            binding.deleteButton.visibility = if (isEditMode) View.VISIBLE else View.GONE
        }

        private fun showEditDialog(item: MealReminder) {
            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_add_meal, null)
            val mealNameInput = dialogView.findViewById<EditText>(R.id.mealNameInput).apply {
                setText(item.mealName)
            }
            val mealTimeInput = dialogView.findViewById<EditText>(R.id.mealTimeInput).apply {
                setText(item.mealTime)
            }

            AlertDialog.Builder(itemView.context)
                .setTitle("Edit Meal Reminder")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    val newName = mealNameInput.text.toString().trim()
                    val newTime = mealTimeInput.text.toString().trim()
                    if (newName.isNotEmpty() && newTime.isNotEmpty()) {
                        onItemRename(item, newName, newTime)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}