package com.example.administrator.bloodsoulview.ticker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.administrator.bloodsoulview.R
import com.robinhood.ticker.TickerUtils
import kotlinx.android.synthetic.main.activity_ticker.*
import kotlin.random.Random

/**
 *  https://github.com/robinhood/ticker
 */
class TickerActivity : AppCompatActivity() {

    private val random = Random(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticker)
        init()
    }

    private fun init() {
        randomView.setCharacterLists(TickerUtils.provideNumberList())
        tickerView.setCharacterLists(TickerUtils.provideNumberList())

//        tickerView.textColor = textColor
//        tickerView.setTextSize(textSize)
//        tickerView.typeface = myCustomTypeface
//        tickerView.animationDuration = 500
//        tickerView.animationInterpolator = OvershootInterpolator()
//        tickerView.gravity = Gravity.START
//        tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY)
    }

    fun setNum(view: View) {
        val random = random.nextInt(10)
        randomView.text = random.toString()
        val num = System.currentTimeMillis() * random
        tickerView.text = num.toString()
//        tickerView.setText(num.toString(), false) // 取消动画
    }

}
