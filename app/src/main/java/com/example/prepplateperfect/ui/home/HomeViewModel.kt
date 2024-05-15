package com.example.prepplateperfect.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prepplateperfect.ui.cookbook.Recipe

class HomeViewModel : ViewModel() {

    private val _recipeOfTheWeek = MutableLiveData<Recipe>().apply {
        value = Recipe(
            id = "1",
            name = "Recipe of the Week",
            description = "Delicious homemade pizza with fresh ingredients.",
            time = "30 mins",
            ingredients = "Dough, Tomato Sauce, Cheese, Pepperoni",
            instructions = "1. Preheat oven. 2. Prepare dough. 3. Spread sauce. 4. Add toppings. 5. Bake."
        )
    }
    val recipeOfTheWeek: LiveData<Recipe> = _recipeOfTheWeek
}