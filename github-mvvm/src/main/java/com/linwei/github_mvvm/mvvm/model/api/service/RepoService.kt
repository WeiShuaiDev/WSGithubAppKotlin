package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.bean.Repository
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/21
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface RepoService {

    fun getUserRepository100StatusDao(@Path("user") user: String,
                                      @Query("page") page: Int,
                                      @Query("sort") sort: String = "pushed",
                                      @Query("per_page") per_page: Int = 100):LiveData<ArrayList<Repository>>
}