package com.linwei.github_mvvm

import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.*
import androidx.databinding.ViewDataBinding
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.ext.navigationPopUpTo
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.getUserInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.isLoginState
import com.linwei.github_mvvm.mvvm.ui.module.login.UserActivity
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 启动页面
 *-----------------------------------------------------------------------
 */
class WelcomActivity : BaseMvvmActivity<BaseViewModel, ViewDataBinding>() {
    private var mAnimationSet: AnimationSet? = null

    override fun provideContentViewId(): Int = R.layout.activity_splash

    override fun useDataBinding(): Boolean = false

    override fun initLayoutView(savedInstanceState: Bundle?) {
        setViewAnimation()
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
        mAnimationSet?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(anim: Animation?) {
            }

            override fun onAnimationEnd(anim: Animation?) {
                toDelayJump()
            }

            override fun onAnimationStart(anim: Animation?) {
            }
        })
    }

    /**
     * 根据当前用户状态进行跳转，未登录状态，跳转到登录页面。 登录状态，跳转到首页
     */
    private fun userStatusJumpLogic() {
        isLoginState.yes {
            //当前登录状态
            getUserInfoPref()?.let {
                //用户数据状态为ModelU

                MainActivity.start(this)
                return@yes
            }

            UserActivity.start(this)
        }.otherwise {
            //当前未登录状态
            UserActivity.start(this)
        }
        this.finish()
    }

    /**
     * 延时跳转到首页
     */
    private fun toDelayJump() {
        val timer: CountDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                userStatusJumpLogic()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }
        timer.start()
    }

    /**
     * 设置动画效果
     */
    private fun setViewAnimation() {
        //透明动画
        val clAlphaAnimation: AlphaAnimation = AlphaAnimation(0.5f, 1.0f).apply {
            duration = 1500
        }

        //缩放动画
        val clScaleAnimation: ScaleAnimation = ScaleAnimation(
            0.5f, 1.2f, 0.5f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 2000
        }

        mAnimationSet = AnimationSet(true).apply {
            addAnimation(clAlphaAnimation)
            addAnimation(clScaleAnimation)
            interpolator = AccelerateInterpolator()
            fillBefore = true
        }
        mCLSplashRoot.startAnimation(mAnimationSet)
    }

    override fun bindViewModel() {

    }
}
