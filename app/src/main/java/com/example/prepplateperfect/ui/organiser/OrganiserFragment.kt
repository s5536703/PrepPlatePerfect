package com.example.prepplateperfect.ui.organiser

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prepplateperfect.R
import com.example.prepplateperfect.databinding.FragmentOrganiserBinding
import java.util.*

class OrganiserFragment : Fragment() {
    private var _binding: FragmentOrganiserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrganiserViewModel by viewModels()
    private lateinit var mealAdapter: AdapterOrganiser
    private var selectedDate: Calendar = Calendar.getInstance()
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrganiserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendar()
        setupRecyclerView()
        setupAddMealButton()
        setupEditModeButton()
        updateMealReminders(selectedDate)
    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            updateMealReminders(selectedDate)
        }
    }

    private fun setupRecyclerView() {
        mealAdapter = AdapterOrganiser(
            items = emptyList(),
            onDelete = { mealReminder ->
                viewModel.removeMealReminder(selectedDate, mealReminder)
                updateMealReminders(selectedDate)
            },
            onItemRename = { mealReminder, newName, newTime ->
                viewModel.updateMealReminder(mealReminder, newName, newTime)
                updateMealReminders(selectedDate)
            },
            isEditMode = isEditMode
        )
        binding.mealRecyclerView.apply {
            adapter = mealAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupAddMealButton() {
        binding.addMealButton.setOnClickListener {
            showAddMealDialog()
        }
    }

    private fun setupEditModeButton() {
        binding.toggleEditModeButton.setOnClickListener {
            toggleEditMode()
        }
    }

    private fun showAddMealDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_meal, null)
        val mealNameInput = dialogView.findViewById<EditText>(R.id.mealNameInput)
        val mealTimeInput = dialogView.findViewById<EditText>(R.id.mealTimeInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Meal Reminder")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val mealName = mealNameInput.text.toString().trim()
                val mealTime = mealTimeInput.text.toString().trim()
                if (mealName.isNotEmpty() && mealTime.isNotEmpty()) {
                    val mealReminder = MealReminder(UUID.randomUUID().toString(), mealName, mealTime)
                    viewModel.addMealReminder(selectedDate, mealReminder)
                    updateMealReminders(selectedDate)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        mealAdapter.toggleEditMode(isEditMode)
        binding.addMealButton.visibility = if (isEditMode) View.VISIBLE else View.GONE
        binding.toggleEditModeButton.text = if (isEditMode) "Done" else "Edit"
    }

    private fun updateMealReminders(calendar: Calendar) {
        val mealReminders = viewModel.getMealRemindersForDay(calendar)
        mealAdapter.updateItems(mealReminders)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}