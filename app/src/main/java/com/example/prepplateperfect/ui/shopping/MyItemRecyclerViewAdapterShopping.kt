package com.example.prepplateperfect.ui.shopping

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.databinding.FragmentShoppingItemBinding

data class ShoppingItem(
    val id: String,
    var content: String,
    var isChecked: Boolean = false
)

class ShoppingItemDiffCallback(
    private val oldList: List<ShoppingItem>,
    private val newList: List<ShoppingItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}

class MyItemRecyclerViewAdapterShopping(
    private var items: List<ShoppingItem>,
    private val onDelete: (ShoppingItem) -> Unit,
    private val onItemRename: (ShoppingItem, String) -> Unit,
    var isEditMode: Boolean
) : RecyclerView.Adapter<MyItemRecyclerViewAdapterShopping.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = FragmentShoppingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ShoppingItem>) {
        val diffResult = DiffUtil.calculateDiff(ShoppingItemDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ItemViewHolder(private val binding: FragmentShoppingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemContent.setOnClickListener {
                if (isEditMode) showEditDialog(items[adapterPosition])
            }
        }

        fun bind(item: ShoppingItem) {
            binding.itemCheckbox.text = item.content
            binding.itemCheckbox.isChecked = item.isChecked
            binding.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (!isEditMode) {
                    item.isChecked = isChecked
                }
            }
            binding.itemDeleteButton.visibility = if (isEditMode) View.VISIBLE else View.GONE
            binding.itemDeleteButton.setOnClickListener { if (isEditMode) onDelete(item) }
        }

        private fun showEditDialog(item: ShoppingItem) {
            val editText = EditText(itemView.context).apply {
                setText(item.content)
            }
            AlertDialog.Builder(itemView.context)
                .setTitle("Edit Item Name")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    onItemRename(item, editText.text.toString())
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
