package com.example.counterinterrupt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    lateinit var but3 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        but3 = findViewById(R.id.but3)
        but3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() { super.onStart(); Log.d("message", "A2 onStart") }
    override fun onResume() { super.onResume(); Log.d("message", "A2 onResume") }
    override fun onPause() { super.onPause(); Log.d("message", "A2 onPause") }
    override fun onRestart() { super.onRestart(); Log.d("message", "A2 onRestart") }
    override fun onStop() { super.onStop(); Log.d("message", "A2 onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("message", "A2 onDestroy") }


}