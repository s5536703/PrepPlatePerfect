package com.example.prepplateperfect.ui.mealInfo

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.prepplateperfect.databinding.FragmentMealInformationBinding
import com.example.prepplateperfect.ui.shopping.ShoppingViewModel

class MealInfoFragment : Fragment() {
    private var _binding: FragmentMealInformationBinding? = null
    private val binding get() = _binding!!
    private val shoppingViewModel: ShoppingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMealInformationBinding.inflate(inflater, container, false)

        val recipeName = arguments?.getString("recipeName") ?: "No name"
        val recipeDescription = arguments?.getString("recipeDescription") ?: "No description"
        val recipeInstructions = arguments?.getString("recipeInstructions") ?: "No instructions"
        val cookingTime = arguments?.getString("cookingTime") ?: "No time provided"
        val recipeIngredients = arguments?.getString("recipeIngredients") ?: "No ingredients"
        val formattedIngredients = recipeIngredients.split(",").joinToString("\n") { it.trim() }

        binding.recipeTitle.text = recipeName
        binding.recipeDescription.text = recipeDescription
        binding.recipeTime.text = cookingTime
        binding.recipeIngredients.text = formattedIngredients
        binding.recipeInstructions.text = recipeInstructions

        binding.addToShoppingListButton.setOnClickListener {
            addToShoppingList(recipeIngredients)
        }

        return binding.root
    }

    private fun addToShoppingList(ingredients: String) {
        ingredients.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .forEach { ingredient ->
                shoppingViewModel.addItem(ingredient)
            }
        AlertDialog.Builder(requireContext())
            .setTitle("Ingredients Added")
            .setMessage("The ingredients have been added to your shopping list.")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}