package com.linwei.github_mvvm.ext

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.linwei.cams.base.holder.ItemViewHolder
import com.linwei.cams.ext.*
import com.linwei.cams.http.glide.GlideLoadOption
import com.linwei.cams.manager.ImageLoaderManager
import com.linwei.cams.utils.DialogUtils
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.ui.module.login.UserActivity
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposDetailActivity
import com.linwei.github_mvvm.mvvm.ui.view.MarkdownInputIconList
import jp.wasabeef.glide.transformations.BlurTransformation
import org.jetbrains.anko.browse
import java.util.regex.Pattern

/**
 * Navigation 的页面跳转
 * @param view [View]
 * @param args [Bundle]
 * @param actionId [Int]
 * @param inclusive [Boolean]
 */
fun navigationPopUpTo(
    view: View,
    args: Bundle?,
    actionId: Int,
    inclusive: Boolean
) {
    val controller: NavController = Navigation.findNavController(view)
    controller.navigate(
        actionId,
        args, NavOptions.Builder().setPopUpTo(controller.graph.id, inclusive).build()
    )
}

/**
 * Navigation 退出
 * @param view [View]
 */
fun navigationBack(view: View) {
    val controller: NavController = Navigation.findNavController(view)
    controller.popBackStack()
}

/**
 * 加载用户头像图片
 * @param url [String]
 * @param size [Point]
 */
fun ImageView.loadUserHeaderImage(url: String, size: Point = Point(50.dp, 50.dp)) {
    val option: GlideLoadOption = GlideLoadOption()
        .setDefaultImg(R.mipmap.ic_launcher)
        .setErrorImg(R.mipmap.ic_launcher)
        .setCircle(true)
        .setSize(size)
        .setUri(url)
    ImageLoaderManager.sInstance.imageLoader().loadImage(option, this, null)
}

/**
 * 加载高斯模糊图片
 * @param url [String]
 */
fun ImageView.loadImageBlur(url: String) {
    val process = BlurTransformation()
    val option: GlideLoadOption = GlideLoadOption()
        .setDefaultImg(R.mipmap.ic_launcher)
        .setErrorImg(R.mipmap.ic_launcher)
        .setUri(url)
        .setTransformations(process)

    ImageLoaderManager.sInstance.imageLoader().loadImage(option, this, null)
}

/**
 * @param context [Context]
 * @param url [String]
 */
fun launchUrl(context: Context, url: String?) {
    if (!url.isNotNullOrEmpty()) return
    val parseUrl: Uri = url!!.toUri()
    var isImage: Boolean = parseUrl.toString().isImageEnd()
    if (parseUrl.toString().endsWith("?raw=true")) {
        isImage = parseUrl.toString().replace("?raw=true", "").isImageEnd()
    }
    if (isImage) {
        var imageUrl: String = url
        if (!parseUrl.toString().endsWith("?raw=true")) {
            imageUrl = "$url?raw=true"
        }
        //ImagePreViewActivity.gotoImagePreView(imageUrl)
        return
    }

    if (parseUrl.host == "github.com" && parseUrl.path.isNotNullOrEmpty()) {
        val pathNames: List<String> = parseUrl.path!!.split("/")
        if (pathNames.size == 2) {
            //解析人
            val userName: String = pathNames[1]
            //PersonActivity.gotoPersonInfo(userName) 
        } else if (pathNames.size >= 3) {
            val userName: String = pathNames[1]
            val repoName: String = pathNames[2]
            //解析仓库
            if (pathNames.size == 3) {
                ReposDetailActivity.start(context, userName, repoName)
            } else {
                context.browse(url)
            }
        }
    } else if (url.startsWith("http")) {
        context.browse(url)
    }
}

private val sImageEndTag: ArrayList<String> = arrayListOf(".png", ".jpg", ".jpeg", ".gif", ".svg")

fun String.isImageEnd(): Boolean {
    var image = false
    sImageEndTag.forEach {
        if (this.indexOf(it) + it.length == this.length) {
            image = true
        }
    }
    return image
}

/**
 * 重新在新的任务栈中，启动 [UserActivity]
 */
fun jumpUserActivity() {
    ctx.startActivity(Intent(ctx, UserActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

/**
 * 拓展获取版本号
 */
fun Context.getVersionName(): String {
    val manager: PackageInfo = packageManager.getPackageInfo(packageName, 0)
    return manager.versionName
}

/**
 * 拓展复制到粘粘版
 */
fun Context.copy(string: String) {
    val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE)
            as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("", string)
    clipboardManager.setPrimaryClip(clip)
}


/**
 * 拓展列表到文本转化
 */
fun ArrayList<String>.toSplitString(): String {
    var result = ""
    this.forEach {
        result = "$result/$it"
    }
    return result.replace("./", "")
}

/**
 * 拓展String版本号对比
 */
fun String.compareVersion(v2: String?): String? {
    if (v2 == null || v2.isEmpty()) return null
    val regEx = "[^0-9]"
    val p: Pattern = Pattern.compile(regEx)
    var s1: String = p.matcher(this).replaceAll("").trim()
    var s2: String = p.matcher(v2).replaceAll("").trim()

    val cha: Int = s1.length - s2.length
    val buffer = StringBuffer()
    var i = 0
    while (i < Math.abs(cha)) {
        buffer.append("0")
        ++i
    }

    if (cha > 0) {
        buffer.insert(0, s2)
        s2 = buffer.toString()
    } else if (cha < 0) {
        buffer.insert(0, s1)
        s1 = buffer.toString()
    }

    val s1Int: Int = s1.toInt()
    val s2Int: Int = s2.toInt()

    return if (s1Int > s2Int) this
    else v2
}

fun Context.showIssueEditDialog(
    title: String,
    needEditTitle: Boolean,
    editTitle: String?,
    editContent: String?,
    listener: IssueDialogClickListener
) {
    DialogUtils.createCustomDialog(this, R.layout.layout_issue_edit_dialog,
        R.style.dialog_style, object : DialogUtils.OnViewCreatedListener {
            override fun onCreatedView(dialog: Dialog, viewHolder: ItemViewHolder) {
                val titleView: EditText = viewHolder.getEditText(R.id.issue_dialog_edit_title)
                val contentView: EditText = viewHolder.getEditText(R.id.issue_dialog_edit_content)
                //设置`Dialog` 显示框高度
                viewHolder.getView(R.id.issue_dialog_content_layout).layoutParams.height =
                    (Resources.getSystem().displayMetrics.heightPixels * 0.6).toInt()

                (viewHolder.getView(R.id.issue_dialog_markdown_list) as MarkdownInputIconList).editText =
                    contentView

                viewHolder.getTextView(R.id.issue_dialog_title).text = title

                needEditTitle.yes {
                    titleView.visibility = View.VISIBLE
                }.otherwise {
                    titleView.visibility = View.GONE
                }

                editTitle?.let {
                    titleView.setText(it)
                }

                editContent?.let {
                    contentView.setText(it)
                }

                viewHolder.getTextView(R.id.issue_dialog_edit_cancel).setOnClickListener {
                    dialog.dismiss()
                }

                viewHolder.getTextView(R.id.issue_dialog_edit_ok).setOnClickListener {
                    needEditTitle.yes {
                        isEmptyParameter(titleView.text.string()).yes {
                            R.string.issue_title_empty.showShort()
                            return@setOnClickListener
                        }
                    }

                    isEmptyParameter(contentView.text.string()).yes {
                        R.string.issue_content_empty.showShort()
                        return@setOnClickListener
                    }

                    listener.onConfirm(
                        dialog,
                        title,
                        titleView.text.string(),
                        contentView.text.string()
                    )
                }

            }
        }).show()
}

interface IssueDialogClickListener {
    fun onConfirm(dialog: Dialog, title: String, editTitle: String?, editContent: String?)
}




