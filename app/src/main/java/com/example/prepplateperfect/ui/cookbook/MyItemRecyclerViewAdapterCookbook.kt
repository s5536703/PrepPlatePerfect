package com.example.prepplateperfect.ui.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.databinding.FragmentCookbookItemBinding

class MyItemRecyclerViewAdapterCookbook(
    private val recipes: List<Recipe>,
    private val onRecipeClick: (Bundle) -> Unit
) : RecyclerView.Adapter<MyItemRecyclerViewAdapterCookbook.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = FragmentCookbookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, onRecipeClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = recipes.size

    class RecipeViewHolder(
        private val binding: FragmentCookbookItemBinding,
        private val onRecipeClick: (Bundle) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.name
            binding.recipeDescription.text = recipe.description
            binding.infoButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("recipeName", recipe.name)
                    putString("recipeDescription", recipe.description)
                    putString("recipeTime", recipe.time)
                    putString("recipeIngredients", recipe.ingredients)
                    putString("recipeInstructions", recipe.instructions)
                }
                onRecipeClick(bundle)
            }
        }
    }
}