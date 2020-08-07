package com.linwei.cams_mvp_template.mvp.model

import com.linwei.cams.manager.RepositoryManager
import com.linwei.cams_mvp.mvp.BaseModel
import com.linwei.cams_mvp_template.mvp.contract.MainContract
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainModel @Inject constructor(repositoryManager: RepositoryManager) :
    BaseModel(repositoryManager), MainContract.Model {


}