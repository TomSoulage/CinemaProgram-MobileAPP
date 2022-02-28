package com.example.androidproject.model

import android.util.Log
import com.example.androidproject.MapContract
import com.example.androidproject.api.Point
import com.example.androidproject.api.PointsApi
import com.example.androidproject.factory.getPointsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapModelImpl : MapContract.MapModel {
    override fun loadPoints(listener: MapContract.MapModel.OnPointsLoadedListener) {
        val pointsApi = getPointsApi()
        pointsApi.getPoints().enqueue(object: Callback<Map<String, Point>>{
            override fun onResponse(
                call: Call<Map<String, Point>>,
                response: Response<Map<String, Point>>
            ) {
                if(response.body() != null){
                    listener.onPointsLoaded(response.body()!!.values)
                }
            }

            override fun onFailure(call: Call<Map<String, Point>>, t: Throwable) {
                Log.d(javaClass.name,t.message,t)
            }
        })
    }
}