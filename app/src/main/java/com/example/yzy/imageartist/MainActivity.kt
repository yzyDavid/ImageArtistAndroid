package com.example.yzy.imageartist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private lateinit var mButton1: Button
    private lateinit var mButton2: Button
    private lateinit var mButton3: Button
    private lateinit var mButton4: Button
    private lateinit var mButton5: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mButton1 = findViewById<Button>(R.id.stylize_button) as Button
        mButton1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, stylize::class.java)
            startActivity(intent)
        }

        mButton2 = findViewById<Button>(R.id.joint_button) as Button
        mButton2.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, joint::class.java)
            startActivity(intent)
        }

        mButton3 = findViewById<Button>(R.id.sticker_button) as Button
        mButton3.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, sticker::class.java)
            startActivity(intent)
        }

        mButton4 = findViewById<Button>(R.id.frame_button) as Button
        mButton4.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, frame::class.java)
            startActivity(intent)
        }
        mButton5 = findViewById<FloatingActionButton>(R.id.setting_button) as FloatingActionButton
        mButton5.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, setting::class.java)
            startActivity(intent)
        }

    }
}
