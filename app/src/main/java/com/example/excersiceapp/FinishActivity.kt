package com.example.excersiceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.excersiceapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding : ActivityFinishBinding? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbFinshedactivity)



        if(supportActionBar!=null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbFinshedactivity?.setNavigationOnClickListener{

            onBackPressed()
        }

        binding?.btnfinishedBack?.setOnClickListener{
           finish()

        }
        val dao=(application as WorkoutApp).db.getHistoryDao()
        addDatetoDatabase(dao)


    }
private  fun addDatetoDatabase(entityDao: EntityDao)
{
    val c = Calendar.getInstance()
val datetime =c.time

    val sdf=SimpleDateFormat("dd MMM yyyy HH :mm:ss", Locale.getDefault())
    val date=sdf.format((datetime))
    lifecycleScope.launch{

        entityDao.insert(HistoryEntity(data = "$date", id = 0))
        Log.e(
            "Date : ",
            "Added..."
        )
    }

}

}