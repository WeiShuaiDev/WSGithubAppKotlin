package com.linwei.github_mvvm.mvvm.contract.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/9
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface ReposFileListContract {

    interface View

    interface ViewModel {

        /**
         * 获取当前 [reposName] 仓库的 `Files`地址
         * @param userName [String]
         * @param reposName [String]
         * @param path [String]
         */
        fun toFiles(userName: String?, reposName: String?, path: String?)
    }

    interface Model {

        /**
         * 获取当前 [reposName] 仓库的 `Files`地址
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param path [String]
         * @param observer [LiveDataCallBack]
         */
        fun obtainFiles(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            path: String,
            observer: LiveDataCallBack<List<FileModel>>
        )
    }
}