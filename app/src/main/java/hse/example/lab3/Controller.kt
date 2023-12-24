package hse.example.lab3

import com.google.gson.Gson;
import com.google.gson.GsonBuilder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.Consumer


class Controller : Callback<List<String?>> {
    fun start() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val newdataApi: NewDataApi = retrofit.create(NewDataApi::class.java)
        val call: Call<List<String>> = newdataApi.getData("status:open")
        call.enqueue(this)
    }

    override fun onResponse(call: Call<List<String?>>, response: Response<List<String?>>) {
        if (response.isSuccessful) {
            val changesList: List<String?> = response.body()!!
            changesList.forEach(Consumer<String> { change: String ->
                System.out.println(
                    change.subject
                )
            })
        } else {
            println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<List<String?>>, t: Throwable) {
        t.printStackTrace()
    }

    companion object {
        const val BASE_URL = "https://git.eclipse.org/r/"
    }
}