package com.example.administrator.bloodsoulview.particle

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.graphics.Path
import android.graphics.Path.Direction.CCW
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.administrator.bloodsoulview.util.SizeUtil
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class DimpleView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private val particleList = mutableListOf<Particle>()
    private val paint = Paint()
    private val random = Random()
    private val path = Path()

    private val animator = ValueAnimator.ofFloat(0f, 1f)

    private val pathMeasure = PathMeasure()
    private val posArray = FloatArray(2)
    private val tanArray = FloatArray(2)

    private val centerCircleRadius = SizeUtil.dp2px(100f).toFloat()
    private val maxAlpha = 225
    private val total = 2000

    init {
        paint.style = FILL
        paint.isAntiAlias = true
        paint.color = Color.WHITE

        animator.duration = 3000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = -1
        animator.addUpdateListener {
            val value = it.animatedValue
            updateParticle(value as Float)
            invalidate()
        }
    }

    fun start() {
        if (animator.isStarted) {
            animator.cancel()
            return
        }
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerX = (measuredWidth / 2).toFloat()
        centerY = (measuredHeight / 2).toFloat()
        prepareParticleList()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        prepareParticleList()
    }

    private fun prepareParticleList() {
        path.addCircle(centerX, centerY, centerCircleRadius, CCW)
        pathMeasure.setPath(path, false)

        particleList.clear()

        for (i in 0..total) {

            pathMeasure.getPosTan(i * 1f / total * pathMeasure.length, posArray, tanArray)
            val x = posArray[0] + random.nextInt(10) - 5f
            val y = posArray[1] + random.nextInt(10) - 5f

            val alpha = maxAlpha
            val radius = 2f
            val speed: Float = createSpeed()
            val maxOffset: Float = random.nextInt(centerX.toInt()).toFloat()
            val angle = acos(((posArray[0] - centerX) / centerCircleRadius).toDouble())
            val offset: Float = random.nextInt(centerX.toInt()).toFloat()

            val particle = Particle(x, y, alpha, radius, speed, maxOffset, angle, offset)

            particleList.add(particle)
        }
    }

    private fun updateParticle(value: Float) {
        particleList.forEach {
            if (it.offset > it.maxOffset) {
                it.offset = 0f
                it.speed = createSpeed()
            }
            it.alpha = ((1 - it.offset / it.maxOffset) * maxAlpha).toInt()
            it.x = (centerX + cos(it.angle) * (centerCircleRadius + it.offset)).toFloat()
            if (it.y > centerY) {
                it.y = (centerY + sin(it.angle) * (centerCircleRadius + it.offset)).toFloat()
            } else {
                it.y = (centerY - sin(it.angle) * (centerCircleRadius + it.offset)).toFloat()
            }
            it.offset += it.speed.toInt()
        }
    }

    private fun createSpeed() = (random.nextInt(3) + 1).toFloat()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            it.drawColor(Color.BLACK)

            for (particle in particleList) {

                paint.alpha = particle.alpha

                it.drawCircle(particle.x, particle.y, particle.radius, paint)
            }
        }
    }

}