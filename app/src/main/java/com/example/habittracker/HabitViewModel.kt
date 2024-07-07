package com.example.habittracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ViewModel для управления данными привычек
class HabitViewModel : ViewModel() {
    // Приватное MutableLiveData для хранения списка привычек
    private val _habits = MutableLiveData<MutableList<Habit>>(mutableListOf())

    // Публичное LiveData для доступа к списку привычек
    val habits: LiveData<MutableList<Habit>> = _habits

    // Метод для добавления новой привычки
    fun addHabit(habit: Habit) {
        _habits.value?.add(habit)
        _habits.value = _habits.value // Обновляем LiveData
    }
    fun updateHabit(position: Int, newHabit: Habit) {
        _habits.value?.set(position, newHabit)
        _habits.value = _habits.value
    }
}
