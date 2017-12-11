package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import com.example.yzy.imageartist.Utils

class Modify : AppCompatActivity() {
    lateinit var mPhoto: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        this.setTitle(R.string.modify)
        mPhoto = findViewById(R.id.modify_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_modify, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        when (item?.itemId) {
            R.id.menu_change_contrast -> contrastAdd()
        }
        return true
    }

    private fun contrastAdd() {
        WorkspaceManager.bitmap?.let {
            val mat = it.toMat()
            val target = Mat.zeros(mat.size(), mat.type())
            mat.convertTo(target, mat.type(), Config.contrastAlphaIncrease, 0.toDouble())
            val result = target.toBitmap()
            WorkspaceManager.bitmap = result
            mPhoto.setImageBitmap(result)
        }
    }
}
