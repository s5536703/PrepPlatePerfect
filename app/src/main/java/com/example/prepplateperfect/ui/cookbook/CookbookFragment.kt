package com.example.prepplateperfect.ui.cookbook

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentCookbookBinding
import java.util.UUID

class CookbookFragment : Fragment() {
    private var _binding: FragmentCookbookBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CookbookViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCookbookBinding.inflate(inflater, container, false)
        setupRecyclerView()
        binding.addRecipeButton.setOnClickListener {
            addRecipeDialog()
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapterCookbook(recipes ?: emptyList()) { bundle ->
                    findNavController().navigate(R.id.action_cookbookFragment_to_mealInformationFragment, bundle)
                }
            }
        }
    }

    private fun addRecipeDialog() {
        val layout = LayoutInflater.from(context).inflate(R.layout.add_recipe_dialog, null)
        val recipeNameInput = layout.findViewById<EditText>(R.id.recipeNameInput)
        val recipeDescriptionInput = layout.findViewById<EditText>(R.id.recipeDescriptionInput)
        val recipeTimeInput = layout.findViewById<EditText>(R.id.recipeTimeInput)
        val recipeIngredientsInput = layout.findViewById<EditText>(R.id.recipeIngredientsInput)
        val recipeInstructionsInput = layout.findViewById<EditText>(R.id.recipeInstructionsInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Recipe")
            .setView(layout)
            .setPositiveButton("Add") { dialog, _ ->
                val name = recipeNameInput.text.toString().trim()
                val desc = recipeDescriptionInput.text.toString().trim()
                val time = recipeTimeInput.text.toString().trim()
                val ingredients = recipeIngredientsInput.text.toString().trim()
                val instructions = recipeInstructionsInput.text.toString().trim()
                if (name.isNotEmpty() && desc.isNotEmpty()) {
                    val newRecipe = Recipe(
                        UUID.randomUUID().toString(),
                        name,
                        desc,
                        time,
                        ingredients,
                        instructions,

                    )
                    viewModel.addRecipe(newRecipe)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}