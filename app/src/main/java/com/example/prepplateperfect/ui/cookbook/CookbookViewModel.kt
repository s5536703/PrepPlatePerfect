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

    init {
        displayRecipes()
    }

    private fun displayRecipes() {
        _recipes.value = listOf(
            Recipe(
                id = "1",
                name = "Spaghetti Bolognese",
                description = "Rich tomato sauce with ground beef over spaghetti.",
                time = "45 mins",
                ingredients = ",Ground beef, Onions, Garlic, Tomato sauce, Spaghetti, s" +
                        "Salt, pepper, olive oil",
                instructions = "Brown the beef. Sauté onions and garlic, add tomato sauce, cook spaghetti, mix and serve."
            ),
            Recipe(
                id = "2",
                name = "Chicken Curry",
                description = ",Creamy curry with tender chicken pieces and spices.",
                time = "1 hour",
                ingredients = ",Chicken breast, curry paste, coconut milk, onions, garlic, ginger, oil, salt",
                instructions = "Cook chicken with curry paste, add coconut milk and simmer. Serve with rice."
            ),
            Recipe(
                id = "3",
                name = "Vegetable Stir Fry",
                description = "Fresh veggies sautéed with soy sauce and sesame oil.",
                time = "30 mins",
                ingredients = ",Bell peppers, broccoli, carrots, soy sauce, sesame oil, garlic, ginger",
                instructions = "Stir fry vegetables with garlic and ginger in sesame oil, add soy sauce, serve hot."
            ),
            Recipe(
                id = "4",
                name = "Beef Stew",
                description = "Slow-cooked beef stew with root vegetables.",
                time = "3 hours",
                ingredients = ",Beef chuck, potatoes, carrots, onions, beef broth, thyme, salt, pepper",
                instructions = "Brown beef, add all ingredients into a pot, simmer for 2.5 hours."
            ),
            Recipe(
                id = "5",
                name = "Caesar Salad",
                description = ",Classic Caesar salad with homemade dressing and croutons.",
                time = "25 mins",
                ingredients = "Romaine lettuce, Parmesan cheese, croutons, Caesar dressing, anchovies, lemon juice",
                instructions = "Mix lettuce with dressing, top with croutons and cheese."
            ),
            Recipe(
                id = "6",
                name = "Pan-seared Salmon",
                description = ",Crispy skin salmon with a lemon butter sauce.",
                time = "20 mins",
                ingredients = ",Salmon fillets, butter, lemon, parsley, garlic, salt, pepper",
                instructions = "Sear salmon skin-side down, flip, add lemon butter sauce, and serve."
            ),
            Recipe(
                id = "7",
                name = "Mushroom Risotto",
                description = ",Creamy risotto with Parmesan cheese and wild mushrooms.",
                time = "1 hour",
                ingredients = "Arborio rice, chicken stock, wild mushrooms, onions, garlic, Parmesan cheese, white wine",
                instructions = "Cook mushrooms, set aside. Sauté onions, garlic, add rice, wine, and stock gradually."
            )
        )
    }
}