package com.example.prepplateperfect.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import java.util.UUID

class ShoppingViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<ShoppingItem>?>(mutableListOf())
    val items: MutableLiveData<MutableList<ShoppingItem>?> = _items

    fun addItem(content: String) {
        val newItem = ShoppingItem(UUID.randomUUID().toString(), content, false)
        val updatedItems = _items.value ?: mutableListOf()
        updatedItems.add(newItem)
        _items.value = updatedItems
    }

    fun removeItem(item: ShoppingItem) {
        val updatedItems = _items.value ?: mutableListOf()
        updatedItems.remove(item)
        _items.value = updatedItems
    }

    fun updateItem(item: ShoppingItem, newContent: String) {
        val updatedItems = _items.value?.map {
            if (it.id == item.id) it.copy(content = newContent) else it
        }?.toMutableList()
        _items.value = updatedItems
    }
}
