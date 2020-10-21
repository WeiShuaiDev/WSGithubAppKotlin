package com.linwei.github_mvvm.mvvm.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.*
import android.widget.RelativeLayout
import com.linwei.cams.ext.dp
import com.linwei.github_mvvm.R
import com.wang.avi.AVLoadingIndicatorView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/21
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 拓展了触摸滑动兼容的WebView容器
 *-----------------------------------------------------------------------
 */
@SuppressLint("SetJavaScriptEnabled")
class TouchSlideWebViewContainer : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val mWebView: TouchSlideWebView = TouchSlideWebView(context)

    private val mAvi: AVLoadingIndicatorView

    init {
        mWebView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGlobalWhite))
        var layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.addRule(CENTER_IN_PARENT)

        val settings: WebSettings? = mWebView.settings
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        settings?.domStorageEnabled = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings?.setAppCacheEnabled(true)

        addView(mWebView, layoutParams)

        mAvi = AVLoadingIndicatorView(context)
        layoutParams = LayoutParams(90.dp, 90.dp)
        mAvi.setIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary))
        mAvi.setIndicator("BallScaleMultipleIndicator")
        layoutParams.addRule(CENTER_IN_PARENT)

        addView(mAvi, layoutParams)

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                mAvi.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                mAvi.hide()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                //Common.launchUrl(view!!.context, request!!.url!!.toString())
                return true
            }
        }

        mWebView.webViewClient = webViewClient

        mWebView.addJavascriptInterface(JsCallback(), "GSYWebView")
    }

    internal inner class JsCallback {

        @JavascriptInterface
        fun requestEvent(request: Boolean) {
            mWebView.requestIntercept = request
        }
    }
}