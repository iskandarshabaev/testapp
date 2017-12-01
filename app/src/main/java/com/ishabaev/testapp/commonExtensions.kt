package com.ishabaev.testapp

public fun Float.floorTo(signs: Int): Float {
    val d = Math.pow(10.0, signs.toDouble())
    return (Math.floor(this * d) / d).toFloat()
}