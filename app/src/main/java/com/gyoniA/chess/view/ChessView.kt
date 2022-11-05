package com.gyoniA.chess.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnLayout
import androidx.fragment.app.FragmentManager
import com.GyoniA.chess.R
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gyoniA.chess.model.GameBackup
import com.gyoniA.chess.model.Jatek
import java.io.IOException
import java.lang.Integer.min
import kotlin.math.roundToInt


class ChessView : View {
    private val paintBg = Paint()
    private val paintLine = Paint()
    private val paintDark = Paint()
    private val paintLight = Paint()
    private val paintSelected = Paint()
    private val paintPossible = Paint()
    private val paintPossibleHit = Paint()

    private val startingTime = 300

    private var image: Bitmap? = null
    private var chessPieceBitmaps: Array<Bitmap?> = Array(12) { null }

    var whoseTurn: TextView? = null
    private var whiteTimeView: TextView? = null
    private var blackTimeView: TextView? = null

    private var game: Jatek

    var gameMode: Int = 0

    private var host: GameFragment? = null

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
        game.jatekmod = gameMode
        game.view = this
        game.start()
        doOnAttach { host = FragmentManager.findFragment<GameFragment>(this) }
    }

    private fun splitBitmap(bitmap: Bitmap, xCount: Int, yCount: Int): Array<Array<Bitmap?>> {
        // Allocate a two dimensional array to hold the individual images.
        val bitmaps = Array(xCount) {
            arrayOfNulls<Bitmap>(
                yCount
            )
        }
        // Divide the original bitmap width by the desired vertical column count
        val width: Int = bitmap.width / xCount
        // Divide the original bitmap height by the desired horizontal row count
        val height: Int = bitmap.height / yCount
        // Loop the array and create bitmaps for each coordinate
        for (x in 0 until xCount) {
            for (y in 0 until yCount) {
                // Create the sliced bitmap
                bitmaps[x][y] = Bitmap.createBitmap(bitmap, x * width, y * height, width, height)
            }
        }
        // Return the array
        return bitmaps
    }

    fun setImage(bitmap: Bitmap) {
        image = bitmap

        doOnLayout {
            val splitMap = splitBitmap(bitmap, 6, 2)
            var i = 0
            for (map in splitMap) {
                for (piece in map) {
                    chessPieceBitmaps[i] = Bitmap.createScaledBitmap(piece!!, (width.toFloat() / 8).roundToInt(), (height.toFloat() / 8).roundToInt(), false)
                    i++
                }
            }
            invalidate()
        }
    }

    fun setWhiteTimeView(whiteTime: TextView) {
        whiteTimeView = whiteTime
        whiteTime.text = context?.getString(R.string.whitePlayersTime, startingTime)
        game.setWhiteTimeView(whiteTime)
    }

    fun setBlackTimeView(blackTime: TextView) {
        blackTimeView = blackTime
        blackTime.text = context?.getString(R.string.blackPlayersTime, startingTime)
        game.setBlackTimeView(blackTime)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paintBg)
        drawBoard(canvas)
    }

    private fun drawBoard(canvas: Canvas) {

        var paintForBackground: Paint?
        val widthOfSquare = width.toFloat() / 8
        val heightOfSquare = height.toFloat() / 8

        var left: Float //x coord of top left corner
        var top: Float //y coord of top left corner

        game.kivalasztott?.HovaLephet()

        for (i in 0..7) {
            for (j in 0..7) {
                left = i * widthOfSquare
                top = j * heightOfSquare
                if ((i + j) % 2 == 0) {
                    paintForBackground = paintLight
                } else {
                    paintForBackground = paintDark
                }
                canvas.drawRect(left, top, left+widthOfSquare, top+heightOfSquare, paintForBackground)

                var b = game.tab!!.GetFeherBabuk()[Point(i, j)]
                if (b == null) {
                    b = game.tab!!.GetFeketeBabuk()[Point(i, j)]
                }
                if (b != null) {
                    if (b === game.kivalasztott) {
                        paintForBackground = paintSelected
                        canvas.drawCircle(left + widthOfSquare/2, top + heightOfSquare/2, widthOfSquare/2, paintForBackground)
                    } else if (game.kivalasztott != null && game.kivalasztott!!.GetUtesiLehetosegek()
                            .contains(
                                Point(i, j)
                            )
                    ) {
                        paintForBackground = paintPossibleHit
                        canvas.drawCircle(left + widthOfSquare/2, top + heightOfSquare/2, widthOfSquare/2, paintForBackground)
                    }
                    canvas.drawBitmap(chessPieceBitmaps[b.GetImageIndex()]!!, left, top, paintForBackground)
                } else if (game.kivalasztott != null && game.kivalasztott!!.GetLepesiLehetosegek()
                        .contains(
                            Point(i, j)
                        )
                ) {
                    paintForBackground = paintPossible
                    canvas.drawCircle(left + widthOfSquare/2, top + heightOfSquare/2, widthOfSquare/4, paintForBackground)
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

    fun checkEndGame(res: Int = -10) {
        if (res != -10) {
            if (res != 2) {
                host?.endGame(res)
            }
        } else {
            val result = game.VegeVanMar()
            if (result != 2) {
                host?.endGame(result)
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                checkEndGame()
                if (!game.megyAJatek) {
                    return true
                }
                val tX: Int = (event.x / (width / 8)).toInt()
                val tY: Int = (event.y / (height / 8)).toInt()
                game.onTouch(tX, tY)
                if (game.feherJonE) {
                    whoseTurn?.text = context.getString(R.string.whitePlayersTurn)
                } else {
                    whoseTurn?.text  = context.getString(R.string.blackPlayersTurn)
                }
                invalidate()
                checkEndGame()

                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

    fun changeGameMode(mode: Int) {
        game.jatekmod = mode
    }

    fun updateWhiteTimeView(string: String?) {
        host?.runUIOperation(Runnable {
            whiteTimeView?.text = string
        })
    }

    fun updateBlackTimeView(string: String?) {
        host?.runUIOperation(Runnable {
            blackTimeView?.text = string
        })
    }

    fun getSaveData(): String {
        val mapper = jacksonObjectMapper()
        return mapper.writeValueAsString(game.backupGame())
    }

    fun loadFromSaveData(data: String) {
        val mapper = jacksonObjectMapper()

        val simpleModule = SimpleModule()
        simpleModule.addKeyDeserializer(Point::class.java, PointDeserializer())
        mapper.registerModule(simpleModule)
        val backupFromJson = mapper.readValue(data, GameBackup::class.java)
        game.restoreFromBackup(backupFromJson)
        invalidate()
    }

    class PointDeserializer : KeyDeserializer() {

        private val MAPPER = ObjectMapper()

        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserializeKey(key: String?, ctxt: DeserializationContext?): Any? {

            return MAPPER.readValue(key, Point::class.java)
        }
    }
}