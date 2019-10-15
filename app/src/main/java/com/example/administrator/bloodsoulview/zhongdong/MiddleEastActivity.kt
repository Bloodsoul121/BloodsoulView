package com.example.administrator.bloodsoulview.zhongdong

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.example.administrator.bloodsoulview.R
import kotlinx.android.synthetic.main.activity_middle_east.*
import java.text.Format
import java.util.*

class MiddleEastActivity : AppCompatActivity() {

    private var isVip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle_east)
    }

    fun clickBtn1(view: View) {
        isVip = !isVip
        setText()
    }

    private fun setText() {
        val ss: SpannableString

        ss = if (isVip) {
            val vip2 : String = resources.getText(R.string.sign_in_vip2).toString()
            SpannableString(vip2.format(Locale.US, 2, "20%"))
        } else {
            SpannableString(resources.getText(R.string.sign_in_vip))
        }

        val tag = resources.getText(R.string.sign_in_vip_tag).toString()
        val index = ss.toString().indexOf(tag)

        tv2.text = ss.length.toString()

        ss.setSpan(ForegroundColorSpan(resources.getColor(R.color.pure_white)), index, index + tag.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv1.text = ss
    }
}
