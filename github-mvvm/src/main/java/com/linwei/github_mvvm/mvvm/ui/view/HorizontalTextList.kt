package com.linwei.github_mvvm.mvvm.ui.view

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linwei.cams.ext.dp
import com.linwei.github_mvvm.R

/**
 * RecyclerView实现的横向TextView列表
 */
class HorizontalTextList : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    val mRecycler = RecyclerView(context)
    val mDataList: ArrayList<String> = arrayListOf()
    var mItemClick: OnItemClickListener? = null

    init {
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        mRecycler.adapter = TextItemAdapter(mDataList)
        mRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addView(mRecycler, params)
    }

    inner class TextItemAdapter(private val dataList: ArrayList<String>) :
        RecyclerView.Adapter<TextItemAdapter.VH>() {

        inner class VH(val v: TextView) : RecyclerView.ViewHolder(v)

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.v.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            val fontSize: Float = resources.getDimension(R.dimen.sp_14)
            holder.v.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
            holder.v.text = dataList[position] + "   > "

            holder.itemView.setOnClickListener {
                mItemClick?.onItemClick(it, position)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val textView = TextView(context)
            textView.setPadding(10.dp)
            textView.gravity = Gravity.CENTER
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            textView.layoutParams = layoutParams
            return VH(textView)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }
}

