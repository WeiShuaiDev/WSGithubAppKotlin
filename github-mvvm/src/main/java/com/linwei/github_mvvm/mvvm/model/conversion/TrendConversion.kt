package com.linwei.github_mvvm.mvvm.model.conversion

import com.linwei.github_mvvm.mvvm.model.bean.TrendingRepoModel
import java.lang.Exception

/**
 * Html String 到 趋势相关实体转换
 */
val TAGS: HashMap<String, HashMap<String, String>> = hashMapOf(
    Pair(
        "meta",
        hashMapOf(
            Pair("start", "<span class=\"d-inline-block float-sm-right\""),
            Pair("flag", "/svg>"),
            Pair("end", "</span>end")
        )
    ),
    Pair(
        "starCount",
        hashMapOf(
            Pair("start", "<svg aria-label=\"star\""),
            Pair("flag", "/svg>"),
            Pair("end", "</a>")
        )
    ),
    Pair(
        "forkCount",
        hashMapOf(
            Pair("start", "<svg aria-label=\"repo-forked\""),
            Pair("flag", "/svg>"),
            Pair("end", "</a>")
        )
    )
)

object TrendConversion {
    fun htmlToRepo(resultData: String): ArrayList<TrendingRepoModel> {
        mapOf<String, String>()
        var responseData = ""
        try {
            responseData = resultData.replace(Regex("\n"), "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val repos = ArrayList<TrendingRepoModel>()
        var splitWithH3:List<String> = responseData.split("<article")
        splitWithH3 = splitWithH3.subList(1, splitWithH3.size)

        for (element:String in splitWithH3) {
            val repo = TrendingRepoModel()
            val html:String = element

            parseRepoBaseInfo(repo, html)

            val metaNoteContent:String =
                parseContentWithNote(html, "class=\"f6 text-gray mt-2\">", "</div>") + "end"
            repo.meta = parseRepoLabelWithTag(repo, metaNoteContent, TAGS["meta"]!!)
            repo.starCount = parseRepoLabelWithTag(repo, metaNoteContent, TAGS["starCount"]!!)
            repo.forkCount = parseRepoLabelWithTag(repo, metaNoteContent, TAGS["forkCount"]!!)

            parseRepoLang(repo, metaNoteContent)
            parseRepoContributors(repo, metaNoteContent)
            repos.add(repo)
        }
        return repos
    }

    private fun parseContentWithNote(htmlStr: String, startFlag: String, endFlag: String): String {
        var noteStar:Int = htmlStr.indexOf(startFlag)
        if (noteStar == -1) {
            return ""
        } else {
            noteStar += startFlag.length
        }

        val noteEnd:Int = htmlStr.indexOf(endFlag, noteStar)
        val content:String = htmlStr.substring(noteStar, noteEnd)
        return content.trim()
    }

    private fun parseRepoBaseInfo(repo: TrendingRepoModel, htmlBaseInfo: String) {
        val urlIndex:Int = htmlBaseInfo.indexOf("<a href=\"") + "<a href=\"".length
        val url:String = htmlBaseInfo.substring(urlIndex, htmlBaseInfo.indexOf("\">", urlIndex))
        repo.url = url
        repo.fullName = url.substring(1, url.length)
        if (repo.fullName.indexOf('/') != -1) {
            repo.name = repo.fullName.split('/')[0]
            repo.reposName = repo.fullName.split('/')[1]
        }

        var description:String =
            parseContentWithNote(htmlBaseInfo, "<p class=\"col-9 text-gray my-1 pr-4\">", "</p>")
        if (description.isNotEmpty()) {
            val reg = "<g-emoji.*?>.+?</g-emoji>"
            val tag = Regex(reg)
            val tags:Sequence<MatchResult> = tag.findAll(description)
            for (m:MatchResult in tags) {
                val match:String? = m.groups[0]?.value?.replace(Regex("<g-emoji.*?>"), "")
                    ?.replace(Regex("</g-emoji>"), "")
                description = description.replace(Regex(m.groups[0]?.value!!), match!!)
            }
        }
        repo.description = description
    }

    private fun parseRepoLabelWithTag(
        repo: TrendingRepoModel,
        noteContent: String,
        tag: Map<String, String>
    ): String {
        val startFlag:String = if (TAGS["starCount"] == tag || TAGS["forkCount"] == tag) {
            //tag["start"] + " href=\"/" + repo.fullName + tag["flag"]
            tag["start"] ?: error("")
        } else {
            tag["start"] ?: error("")
        }
        val content:String = parseContentWithNote(noteContent, startFlag, tag["end"] ?: error(""))
        return if (content.indexOf(tag["flag"] ?: error("")) != -1 && (content.indexOf(
                tag["flag"] ?: error("")
            ) + "</span>".length <= content.length)
        ) {
            val metaContent:String = content.substring(
                content.indexOf(tag["flag"] ?: error("")) + (tag["flag"] ?: error("")).length,
                content.length
            )
            metaContent.trim()
        } else {
            content.trim()
        }
    }

    private fun parseRepoLang(repo: TrendingRepoModel, metaNoteContent: String) {
        val content:String = parseContentWithNote(metaNoteContent, "programmingLanguage\">", "</span>")
        repo.language = content.trim()
    }

    private fun parseRepoContributors(repo: TrendingRepoModel, htmlContributorsString: String) {
        val htmlContributors:String = parseContentWithNote(htmlContributorsString, "Built by", "</a>")
        val splitWitSemicolon:List<String> = htmlContributors.split("\"")
        if (splitWitSemicolon.size < 2) {
            repo.contributors = arrayListOf("")
            return
        }
        repo.contributorsUrl = splitWitSemicolon[1]
        val contributors = ArrayList<String>()

        for (element:String in splitWitSemicolon) {
            val url:String = element
            if (url.indexOf("http") != -1) {
                contributors.add(url)
            }
        }

        repo.contributors = contributors
    }
}