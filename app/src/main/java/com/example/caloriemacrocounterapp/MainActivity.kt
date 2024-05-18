package com.example.caloriemacrocounter

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caloriemacrocounterapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var etProtein: EditText
    private lateinit var etCarbs: EditText
    private lateinit var etFat: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etProtein = findViewById(R.id.et_protein)
        etCarbs = findViewById(R.id.et_carbs)
        etFat = findViewById(R.id.et_fat)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        loadSavedValues()

        btnCalculate.setOnClickListener {
            val protein = etProtein.text.toString().toDoubleOrNull()
            val carbs = etCarbs.text.toString().toDoubleOrNull()
            val fat = etFat.text.toString().toDoubleOrNull()

            if (protein == null || carbs == null || fat == null) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveValues(protein, carbs, fat)

            val calories = calculateCalories(protein, carbs, fat)
            tvResult.text = "Total Calories: $calories kcal"
        }
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
        val protein = sharedPreferences.getFloat("protein", 0f)
        val carbs = sharedPreferences.getFloat("carbs", 0f)
        val fat = sharedPreferences.getFloat("fat", 0f)

        if (protein != 0f) etProtein.setText(protein.toString())
        if (carbs != 0f) etCarbs.setText(carbs.toString())
        if (fat != 0f) etFat.setText(fat.toString())
    }
}