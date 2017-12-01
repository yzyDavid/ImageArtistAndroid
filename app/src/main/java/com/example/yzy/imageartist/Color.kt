package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find

class Color : AppCompatActivity() {
    public lateinit var mPhoto: ImageView
    private lateinit var mText1: TextView
    private lateinit var mText2: TextView
    private lateinit var mText3: TextView
    private lateinit var mText4: TextView
    private lateinit var mText5: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        this.setTitle(R.string.choose_color)
        mPhoto=findViewById(R.id.color_photo)
        mText1=findViewById(R.id.text_one)
        mText2=findViewById(R.id.text_two)
        mText3=findViewById(R.id.text_three)
        mText4=findViewById(R.id.text_four)
        mText5=findViewById(R.id.text_five)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }
}
