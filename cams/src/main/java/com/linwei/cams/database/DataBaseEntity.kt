package com.linwei.cams.database

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/19
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 数据库实体类
 *-----------------------------------------------------------------------
 */

/**
 * 通话记录信息
 * */
data class CallLogEntity(
    val cachedName: String="", val number: String="",
    val type: String="", val date: String="", val duration: String=""
) {

    companion object {
        val callLogTypes: Array<String> = arrayOf(
            "UNKNOW_TYPE",
            "INCOMING_TYPE",
            "OUTGOING_TYPE",
            "MISSED_TYPE",
            "VOICEMAIL_TYPE",
            "REJECTED_TYPE",
            "BLOCKED_TYPE",
            "ANSWERED_EXTERNALLY_TYPE"
        )

        @JvmStatic
        fun getCallLogTypeDesc(type: Int = 0): String {
            if (type > 0 && type < callLogTypes.size) {
                return callLogTypes[type]
            }
            return callLogTypes[0]
        }
    }


    override fun toString(): String {
        return "CallLogEntity(cachedName='$cachedName', number='$number', type='$type', date='$date', duration='$duration')"
    }
}

/**
 * 通讯录信息
 */
data class ContactEntity(
    val name: String="", val number: List<NumberEntity>?=null,
    val lastUpdate: Long?=0, val contact_times: Int?=0, val last_contact_time: Long?=0,
    val nickname: String?="", val relation: String?="", val status: String?=""
) {
    override fun toString(): String {
        return "ContactEntity(name='$name', number=$number, lastUpdate=$lastUpdate, contact_times=$contact_times, last_contact_time=$last_contact_time, nickname='$nickname', relation='$relation', status='$status')"
    }
}

data class NumberEntity(
    val number: String?="", val last_time_used: String?="",
    val time_used: Int?=0, val type_label: String?=""
) {
    override fun toString(): String {
        return "NumberEntity(number='$number', last_time_used='$last_time_used', time_used=$time_used, type_label='$type_label')"
    }
}

data class ContactDetail(
    val contact_times: Int? = 0, val last_contact_time: Long? = 0,
    val nickname: String? = "", val relation: String? = "", val status: String? = ""
) {
    override fun toString(): String {
        return "ContactDetail(contact_times=$contact_times, last_contact_time=$last_contact_time, nickname='$nickname', relation='$relation', status='$status')"
    }
}

/**
 * 短信记录信息
 */
data class SmsEntity(
    val address: String="", val type: Int=0, val date: Long=0,
    val body: String="", val subject: String=""
) {

    companion object {
        val smsType: Array<String> = arrayOf(
            "MESSAGE_TYPE_ALL",
            "MESSAGE_TYPE_INBOX",
            "MESSAGE_TYPE_SENT",
            "MESSAGE_TYPE_DRAFT",
            "MESSAGE_TYPE_OUTBOX",
            "MESSAGE_TYPE_FAILED",
            "MESSAGE_TYPE_QUEUED",
            "MESSAGE_UNKNOW_TYPE"
        )

        @JvmStatic
        fun getSmsTypeDesc(type: Int = 0): String {
            if (type > 0 && type < smsType.size) {
                return smsType[type]
            }
            return smsType[0]
        }
    }

    override fun toString(): String {
        return "SmsEntity(address='$address', type=$type, date=$date, body='$body', subject='$subject')"
    }
}