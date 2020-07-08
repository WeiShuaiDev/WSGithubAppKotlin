package com.linwei.cams.database

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/20
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 获取设备数据库
 *-----------------------------------------------------------------------
 */
class DataBaseHelper(val mContext: Context) {
    private val mResolver: ContentResolver = mContext.contentResolver

    companion object {
        private var INSTANCE: DataBaseHelper? = null

        @JvmStatic
        fun getInstance(context: Context): DataBaseHelper {

            return INSTANCE ?: DataBaseHelper(context).apply {
                INSTANCE = this
            }
        }
    }

    /**
     * 获取系统所有联系人信息
     */
    fun getContacts(): ArrayList<ContactEntity> {
        val contactLists: ArrayList<ContactEntity> = arrayListOf()
        var cursor: Cursor? = null
        try {
            cursor =
                DataBaseProvide.getCursor(mResolver, CollectType.CONTACT) ?: return contactLists
            val contactIdNumbersMap: HashMap<Long, ArrayList<NumberEntity>> =
                DataBaseProvide.queryAllContactNumbers(mContext, mResolver)
            val contactDetailMap: HashMap<Long, ContactDetail> =
                DataBaseProvide.queryAllContactDetail(mResolver)
            while (cursor.moveToNext()) {
                val contactEntity: ContactEntity? =
                    DataBaseProvide.getOneContact(cursor, contactIdNumbersMap, contactDetailMap)
                if (contactEntity != null) {
                    contactLists.add(contactEntity)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor?.isClosed == false)
                cursor.close()
        }
        return contactLists
    }

    /**
     * 获取通话记录信息
     */
    @SuppressLint("MissingPermission")
    fun getCallLogs(maxCount: Int): ArrayList<CallLogEntity> {
        val logsLists: ArrayList<CallLogEntity> = arrayListOf()
        var cursor: Cursor? = null
        try {
            cursor = DataBaseProvide.getCursor(mResolver, CollectType.CALL_LOG) ?: return logsLists
            while (cursor.moveToNext()) {
                val callLogEntity: CallLogEntity? = DataBaseProvide.getOneCallLog(cursor)
                if (callLogEntity != null) {
                    logsLists.add(callLogEntity)
                }
                if (logsLists.size >= maxCount) {
                    break
                }
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }

        return logsLists
    }

    /**
     * 获取短信记录信息
     */
    @SuppressLint("MissingPermission")
    fun getSms(maxCount: Int): ArrayList<SmsEntity> {
        val smsLists: ArrayList<SmsEntity> = arrayListOf()
        var cursor: Cursor? = null
        try {
            cursor = DataBaseProvide.getCursor(mResolver, CollectType.SMS_LOG) ?: return smsLists
            while (cursor.moveToNext()) {
                val smsEntity: SmsEntity? = DataBaseProvide.getOneSms(cursor)
                if (smsEntity != null) {
                    smsLists.add(smsEntity)
                }
                if (smsLists.size >= maxCount) {
                    break
                }
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return smsLists
    }
}