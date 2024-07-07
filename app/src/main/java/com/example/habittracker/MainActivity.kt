package com.example.habittracker

import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
import android.widget.Button
//import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    // Получаем ViewModel с использованием делегата viewModels()
    private val habitViewModel: HabitViewModel by viewModels()

    // Регистрация Activity Result Launcher для добавления и редактирования привычек
    private val habitDetailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val habitName = result.data?.getStringExtra("HABIT_NAME")
            val habitPosition = result.data?.getIntExtra("HABIT_POSITION", -1)

            if (habitName != null && habitPosition != null && habitPosition >= 0) {
                habitViewModel.updateHabit(habitPosition, Habit(habitName))
            } else if (habitName != null) {
                habitViewModel.addHabit(Habit(habitName))
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Находим RecyclerView в разметке
        recyclerView.layoutManager = LinearLayoutManager(this) // Устанавливаем LayoutManager для RecyclerView

        val adapter = HabitAdapter(emptyList(), this::onHabitClick) // Создаем адаптер и передаем список привычек
        recyclerView.adapter = adapter // Устанавливаем адаптер для RecyclerView

        // Наблюдаем за изменениями в списке привычек
        habitViewModel.habits.observe(this, Observer { habits ->
            // Обновляем адаптер при изменении данных
            adapter.updateHabits(habits)
        })

        // Находим кнопку для добавления привычек
        val buttonAddHabit: Button = findViewById(R.id.buttonAddHabit)
        buttonAddHabit.setOnClickListener {
            val intent = Intent(this, HabitDetailActivity::class.java)
            habitDetailLauncher.launch(intent)
        }
        // проба длинного нажатия
        buttonAddHabit.setOnLongClickListener {
            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
            true
        }
    }
    private fun onHabitClick(habit: Habit, position: Int) {
        val intent = Intent(this, HabitDetailActivity::class.java)
        intent.putExtra("HABIT_NAME", habit.name)
        intent.putExtra("HABIT_POSITION", position)
        habitDetailLauncher.launch(intent)
    }
}

// Модель данных для привычки
data class Habit(val name: String)

// Адаптер для RecyclerView
//class HabitAdapter(private var habitList: List<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
//
//    // ViewHolder для привычек
//    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textView: TextView = itemView.findViewById(R.id.textView) // Находим TextView в разметке элемента списка
//    }
//
//    // Создаем ViewHolder
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            // Инфлейтим разметку элемента списка (процесс преобразования XML-разметки в объекты View)
//            // Когда вы создаете пользовательский интерфейс в XML-файле, этот файл является просто описанием.
//            // Чтобы использовать его в коде, его нужно преобразовать (или "инфлейтить") в фактические объекты
//            // View, которые можно отображать на экране и с которыми можно взаимодействовать.
//            .inflate(R.layout.habit_item, parent, false)
//        return HabitViewHolder(view) // Возвращаем новый ViewHolder
//    }
//
//    // Привязываем данные к ViewHolder
//    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
//        val habit = habitList[position] // Получаем текущую привычку
//        holder.textView.text = habit.name // Устанавливаем текст в TextView
//    }
//
//    // Возвращаем количество элементов в списке
//    override fun getItemCount() = habitList.size
//
//
//    // Метод для обновления списка привычек
//    fun updateHabits(newHabits: List<Habit>) {
//        habitList = newHabits
//        notifyDataSetChanged() // Уведомляем адаптер об изменениях в данных
//    }
//}

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