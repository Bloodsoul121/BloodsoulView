package com.example.administrator.bloodsoulview.particle

class Particle {

    var x: Float = 0f
    var y: Float = 0f
    var alpha: Int = 0
    var radius: Float = 0f
    var speed: Float = 0f
    var maxOffset: Float = 0f
    var angle: Double = 0.0
    var offset: Float = 0f

    constructor(x: Float, y: Float, alpha: Int, radius: Float, speed: Float, maxOffset: Float, angle: Double, offset: Float) {
        this.x = x
        this.y = y
        this.alpha = alpha
        this.radius = radius
        this.speed = speed
        this.maxOffset = maxOffset
        this.angle = angle
        this.offset = offset
    }

}