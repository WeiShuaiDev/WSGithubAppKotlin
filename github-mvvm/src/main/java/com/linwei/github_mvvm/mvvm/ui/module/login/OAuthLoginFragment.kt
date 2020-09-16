package com.linwei.github_mvvm.mvvm.ui.module.login

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.showShort
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.BuildConfig
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentOauthLoginBinding
import com.linwei.github_mvvm.mvvm.contract.login.OAuthLoginContract
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import com.linwei.github_mvvm.mvvm.viewmodel.login.OAuthLoginViewModel
import kotlinx.android.synthetic.main.fragment_oauth_login.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `OAuth` 登陆页面
 *-----------------------------------------------------------------------
 */
class OAuthLoginFragment : BaseMvvmFragment<OAuthLoginViewModel, FragmentOauthLoginBinding>(),
    OAuthLoginContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_oauth_login

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner

        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {
        initWebView()
    }

    /**
     * 初始化 `WebView`,Setting 设置
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings: WebSettings? = oauth_webview.settings
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        settings?.domStorageEnabled = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings?.setAppCacheEnabled(true)

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                oauth_webview_loadingBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String
            ): Boolean {
                if (!isEmptyParameter(url)) {
                    if (url.startsWith("gsygithubapp://authed")) {
                        val code: String? = Uri.parse(url).getQueryParameter("code")
                        mViewModel?.toOAuthLogin(code)
                        return true
                    }
                }
                return false
            }
        }

        oauth_webview.webViewClient = webViewClient

        val url: String = "https://github.com/login/oauth/authorize?" +
                "client_id=${BuildConfig.CLIENT_ID}&" +
                "state=app&redirect_uri=gsygithubapp://authed";

        oauth_webview.loadUrl(url)
    }

    override fun initLayoutData() {
        mViewModel?.loginResult?.observe(viewLifecycleOwner, Observer {
            it.yes {
                R.string.logcat_login_success.showShort()

                //登录成功后跳转回首页
                MainActivity.start(mContext)
            }.otherwise {
                R.string.logcat_login_failed.showShort()
            }
        })

    }

    override fun initLayoutListener() {
    }

    override fun reloadData() {
    }

    override fun loadData() {
    }

}