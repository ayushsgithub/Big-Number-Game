package com.example.bignumbergame

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var btnLeft: Button
    lateinit var btnRight: Button
    lateinit var tvScore: TextView
    lateinit var rootLayout: View
    private var score = 0;
    lateinit var btnRest: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize buttons after setContentView
        tvScore = findViewById(R.id.tvScore)
        btnLeft = findViewById(R.id.btnLeft)
        btnRight = findViewById(R.id.btnRight)
        rootLayout = findViewById(R.id.main)
        btnRest = findViewById(R.id.btReset)


        getRandomNumber()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvScore.text = "Score: $score"

        btnLeft.setOnClickListener {
            checkAnswer(true)
            getRandomNumber()
        }
        btnRight.setOnClickListener(){
            checkAnswer(false)
            getRandomNumber()
        }
        btnRest.setOnClickListener(){
            reset()
        }
    }

    private fun reset() {
        score = 0
        tvScore.text = "Score: $score"
        getRandomNumber()
    }

    // Generate random number and set to both buttons
    private fun getRandomNumber() {
        val num1 = Random.nextInt(1, 999)
        var num2 = Random.nextInt(1, 999)
        if(num1 == num2){
            num2 = Random.nextInt(1, 999)
        }
        btnLeft.text = "$num1"
        btnRight.text = "$num2"
    }
    private fun checkAnswer(isLeftClicked: Boolean){
        val n1 = btnLeft.text.toString().toInt()
        val n2 = btnRight.text.toString().toInt()

        val correctAnswer: Boolean = if(isLeftClicked) n1 > n2 else n2 > n1

        val toastMessage: String
        val color: Int

        if(correctAnswer){
            score++
            toastMessage = "Correct!!"
            color = Color.GREEN
        }else{
            if (score > 0) score--
            toastMessage = "Wrong!!"
            color = Color.RED
        }
        tvScore.text = "Score: $score"
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        rootLayout.setBackgroundColor(color)

        Handler(Looper.getMainLooper()).postDelayed({
            rootLayout.setBackgroundColor(Color.WHITE) // Change to your default color
        }, 1000)
    }
}
