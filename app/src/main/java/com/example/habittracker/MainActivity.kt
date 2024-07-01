package com.example.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.content.Intent
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

    // Регистрация Activity Result Launcher для получения результата от HabitDetailActivity
    private val addHabitLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.getStringExtra("HABIT_NAME")?.let { habitName ->
                habitViewModel.addHabit(Habit(habitName))
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Находим RecyclerView в разметке
        recyclerView.layoutManager = LinearLayoutManager(this) // Устанавливаем LayoutManager для RecyclerView

        val adapter = HabitAdapter(emptyList()) // Создаем адаптер и передаем список привычек
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
            addHabitLauncher.launch(intent)
        }
//        buttonAddHabit.setOnClickListener {
//            // Открываем HabitDetailActivity для добавления новой привычки
//            val intent = Intent(this, HabitDetailActivity::class.java)
//            startActivity(intent)
//            // Добавляем новую привычку через ViewModel
////            habitViewModel.addHabit(Habit("New Habit"))
////            adapter.notifyItemInserted(habits.size - 1) // Уведомляем адаптер о новом элементе
//        }


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
class HabitAdapter(private var habitList: List<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

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
            .inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view) // Возвращаем новый ViewHolder
    }

    // Привязываем данные к ViewHolder
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position] // Получаем текущую привычку
        holder.textView.text = habit.name // Устанавливаем текст в TextView
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount() = habitList.size


    // Метод для обновления списка привычек
    fun updateHabits(newHabits: List<Habit>) {
        habitList = newHabits
        notifyDataSetChanged() // Уведомляем адаптер об изменениях в данных
    }
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