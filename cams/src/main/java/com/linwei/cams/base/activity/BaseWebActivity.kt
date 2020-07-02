package com.linwei.cams.base.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.linwei.cams.R
import com.linwei.cams.ext.getDomain
import java.io.UnsupportedEncodingException
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: webActivity基类
 */
abstract class BaseWebActivity : BaseActivityWithTop() {

    override fun provideContentViewId(): Int = R.layout.activity_web_view

    override fun getStateViewRoot(): View = mRootView

    lateinit var mWbView: WebView
    private lateinit var mSettings: WebSettings

    protected var mUrl: String? = null
    var mTitleStr: String? = null
    var mJsFunction: String? = null
    private var mPostData: String? = null

    private var isError: Boolean = false
    private var mIsFirstLoad = false
    private val NICK_NAME = "androidPhone"

    companion object {
        const val URL = "url"
        const val TITLE = "title"
        const val POST_DATA = "data"
        const val JSFunction = "js_function" // js方法
    }

    override fun initLayoutView() {
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
     * 初始化WebView
     */
    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        mWebView.webChromeClient = getWebChromeClient()
        mWebView.webViewClient = getWebViewClient()

        if (getRelation() != null)
            mWebView.addJavascriptInterface(getRelation(), getNickName())

        mWebView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (useDefaultBackDeal() && event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                    mWebView.goBack()   //后退
                    return@OnKeyListener true    //已处理
                }
            }
            false
        })
    }

    /**
     * WebView设置
     */
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
     *
     * @return
     */
    fun useDefaultBackDeal(): Boolean {
        return true
    }

    fun getNickName(): String? {
        return NICK_NAME
    }

    open fun getRelation(): Any? {
        return null
    }

    /**
     * 获取WebChromeClient  子类可复写
     *
     * @return
     */
    open fun getWebChromeClient(): WebChromeClient {
        return object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView, title: String) {
                if (mTitleStr.isNullOrEmpty()) {
                    setTopBarTitle(title)
                } else {
                    mTitleStr = ""
                }
            }
        }
    }

    /**
     * 获取WebViewClient  子类可复写
     * @return
     */
    fun getWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (TextUtils.isEmpty(url)) {
                    return true
                }
                try {
                    if (url.startsWith("tel:")//电话
                        || url.startsWith("sms:")//短信
                        || url.startsWith("taobao:")
                        || url.startsWith("alipays:")
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                //https://suum.szsi.gov.cn/suum/goFoundPwd.do?method=goFoundPwd
                val localHttp: String? = mUrl?.getDomain()
                val nextHttp: String = url.getDomain()
                if (localHttp == nextHttp || mIsFirstLoad) {
                    mIsFirstLoad = false
                    @Suppress("DEPRECATION")
                    return super.shouldOverrideUrlLoading(view, url)
                }
                mUrl = url
                mWbView.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                if (isError) {
                    isError = false
                    onLoadError()
                } else {
                    if (!TextUtils.isEmpty(mJsFunction)) {
                        view.loadUrl(mJsFunction)
                    }
                    onLoadPageFinished()
                }
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                super.onReceivedSslError(view, handler, error)
                showSslErrorDialog(handler)
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                isError = true
            }
        }
    }

    /**
     * SSL证书提示框
     */
    private fun showSslErrorDialog(handler: SslErrorHandler?=null) {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage(R.string.error_ssl_tip)
        builder.setPositiveButton(R.string.continues) { _, _ ->
            handler?.proceed()
        }
        builder.setNegativeButton(R.string.cancal) { _, _ ->
            handler?.cancel()
        }
        builder.setOnKeyListener { dialog, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                handler?.cancel();
                dialog.dismiss();
            }
            false
        }
        builder.create().show()
    }

    open fun isPostMethod(): Boolean {
        return false
    }

    override fun initLayoutData() {
        mStateView.showLoading()
        mIsFirstLoad = true
        if (isPostMethod()) {
            if (!TextUtils.isEmpty(mPostData)) {
                try {
                    mWebView.postUrl(mUrl, mPostData!!.toByteArray(charset("UTF-8")))
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
        } else {
            mWebView.loadUrl(mUrl)
        }
    }

    fun onLoadPageFinished() {
        mStateView.showContent()
    }

    fun onLoadError() {
        mStateView.showRetry()
    }

    override fun initLayoutListener() {
        mStateView.setOnRetryClickListener { mWebView.reload() }
    }

}