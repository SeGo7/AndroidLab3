package hse.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesClass(private val notes: MutableList<String>) : RecyclerView.Adapter<NotesClass.NotesViewHolder>() {

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cart: TextView = itemView.findViewById(R.id.note)
        val madeButton: Button = itemView.findViewById(R.id.del_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.cart.text = notes[position]
        holder.madeButton.setOnClickListener { clear(position) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun save(productName: String) {
        notes.add(productName)
        notifyDataSetChanged()
    }

    fun clearAll() {
        notes.clear()
        notifyDataSetChanged()
    }

    private fun clear(position: Int) {
        notes.removeAt(position)
        notifyDataSetChanged()
    }
}