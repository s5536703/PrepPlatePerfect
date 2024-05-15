package com.example.prepplateperfect.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imageLogo.setImageResource(R.drawable.prepplateperfect)
        binding.textTitle.text = "PrepPlatePerfect"
        binding.textWelcome.text = "Welcome to PrepPlatePerfect!"
        binding.textGoal.text = "Our goal is to help you plan, prepare, and perfect your meals with ease. Enjoy a seamless cooking experience with our app!"
        binding.textTipOfTheWeekTitle.text = "Tip of the Week"
        binding.textTipOfTheWeek.text = "Always preheat your oven to ensure even cooking."

        homeViewModel.recipeOfTheWeek.observe(viewLifecycleOwner) { recipe ->
            binding.recipeOfTheWeekDescription.text = recipe.description
            binding.recipeOfTheWeekInfoButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("recipeId", recipe.id)
                    putString("recipeName", recipe.name)
                    putString("recipeDescription", recipe.description)
                    putString("cookingTime", recipe.time)
                    putString("recipeIngredients", recipe.ingredients)
                    putString("recipeInstructions", recipe.instructions)
                }
                findNavController().navigate(R.id.action_homeFragment_to_mealInformationFragment, bundle)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}