package com.example.prepplateperfect.ui.discover

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentDiscoverItemBinding
import com.example.prepplateperfect.ui.cookbook.Recipe

class AdapterDiscover(
    private var recipes: List<Recipe>
) : RecyclerView.Adapter<AdapterDiscover.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = FragmentDiscoverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = recipes.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    class RecipeViewHolder(
        private val binding: FragmentDiscoverItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.name
            binding.recipeDescription.text = recipe.description
            binding.recipeRating.text = "${recipe.rating} ★"
            binding.infoButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("recipeId", recipe.id)
                    putString("recipeName", recipe.name)
                    putString("recipeDescription", recipe.description)
                    putString("cookingTime", recipe.time)
                    putString("recipeIngredients", recipe.ingredients)
                    putString("recipeInstructions", recipe.instructions)
                }
                it.findNavController().navigate(R.id.action_discoverFragment_to_mealInformationFragment, bundle)
            }
        }
    }
}