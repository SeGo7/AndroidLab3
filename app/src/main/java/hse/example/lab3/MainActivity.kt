package hse.example.lab3

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private lateinit var productAdapter: NotesClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        val recyclerView: RecyclerView = findViewById(R.id.productList)

        productAdapter = NotesClass(ArrayList())
        recyclerView.adapter = productAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
    }

    fun save(view: View) {
        val productNameEditText: EditText = findViewById(R.id.productName)
        val productName: String = productNameEditText.text.toString()
        productNameEditText.setText("")

        productAdapter.save(productName)
    }

    fun clear(view: View) {
        productAdapter.clearAll()
    }
}