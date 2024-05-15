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
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentShoppingListBinding

class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by viewModels()
    private var isEditMode = false
    private lateinit var adapter: AdapterShopping

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupListeners()
        observeItems()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = AdapterShopping(
            emptyList(), viewModel::removeItem, viewModel::updateItem, isEditMode)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
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
            adapter.updateItems(items ?: listOf())
        }
    }

    private fun promptForItemName() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_item, null)
        val itemNameInput = dialogView.findViewById<EditText>(R.id.itemNameInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val itemName = itemNameInput.text.toString().trim()
                if (itemName.isNotEmpty()) {
                    viewModel.addItem(itemName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        adapter.toggleEditMode(isEditMode)
        binding.addItemButton.visibility = if (isEditMode) View.VISIBLE else View.GONE
        binding.toggleEditModeButton.text = if (isEditMode) "Done" else "Edit"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}