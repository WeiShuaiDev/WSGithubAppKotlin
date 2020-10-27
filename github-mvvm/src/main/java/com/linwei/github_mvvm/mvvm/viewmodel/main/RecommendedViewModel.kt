package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.stringArray
import com.linwei.cams.ext.yes
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import com.linwei.github_mvvm.mvvm.model.bean.TrendingRepoModel
import com.linwei.github_mvvm.mvvm.model.conversion.ReposConversion
import com.linwei.github_mvvm.mvvm.model.ui.ReposUIModel
import com.linwei.github_mvvm.mvvm.model.repository.RecommendedModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RecommendedViewModel @Inject constructor(
        private val model: RecommendedModel,
        application: Application
) : BaseViewModel(model, application), RecommendedContract.ViewModel {

    val sortData: List<List<String>> = listOf(
        R.array.trend_language.stringArray().toList(),
        R.array.trend_since.stringArray().toList()
    )

    val sortValue: List<List<String>> = listOf(
        R.array.trend_language_data.stringArray().toList(),
        R.array.trend_since_data.stringArray().toList()
    )

    val sortType: ArrayList<String> = arrayListOf(sortValue[0][0], sortValue[1][0])

    /**
     * 接收趋势数据
     */
    private val _reposUIModel = MutableLiveData<List<ReposUIModel>>()
    val reposUIModel: LiveData<List<ReposUIModel>>
        get() = _reposUIModel


    override fun toTrendData() {
        mLifecycleOwner?.let {
            model.obtainTrendData(
                it,
                sortType[0],
                sortType[1],
                object : LiveDataCallBack<List<TrendingRepoModel>>() {

                    override fun onSuccess(code: String?, data: List<TrendingRepoModel>?) {
                        super.onSuccess(code, data)

                        data?.let {
                            it.isNotNullOrSize().yes {
                                _reposUIModel.value = trendConversionByReposUIModel(it)

                                postUpdateStatus(StatusCode.SUCCESS)
                            }.otherwise {
                                _reposUIModel.value = null

                                postUpdateStatus(StatusCode.FAILURE)
                            }
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        _reposUIModel.value = null

                        postUpdateStatus(StatusCode.ERROR)

                    }
                })
        }
    }

    /**
     * 进行数据转换 'Trend' ->'ReposUIModel'
     */
    fun trendConversionByReposUIModel(list: List<TrendingRepoModel>?): MutableList<ReposUIModel> {
        val reposUIModel: MutableList<ReposUIModel> = mutableListOf()
        list?.let {
            for (trend: TrendingRepoModel in it) {
                reposUIModel.add(ReposConversion.trendToReposUIModel(trend))
            }
        }
        return reposUIModel
    }
}