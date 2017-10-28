package com.example.yzy.imageartist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private lateinit var mButtonStylize: Button
    private lateinit var mButtonJoint: Button
    private lateinit var mButtonSticker: Button
    private lateinit var mButtonFrame: Button
    private lateinit var mButtonSettings: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mButtonStylize = findViewById<Button>(R.id.stylize_button) as Button
        mButtonStylize.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Stylize::class.java)
            startActivity(intent)
        }

        mButtonJoint = findViewById<Button>(R.id.joint_button) as Button
        mButtonJoint.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Joint::class.java)
            startActivity(intent)
        }

        mButtonSticker = findViewById<Button>(R.id.sticker_button) as Button
        mButtonSticker.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Sticker::class.java)
            startActivity(intent)
        }

        mButtonFrame = findViewById<Button>(R.id.frame_button) as Button
        mButtonFrame.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Frame::class.java)
            startActivity(intent)
        }
        mButtonSettings = findViewById<FloatingActionButton>(R.id.setting_button) as FloatingActionButton
        mButtonSettings.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Settings::class.java)
            startActivity(intent)
        }

    }
}
