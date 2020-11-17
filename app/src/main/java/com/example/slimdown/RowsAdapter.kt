package com.example.slimdown

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.slimdown.Class.Weights
import kotlinx.android.synthetic.main.recycler_table_row.view.*

class RowsAdapter(private val context: Context): RecyclerView.Adapter<RowsAdapter.MyHolder>(){

    private var rowsRecyclerView = emptyList<Weights>()

    fun getData(data : List<Weights>) {
        rowsRecyclerView = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.recycler_table_row, parent, false))
    }

    override fun getItemCount(): Int {
        return rowsRecyclerView.count()
    }

    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindView( weights: Weights) {
            itemView.dateRecyclerView.text = weights.date.toString()
            itemView.weightRecyclerView.text = weights.weight.toString()
        }
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = rowsRecyclerView[position]
        holder.bindView(currentItem)
    }
}