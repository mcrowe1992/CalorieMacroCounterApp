package com.example.caloriemacrocounter

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriemacrocounterapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var etProtein: EditText
    private lateinit var etCarbs: EditText
    private lateinit var etFat: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView
    private lateinit var rvFoodItems: RecyclerView
    private lateinit var foodItemAdapter: FoodItemAdapter

    private val foodItems = listOf(
        FoodItem("Apple", 0.3, 25.0, 0.2),
        FoodItem("Banana", 1.3, 27.0, 0.3),
        FoodItem("Chicken Breast", 31.0, 0.0, 3.6)
        // Add more food items here
    )

    private var totalProtein = 0.0
    private var totalCarbs = 0.0
    private var totalFat = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etProtein = findViewById(R.id.et_protein)
        etCarbs = findViewById(R.id.et_carbs)
        etFat = findViewById(R.id.et_fat)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
        rvFoodItems = findViewById(R.id.rv_food_items)

        rvFoodItems.layoutManager = LinearLayoutManager(this)
        foodItemAdapter = FoodItemAdapter(foodItems) { foodItem ->
            addFoodItem(foodItem)
        }
        rvFoodItems.adapter = foodItemAdapter

        loadSavedValues()

        btnCalculate.setOnClickListener {
            calculateAndDisplayCalories()
        }
    }

    private fun calculateAndDisplayCalories() {
        val protein = etProtein.text.toString().toDoubleOrNull() ?: 0.0
        val carbs = etCarbs.text.toString().toDoubleOrNull() ?: 0.0
        val fat = etFat.text.toString().toDoubleOrNull() ?: 0.0

        totalProtein += protein
        totalCarbs += carbs
        totalFat += fat

        saveValues(totalProtein, totalCarbs, totalFat)

        val calories = calculateCalories(totalProtein, totalCarbs, totalFat)
        tvResult.text = "Total Calories: $calories kcal"
    }

    private fun calculateCalories(protein: Double, carbs: Double, fat: Double): Double {
        val proteinCalories = protein * 4
        val carbsCalories = carbs * 4
        val fatCalories = fat * 9
        return proteinCalories + carbsCalories + fatCalories
    }

    private fun saveValues(protein: Double, carbs: Double, fat: Double) {
        val sharedPreferences = getSharedPreferences("CalorieMacroCounter", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putFloat("protein", protein.toFloat())
            putFloat("carbs", carbs.toFloat())
            putFloat("fat", fat.toFloat())
            apply()
        }
    }

    private fun loadSavedValues() {
        val sharedPreferences = getSharedPreferences("CalorieMacroCounter", Context.MODE_PRIVATE)
        totalProtein = sharedPreferences.getFloat("protein", 0f).toDouble()
        totalCarbs = sharedPreferences.getFloat("carbs", 0f).toDouble()
        totalFat = sharedPreferences.getFloat("fat", 0f).toDouble()

        etProtein.setText(totalProtein.toString())
        etCarbs.setText(totalCarbs.toString())
        etFat.setText(totalFat.toString())
    }

    private fun addFoodItem(foodItem: FoodItem) {
        totalProtein += foodItem.protein
        totalCarbs += foodItem.carbs
        totalFat += foodItem.fat

        saveValues(totalProtein, totalCarbs, totalFat)

        val calories = calculateCalories(totalProtein, totalCarbs, totalFat)
        tvResult.text = "Total Calories: $calories kcal"
    }
}
