package com.example.prepplateperfect.ui.cookbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.databinding.FragmentCookbookItemBinding

class MyItemRecyclerViewAdapterCookbook(
    private var recipes: List<Recipe>,
    private var isEditMode: Boolean,
    private val onDelete: (String) -> Unit,
    private val onRecipeClick: (Bundle) -> Unit
) : RecyclerView.Adapter<MyItemRecyclerViewAdapterCookbook.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = FragmentCookbookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, onRecipeClick, onDelete)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, isEditMode)
    }

    override fun getItemCount() = recipes.size

    @SuppressLint("NotifyDataSetChanged")
    fun toggleEditMode(editMode: Boolean) {
        isEditMode = editMode
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    class RecipeViewHolder(
        private val binding: FragmentCookbookItemBinding,
        private val onRecipeClick: (Bundle) -> Unit,
        private val onDelete: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe, isEditMode: Boolean) {
            binding.recipeTitle.text = recipe.name
            binding.recipeDescription.text = recipe.description
            binding.infoButton.visibility = if (isEditMode) View.GONE else View.VISIBLE
            binding.deleteButton.visibility = if (isEditMode) View.VISIBLE else View.GONE

            binding.infoButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("recipeId", recipe.id)
                    putString("recipeName", recipe.name)
                    putString("recipeDescription", recipe.description)
                    putString("recipeTime", recipe.time)
                    putString("recipeIngredients", recipe.ingredients)
                    putString("recipeInstructions", recipe.instructions)
                }
                onRecipeClick(bundle)
            }

            binding.deleteButton.setOnClickListener {
                onDelete(recipe.id)
            }
        }
    }
}