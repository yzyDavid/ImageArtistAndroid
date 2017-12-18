package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class ColorActivity : AppCompatActivity() {
    lateinit var mPhoto: ImageView
    private lateinit var model: ColorModel
    lateinit var mpb: MaterialProgressBar
    var isWaiting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        this.setTitle(R.string.choose_color)
        mPhoto = findViewById(R.id.color_photo)
        mpb = findViewById(R.id.color_mpb)
        mpb.visibility = View.INVISIBLE
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
                            if (isWaiting) {
                                return@setOnClickListener
                            }
                            Log.i("Color", "start theme coloring")
                            isWaiting = true
                            mpb.visibility = View.VISIBLE
                            model.getThemeColor(it, pair.first)
                        }
                    }
                }
    }
}
