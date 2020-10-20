package com.linwei.github_mvvm.mvvm.ui.adapter
import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.linwei.github_mvvm.R
import kotlinx.android.synthetic.main.layout_item_default_drop_down.view.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/19
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `RecommendedFragment` 模块 DropDownMenu 适配器。
 *-----------------------------------------------------------------------
 */
class ListDropDownAdapter(private val context: Context, private val list: List<String>) :
    BaseAdapter() {
    private var checkItemPosition = 0

    fun setCheckItem(position: Int) {
        checkItemPosition = position
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convert: View?, parent: ViewGroup): View {
        var convertView:View? = convert
        val viewHolder: ViewHolder?
        if (convertView != null) {
            viewHolder = convertView.tag as ViewHolder
        } else {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.layout_item_default_drop_down, null)
            viewHolder = ViewHolder()
            viewHolder.text = convertView.mDefaultDropText
            convertView.tag = viewHolder
        }
        fillValue(position, viewHolder)
        return convertView!!
    }

    private fun fillValue(position: Int, viewHolder: ViewHolder?) {
        viewHolder?.text?.text = list[position]
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder?.text?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                )
                viewHolder?.text?.setBackgroundResource(R.color.check_bg)
            } else {
                viewHolder?.text?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorDropItemText
                    )
                )
                viewHolder?.text?.setBackgroundResource(R.color.colorGlobalWhite)
            }
        }
    }

    internal class ViewHolder {
        var text: TextView? = null
    }
}