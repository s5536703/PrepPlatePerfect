package com.example.prepplateperfect.ui.discover

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentDiscoverItemBinding
import com.example.prepplateperfect.ui.cookbook.Recipe
import java.util.*
import kotlin.collections.ArrayList

class AdapterDiscover(
    private var recipes: List<Recipe>
) : RecyclerView.Adapter<AdapterDiscover.RecipeViewHolder>(), Filterable {

    private var filteredRecipes: List<Recipe> = recipes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = FragmentDiscoverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = filteredRecipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = filteredRecipes.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        filteredRecipes = recipes
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredRecipes = if (charSearch.isEmpty()) {
                    recipes
                } else {
                    val resultList = ArrayList<Recipe>()
                    for (row in recipes) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) ||
                            row.description.lowercase(Locale.ROOT).contains(charSearch.lowercase(
                                Locale.ROOT
                            ))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredRecipes
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredRecipes = results?.values as List<Recipe>
                notifyDataSetChanged()
            }
        }
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