package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Color : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        this.setTitle(R.string.choose_color)
    }
}
