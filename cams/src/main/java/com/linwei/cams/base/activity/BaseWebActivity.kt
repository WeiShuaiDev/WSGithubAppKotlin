package com.linwei.cams.base.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.github.nukc.stateview.StateView.*
import com.linwei.cams.R
import com.linwei.cams.ext.jumpIntent
import java.io.UnsupportedEncodingException
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 增加导航栏基类 [BaseWebActivity]
 *-----------------------------------------------------------------------
 */
abstract class BaseWebActivity : BaseActivityWithTop(), DownloadListener {

    override fun provideContentViewId(): Int = R.layout.activity_web_view

    override fun obtainStateViewRoot(): View = mRootView

    private lateinit var mWbView: WebView
    private lateinit var mSettings: WebSettings

    private var mUrl: String? = null
    private var mTitleStr: String? = null
    private var mJsFunction: String? = null
    private var mPostData: String? = null

    private var isError: Boolean = false
    private var mIsFirstLoad = false

    companion object {
        const val URL = "URL"
        const val TITLE = "TITLE"
        const val POST_DATA = "POST_DATA"
        const val JSFunction = "JS_FUNCTION"
    }

    override fun initLayoutView(savedInstanceState: Bundle?) {
        mUrl = intent.getStringExtra(URL)
        mTitleStr = intent.getStringExtra(TITLE)
        mJsFunction = intent.getStringExtra(JSFunction)
        mPostData = intent.getStringExtra(POST_DATA)
        mWbView = mWebView

        mTitleStr?.let {
            setTopBarTitle(mTitleStr!!)
        }

        initWebView()
        initWebSetting()
    }

    /**
     * 初始化 [WebView] ,自定义 `WebChromeClient`,`WebViewClient`,并绑定到 [WebView]
     */
    @SuppressLint("JavascriptInterface", "AddJavascriptInterface")
    private fun initWebView() {
        mWebView.webChromeClient = fetchWebChromeClient()
        mWebView.webViewClient = fetchWebViewClient()

        if (useJsInterface())
            if(fetchJsInterfaceObject()!=null&&!TextUtils.isEmpty(fetchJsInterfaceName())) {
                mWebView.addJavascriptInterface(fetchJsInterfaceObject()!!, fetchJsInterfaceName()!!)
            }


        mWebView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (useDefaultGoBack() && event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                    mWebView.goBack()   //后退
                    return@OnKeyListener true    //已处理
                }
            }
            false
        })
    }

    /**
     *  初始化 [WebView]，配置 `settings`
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting() {
        mSettings = mWebView.settings

        mSettings.javaScriptEnabled = true
        mSettings.databaseEnabled = true
        mSettings.setAppCacheEnabled(true)
        mSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        mSettings.blockNetworkImage = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        mSettings.allowFileAccess = true
        mSettings.domStorageEnabled = true
        @Suppress("DEPRECATION")
        mSettings.saveFormData = true

        mSettings.useWideViewPort = true
        mSettings.loadWithOverviewMode = true
        mSettings.defaultTextEncodingName = "UTF-8"
        // 是否可访问Content Provider的资源，默认值 true
        mSettings.allowContentAccess = true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false // 跨域请求
        mSettings.allowFileAccessFromFileURLs = true
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        mSettings.allowUniversalAccessFromFileURLs = true
        // 支持缩放
        @Suppress("DEPRECATION")
        mSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }


    /**
     * 是否使用默认处理返回的操作
     * @return [Boolean] true:默认；false:不是默认
     */
    protected open fun useDefaultGoBack(): Boolean = true


    /**
     * 是否使用 `JavaScript` 交互
     * @return [Boolean] true:默认；false:不是默认
     */
    protected open fun useJsInterface(): Boolean = false


    /**
     * 该方法暴露给开发者，自定义`JavaScript` 交互名称。
     * @return [Any] `JavaScript` 交互名称
     */
    protected open fun fetchJsInterfaceName(): String? = ""

    /**
     * 该方法暴露给开发者，自定义`JavaScript` 交互接口，并返回接口对象。
     * @return [Any] `JavaScript` 交互对象
     */
    protected open fun fetchJsInterfaceObject(): Any? = null

    /**
     * 获取WebChromeClient  子类可复写
     * @return [WebChromeClient]
     */
    protected open fun fetchWebChromeClient(): WebChromeClient {
        return object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                getTopBarLoadingView().progress = newProgress
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                if (mTitleStr.isNullOrEmpty()) {
                    setTopBarTitle(title)
                } else {
                    mTitleStr = ""
                }
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                if (showDialog(view, message, result)) return true
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                if (showDialog(view, message, result)) return true
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(
                view: WebView?,
                url: String?,
                message: String?,
                defaultValue: String?,
                result: JsPromptResult?
            ): Boolean {
                if (showDialog(view, message, result)) return true
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            private fun showDialog(
                view: WebView?,
                message: String?,
                result: JsResult?
            ): Boolean {
                if (view?.context == null)
                    return true
                AlertDialog.Builder(view.context)
                    .setMessage(message)
                    .setPositiveButton(
                        R.string.confirm
                    ) { _, _ -> result?.confirm() }
                    .setNegativeButton(R.string.cancel) { _, _ -> result?.cancel() }
                    .create()
                    .show()
                return false
            }
        }
    }

    /**
     * 获取 [WebViewClient]
     * @return [WebViewClient]
     */
    private fun fetchWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            @Suppress("DEPRECATION")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (TextUtils.isEmpty(url)) {
                    return true
                }

                fetchOnInterceptUrlLoading()?.onLoading(view, url)

                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                if (isError) {
                    isError = false
                    loadWebViewError()
                } else {
                    if (!TextUtils.isEmpty(mJsFunction)) {
                        view.loadUrl(mJsFunction!!)
                    }
                    loadWebViewFinished()
                }
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                super.onReceivedSslError(view, handler, error)
                showSslErrorDialog(handler)
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                isError = true
            }

        }
    }


    /**
     * 显示SSL证书提示框
     * @param handler
     */
    private fun showSslErrorDialog(handler: SslErrorHandler? = null) {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage(R.string.error_ssl_tip)
        builder.setPositiveButton(R.string.continues) { _, _ ->
            handler?.proceed()
        }
        builder.setNegativeButton(R.string.cancel) { _, _ ->
            handler?.cancel()
        }
        builder.setOnKeyListener { dialog, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                handler?.cancel()
                dialog.dismiss()
            }
            false
        }
        builder.create().show()
    }

    /**
     * [WebView] 是否设置提交报文
     * @return [Boolean] true:是；false:否
     */
    protected open fun isPostMethod(): Boolean = true

    override fun initLayoutData() {
        mStateView?.showLoading()
        mIsFirstLoad = true
        if(!TextUtils.isEmpty(mUrl)) {
            if (isPostMethod()) {
                if (!TextUtils.isEmpty(mPostData)) {
                    try {
                        mWebView.postUrl(mUrl!!, mPostData!!.toByteArray(charset("UTF-8")))
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                }
            } else {
                mWebView.loadUrl(mUrl!!)
            }
        }
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : OnRetryClickListener {
            override fun onRetryClick() {
                mWebView.reload()
            }
        }
    }

    /**
     * [WebView] 加载完成，通过 [mStateView] 显示加载完成内容。
     */
    private fun loadWebViewFinished() {
        mStateView?.showContent()
    }

    /**
     * [WebView] 加载失败，通过 [mStateView] 显示重新加载。
     */
    private fun loadWebViewError() {
        mStateView?.showRetry()
    }


    override fun onDownloadStart(
        url: String?,
        userAgent: String?,
        contentDisposition: String?,
        mimetype: String?,
        contentLength: Long
    ) {
        if (!url.isNullOrEmpty()) {
            jumpIntent(url)
            this.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView?.let {
            it.clearHistory()
            it.clearCache(true)
            it.loadUrl("about:blank")
            @Suppress("DEPRECATION")
            it.freeMemory()
            it.pauseTimers()
        }
    }

    /**
     * 设置 [WebView] 加载拦截接口
     */
    protected open fun fetchOnInterceptUrlLoading(): OnInterceptUrlLoading? = null

    interface OnInterceptUrlLoading {
        fun onLoading(view: WebView, url: String)
    }

}