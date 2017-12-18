package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class ColorActivity : AppCompatActivity() {
    lateinit var mPhoto: ImageView
    private lateinit var model: ColorModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        this.setTitle(R.string.choose_color)
        mPhoto = findViewById(R.id.color_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
        model = ColorModel(this)
        listOf(
                Pair(3, R.id.color_3),
                Pair(4, R.id.color_4),
                Pair(5, R.id.color_5),
                Pair(6, R.id.color_6),
                Pair(7, R.id.color_7))
                .map { Pair(it.first, findViewById<TextView>(it.second)) }
                .forEach { pair ->
                    pair.second.setOnClickListener {
                        WorkspaceManager.bitmap?.let {
                            Log.i("Color", "start theme coloring")
                            model.getThemeColor(it, pair.first)
                            Log.i("Color", "finish theme coloring")
                        }
                    }
                }
    }
}
