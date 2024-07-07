package com.example.habittracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class HabitDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_detail) // Устанавливаем разметку активности

        val editTextHabitName: EditText = findViewById(R.id.editTextHabitName)
        val buttonSaveHabit: Button = findViewById(R.id.buttonSaveHabit)

        // Получение данных о привычке из Intent
        val habitName = intent.getStringExtra("HABIT_NAME")
        val habitPosition = intent.getIntExtra("HABIT_POSITION", -1)

        // Если данные о привычке существуют, заполняем поле ввода
        if (habitName != null) {
            editTextHabitName.setText(habitName)
        }

        buttonSaveHabit.setOnClickListener {
            val newHabitName = editTextHabitName.text.toString()
            if (newHabitName.isNotEmpty()) {
                // Создание Intent для передачи данных обратно в MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("HABIT_NAME", newHabitName)
                resultIntent.putExtra("HABIT_POSITION", habitPosition)
                setResult(RESULT_OK, resultIntent)
                finish() // Закрываем активность после сохранения
            }
        }
    }
}
