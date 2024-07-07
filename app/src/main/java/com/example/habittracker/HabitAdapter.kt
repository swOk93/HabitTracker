package com.example.habittracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Адаптер для RecyclerView
class HabitAdapter(
    private var habitList: List<Habit>,
    private val onHabitClick: (Habit, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    // ViewHolder для привычек
    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView) // Находим TextView в разметке элемента списка
    }
    // Создаем ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            // Инфлейтим разметку элемента списка (процесс преобразования XML-разметки в объекты View)
            // Когда вы создаете пользовательский интерфейс в XML-файле, этот файл является просто описанием.
            // Чтобы использовать его в коде, его нужно преобразовать (или "инфлейтить") в фактические объекты
            // View, которые можно отображать на экране и с которыми можно взаимодействовать.
            .inflate(R.layout.habit_item, parent, false) // Возвращаем новый ViewHolder
        return HabitViewHolder(view)
    }
    // Привязываем данные к ViewHolder
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        // Получаем текущую привычку
        holder.textView.text = habit.name // Устанавливаем текст в TextView
        holder.itemView.setOnClickListener {
            onHabitClick(habit, position)
        }
    }
    // Возвращаем количество элементов в списке
    override fun getItemCount() = habitList.size
    // Метод для обновления списка привычек
    fun updateHabits(newHabits: List<Habit>) {
        habitList = newHabits
        notifyDataSetChanged()   // Уведомляем адаптер об изменениях в данных
    }
}
