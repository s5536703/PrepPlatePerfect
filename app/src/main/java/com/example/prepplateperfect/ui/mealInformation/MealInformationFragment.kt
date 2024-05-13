package com.example.prepplateperfect.ui.mealInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prepplateperfect.databinding.FragmentMealInformationBinding

class MealInformationFragment : Fragment() {
    private var _binding: FragmentMealInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMealInformationBinding.inflate(inflater, container, false)

        val recipeName = arguments?.getString("recipeName") ?: "No name"
        val recipeDescription = arguments?.getString("recipeDescription") ?: "No description"
        val recipeInstructions = arguments?.getString("recipeInstructions") ?: "No instructions"
        val cookingTime = arguments?.getString("cookingTime") ?: "No time provided"
        val recipeIngredients = arguments?.getString("recipeIngredients") ?: "No ingredients"
        val formattedIngredients = recipeIngredients.split(",").joinToString("\n")

        binding.recipeTitle.text = recipeName
        binding.recipeDescription.text = recipeDescription
        binding.recipeTime.text = cookingTime
        binding.recipeIngredients.text = formattedIngredients
        binding.recipeInstructions.text = recipeInstructions

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}