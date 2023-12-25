package hse.example.lab3

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsView(item: View) : RecyclerView.ViewHolder(item) {
    val newsTitle: TextView = item.findViewById<TextView>(R.id.newsTitle)
    val newsImage: ImageView = item.findViewById<ImageView>(R.id.newsImage)
    val newsDescription : TextView = itemView.findViewById<TextView>(R.id.newsDescription)
}