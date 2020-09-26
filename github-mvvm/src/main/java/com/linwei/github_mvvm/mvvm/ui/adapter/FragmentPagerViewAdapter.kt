package com.linwei.github_mvvm.mvvm.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/25
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Fragment的ViewPager适配器
 *-----------------------------------------------------------------------
 */
class FragmentPagerViewAdapter(
    private val fragmentList: List<Fragment>,
    supportFragmentManager: FragmentManager
) : FragmentPagerAdapter(supportFragmentManager) {

    override fun getCount(): Int {
        return fragmentList.size
    }


    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}