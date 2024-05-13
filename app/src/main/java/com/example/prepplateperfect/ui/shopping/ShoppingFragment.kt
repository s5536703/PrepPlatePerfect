package com.example.prepplateperfect.ui.shopping

import android.app.AlertDialog
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        binding.addItemButton.setOnClickListener {
            promptForItemName()
        }
        binding.toggleEditModeButton.setOnClickListener {
            toggleEditMode()
        }
        observeItems()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = MyItemRecyclerViewAdapterShopping(emptyList(), viewModel::removeItem, viewModel::updateItem, false)
    }

    private fun observeItems() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            if (items != null) {
                (binding.list.adapter as MyItemRecyclerViewAdapterShopping).updateItems(items)
            }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
