package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*


interface NotificationService {

    /**
     * @param all [Boolean]
     * @param participating [Boolean]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("notifications")
    fun getNotification(
        @Query("all") all: Boolean,
        @Query("participating") participating: Boolean,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Notification>>

    /**
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("notifications")
    fun getNotificationUnRead(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Notification>>

    /**
     * @param threadId [String]
     */
    @PATCH("notifications/threads/{threadId}")
    fun setNotificationAsRead(
        @Path("threadId") threadId: String
    ): LiveData<ResponseBody>


    @PUT("notifications")
    fun setAllNotificationAsRead(): LiveData<ResponseBody>

}
