package com.linwei.github_mvvm.mvvm.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import kotlin.math.abs

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/21
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 拓展了触摸滑动兼容的WebViewr
 *-----------------------------------------------------------------------
 */
class TouchSlideWebView : WebView {

    private var mStartX: Float = 0.0f
    private var mStartY: Float = 0.0f
    private var mOffsetX: Float = 0.0f
    private var mOffsetY: Float = 0.0f

    var requestIntercept = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(requestIntercept)
                mStartX = event.x
                mStartY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                mOffsetX = abs(event.x - mStartX)
                mOffsetY = abs(event.y - mStartY)
                if (mOffsetX > mOffsetY) {
                    parent.requestDisallowInterceptTouchEvent(requestIntercept)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onScrollChanged(x: Int, y: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(x, y, oldl, oldt)
        if (!isVerticalScrollBarEnabled) {
            //禁上下滚动
            scrollTo(x, 0)
        } else if (!isHorizontalScrollBarEnabled) {
            //禁止左右滚动
            scrollTo(0, y)
        }
    }
}