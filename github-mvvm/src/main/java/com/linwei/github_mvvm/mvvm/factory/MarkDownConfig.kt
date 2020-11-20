package com.linwei.github_mvvm.mvvm.factory

import android.content.Context
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.syntax.Prism4jThemeDarkula
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import io.noties.markwon.recycler.table.TableEntryPlugin
import io.noties.markwon.syntax.SyntaxHighlightPlugin

/**
 * markdown文件显示配置
 */
@PrismBundle(includeAll = true, grammarLocatorClassName = ".MyGrammarLocator")
object MarkDownConfig {

//    fun getConfig(context: Context): ArrayList<AbstractMarkwonPlugin> {
//        val prism4j = Prism4j(MyGrammarLocator())
//        return arrayListOf(GlideImagesPlugin.create(context.applicationContext),
//                GlideImagesPlugin.create(context),
//                LinkifyPlugin.create(),
//                HtmlPlugin.create(),
//                TablePlugin.create(context),
//                TableEntryPlugin.create(context.applicationContext),
//                SyntaxHighlightPlugin.create(prism4j, Prism4jThemeDarkula.create(), "java"))
//    }
}



