package com.example.prepplateperfect.ui.cookbook

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val time: String,
    val ingredients: String,
    val instructions: String,
    val rating: Double = 0.0
)