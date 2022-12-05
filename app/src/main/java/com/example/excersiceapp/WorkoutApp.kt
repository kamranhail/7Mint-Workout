package com.example.excersiceapp

import android.app.Application

class WorkoutApp  : Application(){

    val db by lazy{

HistoryDatabase.getDatabase(this)
    }
}