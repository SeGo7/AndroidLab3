package hse.example.lab3

import retrofit2.Call
import retrofit2.http.GET

interface NewDataApi {
    @GET("endpoint") // указываем путь к API методу
    fun getData(status : String): Call<List<String>> // YourResponseModel - модель ответа сервера
}