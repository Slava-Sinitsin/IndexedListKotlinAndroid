package com.example.lab3

import kotlin.math.sqrt

class Point2D(private var x: Double, private var y: Double) : Comparable<Point2D> {

    fun getX(): Double {
        return x
    }

    fun getY(): Double {
        return y
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    override fun compareTo(other: Point2D): Int {
        val distance1 = sqrt(x + y)
        val distance2 = sqrt(other.x + other.y)
        return distance1.compareTo(distance2)
    }
}