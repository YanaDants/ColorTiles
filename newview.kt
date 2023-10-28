package com.example.col

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.util.*


class NewView : View {
    var count = 4
    var tiles = Array(count) {
        IntArray(
            count
        )
    }
    var darkColor = Color.BLACK
    var brightColor = Color.YELLOW
    var color = darkColor
    var heightTile = 0
    var widthTile = 0
    var activity: MainActivity? = null

    constructor(context: Context?) : super(context) {
        activity = context as MainActivity?
        RandomColorTiles()
    }

    private fun RandomColorTiles() {
        val r = Random()
        for (i in 0 until count) {
            for (j in 0 until count) {
                val c = if (r.nextBoolean()) darkColor else brightColor
                tiles[i][j] = c
            }
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onDraw(canvas: Canvas) {
        val p = Paint()
        var colori = 0
        var colorj = 0
        heightTile = height / count
        widthTile = width / count
        var i = 0
        while (i < height - heightTile) {
            var j = 0
            while (j < width) {
                val color = tiles[colori][colorj]
                p.color = color
                canvas.drawRect(
                    j.toFloat(),
                    i.toFloat(),
                    (j + widthTile).toFloat(),
                    (i + heightTile).toFloat(),
                    p
                )
                colorj++
                j += widthTile
            }
            colori++
            colorj = 0
            i += heightTile
        }
    }

    private fun win(): Boolean {
        val color = tiles[0][0]
        for (i in 0 until count) {
            for (j in 0 until count) {
                if (tiles[i][j] != color) return false
            }
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x /= width / count
            y /= height / count
            for (i in 0 until count) {
                tiles[y][i] = if (tiles[y][i] == brightColor) darkColor else brightColor
                tiles[i][x] = if (tiles[i][x] == brightColor) darkColor else brightColor
            }
            invalidate()
            if (win()) {
                val winner = Toast.makeText(activity, "Ты победил!", Toast.LENGTH_LONG)
                winner.show()
            }
        }
        return true
    }
}
