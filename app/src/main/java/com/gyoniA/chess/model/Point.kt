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

    override fun hashCode(): Int {
        return x * 10000 + y
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point) {
            return x == other.x && y == other.y
        }
        return super.equals(other)
    }
}