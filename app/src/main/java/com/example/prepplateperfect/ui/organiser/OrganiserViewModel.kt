package com.example.prepplateperfect.ui.organiser

import androidx.lifecycle.ViewModel
import java.util.*

class OrganiserViewModel : ViewModel() {
    private val mealReminders: MutableMap<Calendar, MutableList<MealReminder>> = mutableMapOf()

    fun addMealReminder(day: Calendar, mealReminder: MealReminder) {
        val dayKey = Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (!mealReminders.containsKey(dayKey)) {
            mealReminders[dayKey] = mutableListOf()
        }
        mealReminders[dayKey]?.add(mealReminder)
    }

    fun removeMealReminder(day: Calendar, mealReminder: MealReminder) {
        val dayKey = Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        mealReminders[dayKey]?.remove(mealReminder)
    }

    fun updateMealReminder(mealReminder: MealReminder, newName: String, newTime: String) {
        for ((key, value) in mealReminders) {
            val index = value.indexOfFirst { it.id == mealReminder.id }
            if (index != -1) {
                value[index] = mealReminder.copy(mealName = newName, mealTime = newTime)
                break
            }
        }
    }

    fun getMealRemindersForDay(day: Calendar): List<MealReminder> {
        val dayKey = Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return mealReminders[dayKey] ?: emptyList()
    }
}