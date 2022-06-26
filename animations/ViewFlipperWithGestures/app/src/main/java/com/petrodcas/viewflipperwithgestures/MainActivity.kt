package com.petrodcas.viewflipperwithgestures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petrodcas.viewflipperwithgestures.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}