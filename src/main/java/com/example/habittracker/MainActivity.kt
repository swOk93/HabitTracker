package com.example.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    // Переменная для хранения списка привычек
    private val habits = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Находим RecyclerView в разметке
        recyclerView.layoutManager = LinearLayoutManager(this) // Устанавливаем LayoutManager для RecyclerView

        val adapter = HabitAdapter(habits)
        recyclerView.adapter = adapter // Устанавливаем адаптер для RecyclerView

        // Находим кнопку для добавления привычек
        val buttonAddHabit: Button = findViewById(R.id.buttonAddHabit)
        buttonAddHabit.setOnClickListener {
            // Добавляем новую привычку при нажатии на кнопку
            habits.add(Habit("New Habit"))
            adapter.notifyItemInserted(habits.size - 1) // Уведомляем адаптер о новом элементе
        }


//        enableEdgeToEdge()
//        setContent {
//            HabitTrackerTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
    }
    // Метод для добавления новой привычки
//    private fun addHabit() {
//        val habitNameInput = EditText(this) // Создаем EditText для ввода названия привычки
//        AlertDialog.Builder(this)
//            .setTitle("New Habit")
//            .setView(habitNameInput)
//            .setPositiveButton("Add") { dialog, _ ->
//                val habitName = habitNameInput.text.toString()
//                if (habitName.isNotEmpty()) {
//                    habitList.add(Habit(habitName)) // Добавляем новую привычку в список
//                    habitAdapter.notifyItemInserted(habitList.size - 1) // Уведомляем адаптер об изменении данных
//                } else {
//                    Toast.makeText(this, "Habit name cannot be empty", Toast.LENGTH_SHORT).show()
//                }
//                dialog.dismiss()
//            }
//            .setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .create()
//            .show()
//    }
}

// Модель данных для привычки
data class Habit(val name: String)

// Адаптер для RecyclerView
class HabitAdapter(private val habitList: List<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    // ViewHolder для привычек
    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView) // Находим TextView в разметке элемента списка
    }

    // Создаем ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_item, parent, false) // Инфлейтим разметку элемента списка
        return HabitViewHolder(view) // Возвращаем новый ViewHolder
    }

    // Привязываем данные к ViewHolder
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position] // Получаем текущую привычку
        holder.textView.text = habit.name // Устанавливаем текст в TextView
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount() = habitList.size
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HabitTrackerTheme {
        Greeting("Android")
    }
}