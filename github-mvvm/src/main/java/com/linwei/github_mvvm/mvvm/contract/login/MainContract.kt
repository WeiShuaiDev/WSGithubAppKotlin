package com.linwei.github_mvvm.mvvm.contract.login

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface MainContract {

    interface View {

    }

    interface ViewModel {
        /**
         *  退出登录
         */
        fun toSignOut()
    }

    interface Model {
    }
}