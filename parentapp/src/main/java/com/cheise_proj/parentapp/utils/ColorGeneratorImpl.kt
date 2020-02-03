package com.cheise_proj.parentapp.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.cheise_proj.presentation.utils.IColorGenerator
import java.util.*
import javax.inject.Inject

class ColorGeneratorImpl @Inject constructor() : IColorGenerator {
    override fun getColor(): GradientDrawable {
        val colors: MutableList<String>
        colors = ArrayList()

        colors.add("#e84e40")
        colors.add("#ec407a")
        colors.add("#ab47bc")
        colors.add("#7e57c2")
        colors.add("#5c6bc0")
        colors.add("#738ffe")
        colors.add("#29b6f6")
        colors.add("#26c6da")
        colors.add("#26a69a")
        colors.add("#2baf2b")
        colors.add("#9ccc65")
        colors.add("#d4e157")
        colors.add("#fbc02d")
        colors.add("#ffa726")
        colors.add("#ff7043")
        colors.add("#8d6e63")
        colors.add("#bdbdbd")
        colors.add("#78909c")

        val r = Random()
        val i1: Int = r.nextInt(17 - 0) + 0
        val draw = GradientDrawable()
        draw.shape = GradientDrawable.OVAL
        draw.setColor(Color.parseColor(colors[i1]))
        return draw
    }
}