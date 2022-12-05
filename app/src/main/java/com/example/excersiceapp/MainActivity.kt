package com.example.excersiceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.excersiceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private  var binding :ActivityMainBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.flstart?.setOnClickListener{
val intent = Intent(this,ExcersiceActivity::class.java)
            startActivity(intent)
           // Toast.makeText(this,"button clicked",Toast.LENGTH_LONG).show()
        }

        binding?.flBMI?.setOnClickListener{
            val intent = Intent(this,ActivityBMI::class.java)
            startActivity(intent)
           // Toast.makeText(this,"button clicked",Toast.LENGTH_LONG).show()
        }
        binding?.flHIS?.setOnClickListener{
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
            // Toast.makeText(this,"button clicked",Toast.LENGTH_LONG).show()
        }



    }






    override fun onDestroy() {

         val i : String ="sdfsdf"



        super.onDestroy()
    binding=null
    }
}