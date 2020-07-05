package com.linwei.cams.base.holder

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import com.linwei.cams.R
import com.linwei.cams.ext.color

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/3
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: Toolbar ViewHolder 管理
 *-----------------------------------------------------------------------
 */
class  TopViewHolder(itemView: View) : ItemViewHolder(itemView) {
    private val mLayoutTopView: AppBarLayout = getView(R.id.mLayoutTopView) as AppBarLayout

    val mPbLoading: ProgressBar = getView(R.id.mPbLoading) as ProgressBar

    val mTvLeftTitle: TextView = getTextView(R.id.mTvLeftTitle)
    val mIbLeftImage: ImageButton = getImageButton(R.id.mIbLeftImage)

    val mTvTitle: TextView = getTextView(R.id.mTvTitle)

    val mIbRightImage: ImageButton = getImageButton(R.id.mIbRightImage)
    val mPbRefresh: ProgressBar = getView(R.id.mPbRefresh) as ProgressBar
    val mTvRightTitle: TextView = getTextView(R.id.mTvRightTitle)

    val mVLine: View = getView(R.id.mVLine)
    val mIncludeContent: FrameLayout = getView(R.id.mIncludeContent) as FrameLayout

    /**
     * 设置 `TopBar` 左边 [Textview] 文本信息
     * @param resId [Int] 显示文本信息，默认显示`退出`
     */
    fun setLeftTitleForId(resId: Int = R.string.back) {
        mTvLeftTitle.setText(resId)
        mTvLeftTitle.visibility = View.VISIBLE
        mIbLeftImage.visibility = View.GONE
    }

    /**
     * 设置 `TopBar` 左边 [ImageButton] 图片
     * @param resId [Int] 显示图片信息，默认显示`退出图片箭头`
     */
    fun setLeftImageForId(resId: Int = R.drawable.ic_arrow_back_black_24dp) {
        mIbLeftImage.setBackgroundResource(resId)
        mTvLeftTitle.visibility = View.GONE
        mIbLeftImage.visibility = View.VISIBLE
    }

    /**
     * 设置 `TopBar` 右边 [Textview] 文本信息
     * @param resId [Int] 显示文本信息，默认显示`刷新`
     */
    fun setRightTitleForId(resId: Int) {
        mTvRightTitle.setText(resId)
        mTvRightTitle.visibility = View.VISIBLE
        mIbRightImage.visibility = View.GONE
    }

    /**
     * 设置 `TopBar` 右边 [ImageButton] 图片
     * @param resId [Int] 显示图片信息，默认显示 `刷新图片箭头`
     */
    fun setRightImageForId(resId: Int) {
        mIbRightImage.setBackgroundResource(resId)
        mTvRightTitle.visibility = View.VISIBLE
        mIbRightImage.visibility = View.GONE
    }

    /**
     * `TopBar` 修改为白色背景主题，灰色字体
     */
    fun useTopBarThemeForWhite() {
        mLayoutTopView.setBackgroundResource(R.color.colorGlobalWhite)
        mTvLeftTitle.setTextColor(R.color.colorGlobalTextGray.color())
        mTvTitle.setTextColor(R.color.colorGlobalTextGray.color())
        mTvRightTitle.setTextColor(R.color.colorGlobalTextGray.color())
    }

    /**
     * `TopBar` 修改为 `App` 透明背景主题，白色字体
     */
    fun useTopBarThemeForTransparent() {
        mLayoutTopView.setBackgroundResource(R.color.colorGlobalTransparent)
        mTvLeftTitle.setTextColor(R.color.colorGlobalTextWhite.color())
        mTvTitle.setTextColor(R.color.colorGlobalTextWhite.color())
        mTvRightTitle.setTextColor(R.color.colorGlobalTextWhite.color())
    }

    /**
     * `TopBar` 修改为 `App` 默认背景主题,白色字体
     */
    fun useTopBarThemeForApp() {
        mLayoutTopView.setBackgroundResource(R.color.colorPrimary)
        mTvLeftTitle.setTextColor(R.color.colorGlobalTextWhite.color())
        mTvTitle.setTextColor(R.color.colorGlobalTextWhite.color())
        mTvRightTitle.setTextColor(R.color.colorGlobalTextWhite.color())
    }

}