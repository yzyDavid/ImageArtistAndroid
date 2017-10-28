package com.example.yzy.imageartist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    private lateinit var mbutton1: Button
    private lateinit var mbutton2: Button
    private lateinit var mbutton3: Button
    private lateinit var mbutton4: Button
    private lateinit var mbutton5: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mbutton1 = findViewById(R.id.stylize_button) as Button
        mbutton1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, stylize::class.java)
            startActivity(intent)
        }

        mbutton2 = findViewById(R.id.joint_button) as Button
        mbutton2.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, joint::class.java)
            startActivity(intent)
        }

        mbutton3 = findViewById(R.id.sticker_button) as Button
        mbutton3.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, sticker::class.java)
            startActivity(intent)
        }

        mbutton4 = findViewById(R.id.frame_button) as Button
        mbutton4.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, frame::class.java)
            startActivity(intent)
        }
        mbutton5 = findViewById(R.id.setting_button) as FloatingActionButton
        mbutton5.setOnClickListener{
            val intent = Intent()
            intent.setClass(this,setting::class.java)
            startActivity(intent)
        }

    }
}
