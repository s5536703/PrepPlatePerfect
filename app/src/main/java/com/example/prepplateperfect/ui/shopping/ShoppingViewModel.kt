package com.example.prepplateperfect.ui.shopping

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import java.util.UUID

class ShoppingViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<ShoppingItem>>(mutableListOf())
    val items: MutableLiveData<List<ShoppingItem>> get() = _items as MutableLiveData<List<ShoppingItem>>

    fun addItem(content: String) {
        if (_items.value?.any { it.content.equals(content, ignoreCase = true) } != true) {
            val newItem = ShoppingItem(UUID.randomUUID().toString(), content, false)
            val updatedItems = _items.value ?: mutableListOf()
            updatedItems.add(newItem)
            _items.value = updatedItems
            Log.d("ShoppingViewModel", "Item added successfully: $content")
        } else {
            Log.d("ShoppingViewModel", "Attempt to add duplicate item: $content")
        }
    }

    fun removeItem(item: ShoppingItem) {
        _items.value?.let {
            it.remove(item)
            _items.value = it
        }
    }

    fun updateItem(item: ShoppingItem, newContent: String) {
        _items.value?.let {
            val index = it.indexOfFirst { it.id == item.id }
            if (index != -1) {
                it[index] = item.copy(content = newContent)
                _items.value = it
            }
        }
    }
}