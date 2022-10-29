package com.gyoniA.chess.model

class Point(var x: Int, var y: Int) {
    constructor(point: Point) : this(point.x, point.y)

    fun translate(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    @JvmName("getX1")
    fun getX(): Int {
        return x
    }

    @JvmName("getY1")
    fun getY(): Int {
        return y
    }

}