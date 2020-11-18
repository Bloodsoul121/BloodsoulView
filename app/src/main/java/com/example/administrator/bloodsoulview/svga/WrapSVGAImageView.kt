package com.example.administrator.bloodsoulview.svga

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser.Companion.shareParser
import com.opensource.svgaplayer.SVGAParser.ParseCompletion
import com.opensource.svgaplayer.SVGAVideoEntity
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.MalformedURLException
import java.net.URL

/**
 * 1.svga离开window后会自动停止动画，这里做下恢复动画
 * 2.支持加载动态图和静态图
 */
class WrapSVGAImageView : SVGAImageView {

    companion object {
        const val TAG = "WrapSVGAImageView"
    }

    private var mIsNet = true
    private var mLoadUrl: String? = null
    private var mCallback: Callback? = null
    private var mNeedResumeAnim = false
    private var mLoadCompleted = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mNeedResumeAnim) {
            if (mIsNet) {
                loadSvgaUrl(mLoadUrl)
            } else {
                loadSvgaAssets(mLoadUrl)
            }
        }
    }

    override fun onDetachedFromWindow() {
        checkAnim()
        super.onDetachedFromWindow()
        mLoadCompleted = false
    }

    private fun checkAnim() {
        mNeedResumeAnim = isAnimating || !isSvgaUrl(mLoadUrl)
    }

    private fun isSvgaUrl(url: String?): Boolean {
        if (url.isNullOrEmpty()) {
            return false
        }
        return url.endsWith(".svga")
    }

    // 如果加载失败，mLoadUrl会被置为null
    fun getLoadUrl(): String? {
        return mLoadUrl
    }

    // 支持动态图和静态图
    fun loadSvgaUrl(resUrl: String?) {
        mIsNet = true
        if (mLoadUrl == resUrl && mLoadCompleted) {
            return
        }
        mLoadUrl = resUrl
        if (resUrl.isNullOrEmpty()) {
            clearWrap()
            mCallback?.onLoadFailed()
            return
        }
        if (isSvgaUrl(resUrl)) {
            loadSvgaAnim(resUrl)
        }
    }

    private fun loadSvgaAnim(resUrl: String) {
        val svgaFilePath = checkSvgaFileExist(resUrl)
        val parseCompletion: ParseCompletion = object : ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                if (resUrl == mLoadUrl) {
                    val drawable = SVGADrawable(videoItem)
                    setImageDrawable(drawable)
                    startAnimation()
                    mCallback?.onLoadCompleted(drawable)
                    mLoadCompleted = true
                }
            }

            override fun onError() {
                if (resUrl == mLoadUrl) {
                    clearWrap()
                    mCallback?.onLoadFailed()
                }
            }
        }
        when {
            TextUtils.isEmpty(resUrl) -> {
                clearWrap()
                mCallback?.onLoadFailed()
                Log.i(TAG, "loadSvgaAnim -> no file")
            }
            TextUtils.isEmpty(svgaFilePath) -> {
                try {
                    shareParser().decodeFromURL(URL(resUrl), parseCompletion)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    clearWrap()
                    mCallback?.onLoadFailed()
                }
                Log.i(TAG, "loadSvgaAnim -> net file")
            }
            else -> {
                try {
                    shareParser().decodeFromInputStream(FileInputStream(svgaFilePath), resUrl, parseCompletion, true)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    clearWrap()
                    mCallback?.onLoadFailed()
                }
                Log.i(TAG, "loadSvgaAnim -> local file")
            }
        }
    }

    private fun checkSvgaFileExist(resUrl: String): String? {
        return null
    }

    fun clearWrap() {
        mLoadUrl = null
        stopAnimation()
        clear()
        mLoadCompleted = false
    }

    fun setLoadCallback(callback: Callback?) {
        mCallback = callback
    }

    interface Callback {
        fun onLoadStart()
        fun onLoadCompleted(drawable: Drawable)
        fun onLoadFailed()
    }

    fun loadSvgaAssets(assetsPath: String?) {
        mIsNet = false
        if (mLoadUrl == assetsPath && mLoadCompleted) {
            return
        }
        mLoadUrl = assetsPath
        if (assetsPath.isNullOrEmpty()) {
            clearWrap()
            mCallback?.onLoadFailed()
            return
        }

        val parseCompletion: ParseCompletion = object : ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                if (assetsPath == mLoadUrl) {
                    val drawable = SVGADrawable(videoItem)
                    setImageDrawable(drawable)
                    startAnimation()
                    mCallback?.onLoadCompleted(drawable)
                    mLoadCompleted = true
                }
            }

            override fun onError() {
                if (assetsPath == mLoadUrl) {
                    clearWrap()
                    mCallback?.onLoadFailed()
                }
            }
        }
        shareParser().decodeFromAssets(assetsPath, parseCompletion)
    }

}