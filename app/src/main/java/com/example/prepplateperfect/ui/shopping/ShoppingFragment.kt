package com.example.prepplateperfect.ui.shopping

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prepplateperfect.databinding.FragmentShoppingListBinding

class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by viewModels()
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupListeners()
        observeItems()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = MyItemRecyclerViewAdapterShopping(
            emptyList(), viewModel::removeItem, viewModel::updateItem, isEditMode)
    }

    private fun setupListeners() {
        binding.addItemButton.setOnClickListener {
            promptForItemName()
        }
        binding.toggleEditModeButton.setOnClickListener {
            toggleEditMode()
        }
    }

    private fun observeItems() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            Log.d("ShoppingFragment", "Observed items: $items")
            (binding.list.adapter as MyItemRecyclerViewAdapterShopping).updateItems(items ?: listOf())
        }
    }

    private fun promptForItemName() {
        val editText = EditText(context)
        AlertDialog.Builder(context)
            .setTitle("Enter Item Name")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val name = editText.text.toString().trim()
                if (name.isNotEmpty()) {
                    viewModel.addItem(name)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        (binding.list.adapter as MyItemRecyclerViewAdapterShopping).toggleEditMode(isEditMode)
        binding.toggleEditModeButton.text = if (isEditMode) "Done" else "Edit"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}