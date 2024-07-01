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

        buttonSaveHabit.setOnClickListener {
            val habitName = editTextHabitName.text.toString()
            if (habitName.isNotEmpty()) {
                // Создание Intent для передачи данных обратно в MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("HABIT_NAME", habitName)
                setResult(RESULT_OK, resultIntent)
                finish() // Закрываем активность после сохранения
            }
        }
    }
}
