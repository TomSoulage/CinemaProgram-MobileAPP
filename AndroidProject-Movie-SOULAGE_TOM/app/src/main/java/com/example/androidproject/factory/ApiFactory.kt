package com.example.androidproject.factory

import retrofit2.Retrofit
import com.example.androidproject.api.PointsApi
import retrofit2.converter.gson.GsonConverterFactory


private const val POINTS_URL = "https://movies-cb1aa-default-rtdb.firebaseio.com/"

private var pointsApi : PointsApi?= null

fun getPointsApi() : PointsApi {
    if(pointsApi == null){
        val retrofit = Retrofit.Builder()
            .baseUrl(POINTS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pointsApi = retrofit.create(PointsApi::class.java)
    }
    return pointsApi!!
}



