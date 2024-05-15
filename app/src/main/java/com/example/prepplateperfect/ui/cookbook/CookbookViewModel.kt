package com.example.prepplateperfect.ui.cookbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val time: String,
    val ingredients: String,
    val instructions: String,
    val rating: Double = 0.0
)

class CookbookViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>(mutableListOf())
    val recipes: MutableLiveData<List<Recipe>> = _recipes

    fun addRecipe(recipe: Recipe) {
        val updatedRecipes = _recipes.value?.toMutableList() ?: mutableListOf()
        updatedRecipes.add(recipe)
        _recipes.value = updatedRecipes
    }

    fun deleteRecipe(recipeId: String) {
        val updatedRecipes = _recipes.value?.toMutableList() ?: mutableListOf()
        updatedRecipes.removeAll { it.id == recipeId }
        _recipes.value = updatedRecipes
    }

    fun updateRecipe(recipe: Recipe, newName: String, newDescription: String, newTime: String, newIngredients: String, newInstructions: String) {
        val updatedRecipes = _recipes.value?.toMutableList() ?: mutableListOf()
        val index = updatedRecipes.indexOfFirst { it.id == recipe.id }
        if (index != -1) {
            updatedRecipes[index] = recipe.copy(
                name = newName,
                description = newDescription,
                time = newTime,
                ingredients = newIngredients,
                instructions = newInstructions
            )
            _recipes.value = updatedRecipes
        }
    }

    init {
        displayRecipes()
    }

    private fun displayRecipes() {
        _recipes.value = listOf(
            Recipe(
                id = "1",
                name = "Example Recipe",
                description = "Example description that describes the example recipe.",
                time = "example time mins",
                ingredients = "Example ingredient, Example ingredient, Example ingredient, Example ingredient, Example ingredient, Example ingredient, Example ingredient",
                instructions = "Example instructions that tell you how to cook example recipe."
            )
        )
    }
}