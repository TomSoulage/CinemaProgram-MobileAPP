package com.example.androidproject.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,
        var picturePath: String? = null,
        var publishedDateTime: LocalDateTime ?= null,
        var read: Boolean ?= false
)
