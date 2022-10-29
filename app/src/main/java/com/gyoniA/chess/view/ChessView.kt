package com.gyoniA.chess.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.gyoniA.chess.model.Jatek
import com.gyoniA.chess.model.Point
import java.lang.Integer.min

class ChessView : View {
    private val paintBg = Paint()
    private val paintLine = Paint()
    private val paintDark = Paint()
    private val paintLight = Paint()
    private val paintSelected = Paint()
    private val paintPossible = Paint()
    private val paintPossibleHit = Paint()

    private var game: Jatek

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paintBg.color = Color.BLACK
        paintBg.style = Paint.Style.FILL

        paintDark.color = Color.rgb(17, 92, 30)
        paintDark.style = Paint.Style.FILL

        paintLight.color = Color.rgb(255, 248, 189)
        paintLight.style = Paint.Style.FILL

        paintSelected.color = Color.rgb(245, 235, 49)
        paintSelected.style = Paint.Style.FILL

        paintPossible.color = Color.rgb(130, 230, 255)
        paintPossible.style = Paint.Style.FILL

        paintPossibleHit.color = Color.rgb(255, 0, 0)
        paintPossibleHit.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5F

        game = Jatek()
        game.jatekmod = 1 //TODO add mode selection
        //val options = arrayOf<Any>("Ember ember ellen", "Ember g�p ellen", "G�p g�p ellen")
        //TODO add menu bar
        game.start()
    }
    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paintBg)

        drawBoard(canvas)
    }

    private fun drawBoard(canvas: Canvas) {
        val widthFloat: Float = width.toFloat()
        val heightFloat: Float = height.toFloat()

        canvas.drawRect(0F, 0F, widthFloat, heightFloat, paintLine)//TODO remove if not needed

        var paintForBackground: Paint?
        var widthOfSquare = widthFloat / 8
        var heightOfSquare = heightFloat / 8

        for (i in 0..7) {
            for (j in 0..7) {
                if ((i + j) % 2 == 0) {
                    paintForBackground = paintLight
                } else {
                    paintForBackground = paintDark
                }
                canvas.drawRect(i*widthOfSquare, j*heightOfSquare, widthOfSquare, heightOfSquare, paintForBackground)

                var b = game.tab!!.GetFeherBabuk()[Point(i, j)]
                if (b == null) {
                    b = game.tab!!.GetFeketeBabuk()[Point(i, j)]
                }
                if (b != null) {
                    if (b === game.kivalasztott) {
                        paintForBackground = paintSelected
                        canvas.drawCircle(i*widthOfSquare + widthOfSquare/2, j*heightOfSquare + heightOfSquare/2, widthOfSquare/2, paintForBackground)
                    } else if (game.kivalasztott != null && game.kivalasztott!!.GetUtesiLehetosegek()
                            .contains(
                                Point(i, j)
                            )
                    ) {
                        paintForBackground = paintPossibleHit
                        canvas.drawCircle(i*widthOfSquare + widthOfSquare/2, j*heightOfSquare + heightOfSquare/2, widthOfSquare/2, paintForBackground)
                    }
                    canvas.drawBitmap(b.GetImage()!!, i*widthOfSquare, j*heightOfSquare, paintForBackground)//TODO check if coordinates of bitmap are correct
                } else if (game.kivalasztott != null && game.kivalasztott!!.GetLepesiLehetosegek()
                        .contains(
                            Point(i, j)
                        )
                ) {
                    paintForBackground = paintPossible
                    canvas.drawCircle(i*widthOfSquare + widthOfSquare/2, j*heightOfSquare + heightOfSquare/2, widthOfSquare/4, paintForBackground)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val d: Int

        when {
            w == 0 -> { d = h }
            h == 0 -> { d = w }
            else -> { d = min(w, h) }
        }

        setMeasuredDimension(d, d)
    }

    fun checkEndGame() {
        var result = game.VegeVanMar()
        when (result) {
            -1 -> Toast.makeText(context, "Black won!", Toast.LENGTH_SHORT).show()
                /*val host = context as GameActivity
                host.endGame(result)*/
                //TODO implement endGame in GameActivity

            0 -> Toast.makeText(context, "Draw!", Toast.LENGTH_SHORT).show()
                /*val host = context as GameActivity
                host.endGame(result)*/
                //TODO implement endGame in GameActivity

            1 -> Toast.makeText(context, "White won!", Toast.LENGTH_SHORT).show()
                /*val host = context as GameActivity
                host.endGame(result)*/
                //TODO implement endGame in GameActivity
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (game.megyAJatek) {
                    return true
                }

                val tX: Int = (event.x / (width / 8)).toInt()
                val tY: Int = (event.y / (height / 8)).toInt()
                game.OnTouch(tX, tY)
                if (game.feherJonE) {
                    //kijon = "It's White's turn" TODO change to text view updating
                } else {
                    //kijon = "It's Black's turn"  TODO change to text view updating
                }
                invalidate()
                checkEndGame()

                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }
}