package com.example.androidproject

import com.example.androidproject.api.Point

interface MapContract {
    interface MapModel {
        interface  OnPointsLoadedListener{
            fun onPointsLoaded(points: Collection<Point>)
        }
        fun loadPoints(listener: OnPointsLoadedListener)
    }
    interface MapView{
        fun addPoints(points: Collection<Point>)
    }
    interface MapPresenter{
        fun requestToLoadPoints()
        fun onDestroy()
    }
}