package com.example.prepplateperfect.ui.organiser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

data class MealReminder(
    val id: String,
    val mealName: String,
    val mealTime: String
)

class OrganiserViewModel : ViewModel() {
    private val _mealReminders = MutableLiveData<Map<Calendar, List<MealReminder>>>(mapOf())

    fun addMealReminder(day: Calendar, mealReminder: MealReminder) {
        val dayKey = Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val updatedReminders = _mealReminders.value?.toMutableMap() ?: mutableMapOf()
        val remindersForDay = updatedReminders[dayKey]?.toMutableList() ?: mutableListOf()
        remindersForDay.add(mealReminder)
        updatedReminders[dayKey] = remindersForDay
        _mealReminders.value = updatedReminders
    }

    fun removeMealReminder(day: Calendar, mealReminder: MealReminder) {
        val dayKey = Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val updatedReminders = _mealReminders.value?.toMutableMap() ?: mutableMapOf()
        val remindersForDay = updatedReminders[dayKey]?.toMutableList() ?: mutableListOf()
        remindersForDay.remove(mealReminder)
        updatedReminders[dayKey] = remindersForDay
        _mealReminders.value = updatedReminders
    }

    fun updateMealReminder(mealReminder: MealReminder, newName: String, newTime: String) {
        _mealReminders.value?.let { currentReminders ->
            val updatedReminders = currentReminders.toMutableMap()
            currentReminders.forEach { (day, reminders) ->
                val index = reminders.indexOfFirst { it.id == mealReminder.id }
                if (index != -1) {
                    val updatedRemindersForDay = reminders.toMutableList()
                    updatedRemindersForDay[index] = mealReminder.copy(mealName = newName, mealTime = newTime)
                    updatedReminders[day] = updatedRemindersForDay
                }
            }
            _mealReminders.value = updatedReminders
        }
    }

    fun getMealRemindersForDay(day: Calendar): List<MealReminder> {
        val dayKey = Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return _mealReminders.value?.get(dayKey) ?: emptyList()
    }

    init {
        displayMealReminders()
    }

    private fun displayMealReminders() {
        val sampleDate = Calendar.getInstance().apply {
            set(2024, Calendar.MAY, 15, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        _mealReminders.value = mapOf(
            sampleDate to listOf(
                MealReminder(
                    id = "1",
                    mealName = "Example event",
                    mealTime = "16:00"
                )
            )
        )
    }
}