package com.example.prepplateperfect.ui.mealInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentMealInformationBinding
import com.example.prepplateperfect.ui.cookbook.CookbookViewModel
import com.example.prepplateperfect.ui.shopping.ShoppingViewModel

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

        setupMoreOptionsButton()

        return binding.root
    }

    private fun setupMoreOptionsButton() {
        binding.btnMoreOptions.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.recipe_options_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_add_to_shopping_list -> {
                    addToShoppingList()
                    true
                }
                R.id.action_delete_recipe -> {
                    deleteRecipe()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun addToShoppingList() {
        val ingredients = arguments?.getString("recipeIngredients") ?: return
        val shoppingViewModel: ShoppingViewModel by activityViewModels()

        ingredients.split(",").map { it.trim() }.forEach { ingredient ->
            if (ingredient.isNotEmpty()) {
                shoppingViewModel.addItem(ingredient)
            }
        }

        findNavController().navigate(R.id.action_mealInformationFragment_to_shoppingFragment)
    }

    private fun deleteRecipe() {
        val recipeId = arguments?.getString("recipeId") ?: return
        val cookbookViewModel: CookbookViewModel by activityViewModels()

        cookbookViewModel.deleteRecipe(recipeId)
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}