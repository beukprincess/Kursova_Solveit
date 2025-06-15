package com.example.solve_it

import android.content.Context
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.io.FileOutputStream


object ImageUploader {

    interface ApiService {
        @Multipart
        @POST("/upload")
        fun uploadImage(
            @Part image: MultipartBody.Part
        ): Call<ResponseBody>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://c98e-31-128-190-108.ngrok-free.app")
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun upload(context: Context, imageFile: File, onSuccess: () -> Unit) {
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

        apiService.uploadImage(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()
                    val responseObj: JSONObject = JSONObject()
                    val outputFile = File(context.filesDir, "result.json")
                    FileOutputStream(outputFile).use { fileOut ->
                        inputStream?.copyTo(fileOut)
                    }
                    onSuccess()
                } else {
                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}