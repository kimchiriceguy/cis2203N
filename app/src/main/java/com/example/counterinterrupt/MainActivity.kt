package com.example.counterinterrupt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val key = "key"

class MainActivity : AppCompatActivity() {
    lateinit var textView : TextView
    lateinit var but1 : Button
    lateinit var but2 : Button
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("message", "A1 onCreate")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView = findViewById(R.id.textView)
        but1 = findViewById(R.id.but1)
        but2 = findViewById(R.id.but2)

        but2.setOnClickListener {
            counter += 1
            textView.text = "$counter"
        }

        but1.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("key", counter)
        Log.d("message", "A1 Saving State: $counter")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("key")
        textView.text = counter.toString()
        Log.d("message", "A1 Restored State: $counter")
    }

    override fun onStart() { super.onStart(); Log.d("message", "A1 onStart") }
    override fun onResume() { super.onResume(); Log.d("message", "A1 onResume") }
    override fun onPause() { super.onPause(); Log.d("message", "A1 onPause") }
    override fun onRestart() { super.onRestart(); Log.d("message", "A1 onRestart") }
    override fun onStop() { super.onStop(); Log.d("message", "A1 onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("message", "A1 onDestroy") }
}