package com.linwei.github_mvvm.global

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.module.LoadMoreModuleConfig.defLoadMoreView
import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.http.glide.loader.GlideImageLoader
import com.linwei.cams.manager.ImageLoaderManager
import com.linwei.github_mvvm.BuildConfig
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.ext.loadUserHeaderImage
import com.linwei.github_mvvm.mvvm.factory.IconFontFactory
import com.linwei.github_mvvm.mvvm.ui.view.RvLoadMoreView
import com.mikepenz.iconics.Iconics
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppLifecycleImpl : AppLifecycles {

    override fun attachBaseContext(context: Context) {
        Timber.i("AppLifecycleImpl to attachBaseContext!")
    }

    override fun onCreate(application: Application, appDelegate: AppDelegate?) {
        Timber.i("AppLifecycleImpl to onCreate!")
        //ImageLoaderManager 初始化
        initImageLoaderManager(application)
        //MaterialDrawer 初始化
        initMaterialDrawer()
        //Iconics 初始化
        initIconics(application)
        //Timber 初始化
        initLog()
        //RecyclerView 配置初始化
        initRVConfig()
    }

    /**
     * 初始化 `MaterialDrawer`
     */
    private fun initMaterialDrawer() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun placeholder(ctx: Context): Drawable {
                return ContextCompat.getDrawable(ctx, R.mipmap.ic_launcher)!!
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return ContextCompat.getDrawable(ctx, R.mipmap.ic_launcher)!!
            }

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                loadUserHeaderImage(imageView, uri.toString())
            }
        })
    }

    /**
     * 初始化图片加载框架
     * @param application [Application]
     */
    private fun initImageLoaderManager(application: Application) {
        ImageLoaderManager.initialize(GlideImageLoader(application))
    }

    /**
     *  初始化日志打印
     */
    private fun initLog() {
        if (BuildConfig.LOG_DEBUG) { //Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(DebugTree())
        }
    }

    /**
     * 初始化 `Iconics`
     * @param application [Application]
     */
    private fun initIconics(application: Application) {
        Iconics.init(application)
        Iconics.registerFont(IconFontFactory)
    }

    /**
     * 初始化 `RecyclerView`
     */
    private fun initRVConfig() {
        // 在 Application 中配置全局自定义的 LoadMoreView
        defLoadMoreView = RvLoadMoreView()
    }

    override fun onTerminate(application: Application) {
        Timber.i("AppLifecycleImpl to onTerminate!")
    }

    override fun onLowMemory(application: Application) {
        Timber.i("AppLifecycleImpl to onLowMemory!")
    }

    override fun onTrimMemory(level: Int) {
        Timber.i("AppLifecycleImpl to onTrimMemory!")
    }
}