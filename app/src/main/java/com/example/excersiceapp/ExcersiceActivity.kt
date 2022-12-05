package com.example.excersiceapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.excersiceapp.databinding.ActivityExcersiceBinding
import com.example.excersiceapp.databinding.DialogCustomBackConfermationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExcersiceActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var RestTimerDuration :Long=1
    private var ExcerciseTimerDuration :Long=1
    private var binding : ActivityExcersiceBinding? =null

    private  var restTimer: CountDownTimer?=null
    private var restprogress : Int =0

    private  var restTimerExcersice: CountDownTimer?=null
    private var restprogressExcersice : Int =0

    private var exerciseTimerDuration:Long = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null // We will initialize the list later.
    private var currentExercisePosition = -1
    private var tts: TextToSpeech? = null

    private var player : MediaPlayer?=null
private var excerciseAdaptor:ExcersiceStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExcersiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbExcersice)
if(supportActionBar!=null)
{
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}


        binding?.tbExcersice?.setNavigationOnClickListener{

            onBackPressed()

        }
        tts = TextToSpeech(this, this)
        exerciseList = Constants.defaultExerciseList()
        setupRestView()
        setupExcerciseStatusRecylerView()
    }
//custodialog with binding
    private fun CustomDialogforBackButton() {
        val customdialog=  Dialog(this)
        val dialogBinding=DialogCustomBackConfermationBinding.inflate(layoutInflater)
        // two seprate binding for activty and seperate dialog binding
        customdialog.setContentView(dialogBinding.root)
        customdialog.setCanceledOnTouchOutside(false)
    dialogBinding.btnDialogyes.setOnClickListener{
       this@ExcersiceActivity.finish()
        customdialog.dismiss()
    }
    dialogBinding.btnDialogno.setOnClickListener{
        customdialog.dismiss()
    }
customdialog.show()

    }

    override fun onBackPressed() {

        CustomDialogforBackButton()
    }

    private fun setupExcerciseStatusRecylerView()
{
    binding?.rvexcersicestatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    excerciseAdaptor=ExcersiceStatusAdapter(exerciseList!!)
    binding?.rvexcersicestatus?.adapter=excerciseAdaptor
}
private fun setupRestView()
{
try {

    val soundURI= Uri.parse("com.example.excersiceapp/" + R.raw.press_start)
    player=MediaPlayer.create(applicationContext,soundURI)
    player?.isLooping=false
    player?.start()
} catch (e : Exception)
{
  e.printStackTrace()
}

    binding?.flRestview?.visibility = View.VISIBLE
    binding?.tvTitle?.visibility = View.VISIBLE
    binding?.tvExerciseName?.visibility = View.INVISIBLE
    binding?.flExerciseview?.visibility = View.INVISIBLE
    binding?.ivImage?.visibility = View.INVISIBLE
binding?.tvNext?.visibility=View.VISIBLE
    if(restTimer!=null){
        restTimer?.cancel()
        restprogress = 0
    }
    speakOut("Take rest")
    setRestProgressBar()
}

    private fun setupExcersiceView()
    {

   //---     binding?.tvTitle?.text="Excersice Timer"



        binding?.tvNext?.visibility=View.INVISIBLE
        binding?.flRestview?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseview?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        if(restTimerExcersice!=null)
        {
            restTimerExcersice?.cancel()
            restprogressExcersice=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setProgressBarExcersice()
    }




    private fun setRestProgressBar()
    {  val listd =Constants.defaultExerciseList()

        binding?.tvNext?.text="Next Excersice is : ${listd[currentExercisePosition+1].getName().toString()}"
        binding?.pbPrograssbar?.progress=restprogress

        restTimer= object : CountDownTimer(RestTimerDuration*1000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                restprogress++
                binding?.pbPrograssbar?.progress= 10 - restprogress
                binding?.tvTimer?.text= (10 - restprogress).toString()
            }

            override fun onFinish() {
             //   Toast.makeText(this@ExcersiceActivity,
                //    "timerfinisher",Toast.LENGTH_LONG).show()
                //when rest view finished start excersice timer
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                excerciseAdaptor!!.notifyDataSetChanged()
                setupExcersiceView()

            }


        }.start()

    }
    private fun setProgressBarExcersice()
    {
        binding?.pbExcersiceprogbar?.progress=restprogressExcersice
        restTimerExcersice= object : CountDownTimer(ExcerciseTimerDuration*1000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                restprogressExcersice++
                binding?.pbExcersiceprogbar?.progress= 30 - restprogressExcersice
                binding?.tvTimerforex?.text= (30 - restprogressExcersice).toString()
            }

            override fun onFinish() {

                if(currentExercisePosition<exerciseList?.size!! - 1)
                {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    excerciseAdaptor!!.notifyDataSetChanged()
                    setupRestView()

                }else {

                    Toast.makeText(
                        this@ExcersiceActivity,
                        "congratulations you have completed  workout", Toast.LENGTH_LONG
                    ).show()

                    val intent =Intent(this@ExcersiceActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }


        }.start()

    }




    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null)
        {
            restTimer?.cancel()

            restprogress=0

        }



        if(restTimerExcersice!=null)
        {
            restTimerExcersice?.cancel()
            restprogressExcersice=0
        }
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null)
        {
player!!.stop()

        }
        binding=null
    }
    override fun onInit(status: Int) {

        //
        // START
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
        // END
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}