package com.example.caloriemacrocounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriemacrocounterapp.R

class FoodItemAdapter(
    private val foodItems: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {

    inner class FoodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvMacros: TextView = itemView.findViewById(R.id.tv_macros)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem = foodItems[position]
        holder.tvName.text = foodItem.name
        holder.tvMacros.text = "Protein: ${foodItem.protein}, Carbs: ${foodItem.carbs}, Fat: ${foodItem.fat}"
        holder.itemView.setOnClickListener { onItemClick(foodItem) }
    }

    override fun getItemCount() = foodItems.size
}

data class FoodItem (
    val name: String,
    val fat: Double,
    val carbs: Double,
    val protein: Double
    )

