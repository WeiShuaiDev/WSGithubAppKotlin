package com.linwei.github_mvvm.mvvm.factory
import com.linwei.github_mvvm.R
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/4
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
object IconFontFactory : ITypeface {
    override val author: String
        get() = "Iconfont"
    override val characters: Map<String, Char>
        get() = Icon.values().associate { it.name to it.character }
    override val description: String
        get() = "GitHub's icon font https://octicons.github.com/"
    override val fontName: String
        get() = "Iconfont"
    override val fontRes: Int
        get() = R.font.iconfont
    override val iconCount: Int
        get() = characters.size
    override val icons: List<String>
        get() = characters.keys.toCollection(LinkedList())
    override val license: String
        get() = " SIL OFL 1.1"
    override val licenseUrl: String
        get() = "http://scripts.sil.org/OFL"
    override val mappingPrefix: String
        get() = "APP"
    override val url: String
        get() = "https://github.com/github/octicons"
    override val version: String
        get() = "1.0.0"

    override fun getIcon(key: String): IIcon = Icon.valueOf(key)

    enum class Icon constructor(override val character: Char) : IIcon {

        APP_HOME('\ue624'),
        APP_MORE('\ue674'),
        APP_SEARCH('\ue61c'),
        APP_EMAIL('\ue685'),
        APP_FOCUS('\ue60a'),
        APP_UN_FOCUS('\ue60b'),
        APP_DES('\ue6c4'),

        APP_MAIN_DT('\ue684'),
        APP_MAIN_QS('\ue818'),
        APP_MAIN_MY('\ue6d0'),
        APP_MAIN_SEARCH('\ue61c'),

        APP_LOGIN_USER('\ue666'),
        APP_LOGIN_PW('\ue60e'),

        APP_REPOS_ITEM_USER('\ue63e'),
        APP_REPOS_ITEM_STAR('\ue643'),
        APP_REPOS_ITEM_FORK('\ue67e'),
        APP_REPOS_ITEM_ISSUE('\ue661'),
        APP_REPOS_ITEM_OPEN('\ue653'),
        APP_REPOS_ITEM_ALL('\ue603'),
        APP_REPOS_ITEM_CLOSE('\ue65c'),

        APP_REPOS_ITEM_STARED('\ue698'),
        APP_REPOS_ITEM_WATCH('\ue681'),
        APP_REPOS_ITEM_WATCHED('\ue629'),
        APP_REPOS_ITEM_FILE('\uea77'),
        APP_REPOS_ITEM_NEXT('\ue610'),

        APP_USER_ITEM_COMPANY('\ue63e'),
        APP_USER_ITEM_LOCATION('\ue7e6'),
        APP_USER_ITEM_LINK('\ue670'),
        APP_USER_NOTIFY('\ue600'),

        APP_ISSUE_ITEM_ISSUE('\ue661'),
        APP_ISSUE_ITEM_COMMENT('\ue6ba'),
        APP_ISSUE_ITEM_ADD('\ue662'),

        APP_NOTIFY_ALL_READ('\ue62f'),

        APP_PUSH_ADD('\ue605'),
        APP_PUSH_REDUCE('\ue63d'),
        APP_PUSH_EDIT('\ue611'),


        APP_MD_1('\ue60c'),
        APP_MD_2('\ue620'),
        APP_MD_3('\ue621'),
        APP_MD_4('\ue654'),
        APP_MD_5('\ue613'),
        APP_MD_6('\ue63a'),
        APP_MD_7('\uea77'),
        APP_MD_8('\ue670'),
        APP_MD_9('\ue651');

        override val typeface: ITypeface by lazy { IconFontFactory }
    }

}