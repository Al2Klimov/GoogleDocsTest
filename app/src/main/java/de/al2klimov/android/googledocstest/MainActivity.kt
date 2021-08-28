package de.al2klimov.android.googledocstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainView = findViewById<View>(R.id.main)
        val textView = findViewById<TextView>(R.id.text)
        val uiThread = Handler()

        Volley.newRequestQueue(this).add(Utf8StringRequest(
            Request.Method.GET,
            "https://docs.google.com/spreadsheets/d/1o4Gb6-Q0wDCb3CCMnrXogZBagXjBxbXoV7XC9dFn9Jc/export?format=csv",
            { resp ->
                uiThread.post {
                    textView.text = resp
                }
            },
            { error ->
                uiThread.post {
                    Snackbar.make(mainView, error.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        ))
    }
}