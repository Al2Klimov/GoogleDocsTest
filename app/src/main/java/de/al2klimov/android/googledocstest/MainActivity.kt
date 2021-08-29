package de.al2klimov.android.googledocstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val bgThread = Executors.newFixedThreadPool(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainView = findViewById<View>(R.id.main)
        val textView = findViewById<TextView>(R.id.text)
        val uiThread = Handler()
        val sheet = File(cacheDir, "sheet.csv")

        Volley.newRequestQueue(this).add(Utf8StringRequest(
            Request.Method.GET,
            "https://docs.google.com/spreadsheets/d/1o4Gb6-Q0wDCb3CCMnrXogZBagXjBxbXoV7XC9dFn9Jc/export?format=csv",
            { resp ->
                uiThread.post {
                    textView.text = resp
                }
                bgThread.submit {
                    sheet.writeText(resp)
                }
            },
            { error ->
                uiThread.post {
                    Snackbar.make(mainView, error.message!!, Snackbar.LENGTH_LONG).show()
                }
                bgThread.submit {
                    var cached: String? = null

                    try {
                        cached = sheet.readText()
                    } catch (_: FileNotFoundException) {
                    }

                    if (cached != null) {
                        uiThread.post {
                            textView.text = cached
                        }
                    }
                }
            }
        ))
    }
}