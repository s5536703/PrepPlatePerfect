package com.example.prepplateperfect.ui.cookbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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