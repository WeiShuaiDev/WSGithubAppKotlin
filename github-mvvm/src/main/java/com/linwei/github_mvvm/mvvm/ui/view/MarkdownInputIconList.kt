package com.linwei.github_mvvm.mvvm.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linwei.cams.ext.color
import com.linwei.cams.ext.dp
import com.linwei.github_mvvm.R
import com.mikepenz.iconics.view.IconicsTextView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/17
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MarkdownInputIconList : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val mRecycler = RecyclerView(context)
    private val mListData: ArrayList<String> = arrayListOf(
        "{APP_MD_1}", "{APP_MD_2}", "{APP_MD_3}", "{APP_MD_4}",
        "{APP_MD_5}", "{APP_MD_6}", "{APP_MD_7}", "{APP_MD_8}", "{APP_MD_9}"
    )
    var itemClick: AdapterView.OnItemClickListener? = null
    var editText: EditText? = null

    init {
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        mRecycler.adapter = TextItemAdapter(mListData)
        mRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addView(mRecycler, layoutParams)

        itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
            var text: CharSequence = editText?.text ?: ""
            when (position) {
                0 -> {
                    text = "$text\n#"
                }
                1 -> {
                    text = "$text\n##"
                }
                2 -> {
                    text = "$text\n###"
                }
                3 -> {
                    text = "$text ** ** "
                }
                4 -> {
                    text = "$text * * "
                }
                5 -> {
                    text = "$text `` "
                }
                6 -> {
                    text = "$text\n```\n\n```\n"
                }
                7 -> {
                    text = "$text []() "
                }
                8 -> {
                    text = "$text\n-\n"
                }
            }
            editText?.setText(text)
        }
    }

    inner class TextItemAdapter(private val data: ArrayList<String>) :
        RecyclerView.Adapter<TextItemAdapter.VH>() {

        inner class VH(val v: TextView) : RecyclerView.ViewHolder(v)

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.v.setTextColor(R.color.colorPrimary.color())
            val fontSize: Float = resources.getDimension(R.dimen.sp_14)
            holder.v.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)

            holder.v.text = data[position]

            holder.itemView.setOnClickListener {
                itemClick?.onItemClick(null, it, position, 0)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val textView = IconicsTextView(context)
            textView.setPadding(10.dp)
            textView.gravity = Gravity.CENTER
            textView.setBackgroundResource(R.drawable.select_primary_light_bg)
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            textView.layoutParams = layoutParams
            return VH(textView)
        }
    }
}

