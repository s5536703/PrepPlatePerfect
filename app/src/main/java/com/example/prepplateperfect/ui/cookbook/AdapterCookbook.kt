package com.example.prepplateperfect.ui.cookbook

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentCookbookItemBinding

class AdapterCookbook(
    private var recipes: List<Recipe>,
    private var isEditMode: Boolean,
    private val onRecipeClick: (Bundle) -> Unit,
    private val onItemRename: (Recipe, String, String, String, String, String) -> Unit
) : RecyclerView.Adapter<AdapterCookbook.RecipeViewHolder>() {

    private val recipesToDelete = mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = FragmentCookbookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, onRecipeClick, onItemRename, recipesToDelete)
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

    fun getRecipesToDelete(): List<String> {
        return recipesToDelete.toList()
    }

    class RecipeViewHolder(
        private val binding: FragmentCookbookItemBinding,
        private val onRecipeClick: (Bundle) -> Unit,
        private val onItemRename: (Recipe, String, String, String, String, String) -> Unit,
        private val recipesToDelete: MutableSet<String>
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
                    putString("cookingTime", recipe.time)
                    putString("recipeIngredients", recipe.ingredients)
                    putString("recipeInstructions", recipe.instructions)
                }
                onRecipeClick(bundle)
            }

            binding.deleteButton.setOnClickListener {
                if (recipesToDelete.contains(recipe.id)) {
                    recipesToDelete.remove(recipe.id)
                    binding.recipeTitle.setTextColor(Color.BLACK)
                    binding.recipeDescription.setTextColor(Color.BLACK)
                } else {
                    recipesToDelete.add(recipe.id)
                    binding.recipeTitle.setTextColor(Color.RED)
                    binding.recipeDescription.setTextColor(Color.RED)
                }
            }

            binding.root.setOnClickListener {
                if (isEditMode) {
                    showEditDialog(recipe)
                }
            }
        }

        private fun showEditDialog(recipe: Recipe) {
            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_add_recipe, null)
            val recipeNameInput = dialogView.findViewById<EditText>(R.id.recipeNameInput).apply {
                setText(recipe.name)
            }
            val recipeDescriptionInput = dialogView.findViewById<EditText>(R.id.recipeDescriptionInput).apply {
                setText(recipe.description)
            }
            val recipeTimeInput = dialogView.findViewById<EditText>(R.id.recipeTimeInput).apply {
                setText(recipe.time)
            }
            val recipeIngredientsInput = dialogView.findViewById<EditText>(R.id.recipeIngredientsInput).apply {
                setText(recipe.ingredients)
            }
            val recipeInstructionsInput = dialogView.findViewById<EditText>(R.id.recipeInstructionsInput).apply {
                setText(recipe.instructions)
            }

            AlertDialog.Builder(itemView.context)
                .setTitle("Edit Recipe")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    val newName = recipeNameInput.text.toString().trim()
                    val newDescription = recipeDescriptionInput.text.toString().trim()
                    val newTime = recipeTimeInput.text.toString().trim()
                    val newIngredients = recipeIngredientsInput.text.toString().trim()
                    val newInstructions = recipeInstructionsInput.text.toString().trim()
                    if (newName.isNotEmpty() && newDescription.isNotEmpty()) {
                        onItemRename(recipe, newName, newDescription, newTime, newIngredients, newInstructions)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}