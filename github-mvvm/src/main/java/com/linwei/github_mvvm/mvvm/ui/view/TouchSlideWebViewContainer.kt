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

    val webView: TouchSlideWebView = TouchSlideWebView(context)

    val avi: AVLoadingIndicatorView

    init {
        webView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGlobalWhite))
        var layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.addRule(CENTER_IN_PARENT)

        val settings: WebSettings? = webView.settings
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        settings?.domStorageEnabled = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings?.setAppCacheEnabled(true)

        addView(webView, layoutParams)

        avi = AVLoadingIndicatorView(context)
        layoutParams = LayoutParams(90.dp, 90.dp)
        avi.setIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary))
        avi.setIndicator("BallScaleMultipleIndicator")
        layoutParams.addRule(CENTER_IN_PARENT)

        addView(avi, layoutParams)

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                avi.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                avi.hide()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                //Common.launchUrl(view!!.context, request!!.url!!.toString())
                return true
            }
        }

        webView.webViewClient = webViewClient

        webView.addJavascriptInterface(JsCallback(), "GSYWebView")
    }

    internal inner class JsCallback {

        @JavascriptInterface
        fun requestEvent(request: Boolean) {
            webView.requestIntercept = request
        }
    }
}