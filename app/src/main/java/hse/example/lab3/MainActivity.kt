package hse.example.lab3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL


class MainActivity : AppCompatActivity() {


    private var apiKey : String = "pub_35345a830e8ba3121b7360cb00f45b2c015a5"

    private var newsImages: MutableList<URL> = ArrayList()
    private var newsTitles: MutableList<String> = ArrayList()
    private var newsDescription: MutableList<String> = ArrayList()
    private var newsSource: MutableList<URL> = ArrayList()

    private var currentJob: Job? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NotesClass
    private lateinit var apiService: ApiService

    private lateinit var keyEditText: EditText;
    private lateinit var searchButton: Button;

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    interface ApiService {
        @GET("news?")
        suspend fun getNews(@Query("apikey") apiKey: String, @Query("q") query: String): NewReq
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsdata.io/api/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            apiService = retrofit.create(ApiService::class.java)
            Toast.makeText(applicationContext, "Successful initialize", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Initialize failed", Toast.LENGTH_SHORT).show()
        }

        keyEditText = findViewById(R.id.keywordsForSearch)
        searchButton = findViewById(R.id.searchButton)
        recyclerView = findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NotesClass(newsImages, newsTitles, newsDescription, newsSource)

        recyclerView.adapter = newsAdapter

        searchButton.setOnClickListener(View.OnClickListener {
            val keyword = keyEditText.text.toString()
            if (keyword.isNotEmpty()) {

                currentJob = CoroutineScope(Dispatchers.Main).launch {
                    sendRequest(keyword)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun sendRequest(request: String){
        try {
            val news = withContext(Dispatchers.IO){
                apiService.getNews(apiKey, request.toString())
            }

            for (art in news.results){
                newsImages.add(0, art.imageURL)
                newsAdapter?.notifyItemInserted(newsImages.size - 1);
                newsTitles.add(0, art.title)
                newsAdapter?.notifyItemInserted(newsTitles.size - 1);
                newsDescription.add(0,art.description)
                newsAdapter?.notifyItemInserted(newsDescription.size - 1);
                newsSource.add(0,art.link)
                newsAdapter?.notifyItemInserted(newsSource.size - 1);
            }

            newsAdapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(0)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext,"FAIL", Toast.LENGTH_SHORT).show()
        }
    }
}