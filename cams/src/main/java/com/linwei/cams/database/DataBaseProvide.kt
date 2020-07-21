package com.linwei.cams.database

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.CallLog
import android.provider.ContactsContract

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/20
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 提供数据库查询功能
 *-----------------------------------------------------------------------
 */
object DataBaseProvide {

    @SuppressLint("MissingPermission")
    fun getCursor(resolver: ContentResolver?, type: CollectType): Cursor? {
        if (resolver == null) return null
        when (type) {
            CollectType.CONTACT -> {
                return resolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null
                )
            }
            CollectType.CALL_LOG -> {
                return resolver.query(
                    CallLog.Calls.CONTENT_URI,
                    arrayOf(
                        CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.TYPE,
                        CallLog.Calls.DATE,
                        CallLog.Calls.DURATION
                    ),
                    null,
                    null,
                    CallLog.Calls.DEFAULT_SORT_ORDER
                )
            }
            CollectType.SMS_LOG -> {
                return resolver.query(
                    Uri.parse("content://sms"), arrayOf(
                        "person",  //reference to item in {@code content://contacts/people}
                        "address",  //The address of the other party.
                        "type",
                        "date",
                        "body",
                        "subject"
                    ),
                    null,
                    null,
                    "date DESC"
                )
            }
            else -> {
                return null
            }
        }
    }

    /**
     * 查询一条通话记录
     */
    fun getOneCallLog(cursor: Cursor?): CallLogEntity? {
        if (cursor == null) return null

        return CallLogEntity(
            cursor.getString(0), cursor.getString(1),
            cursor.getString(2), cursor.getString(3), cursor.getString(4)
        )
    }

    /**
     * 查询一条短信记录
     */
    fun getOneSms(cursor: Cursor?): SmsEntity? {
        if (cursor == null) return null

        return SmsEntity(
            cursor.getString(1), cursor.getInt(2),
            cursor.getLong(3), cursor.getString(4),
            cursor.getString(5)
        )
    }

    /**
     * 查询一条联系人信息
     */
    fun getOneContact(
        cursor: Cursor?, contactIdNumbersMap: Map<Long, List<NumberEntity>>,
        contactDetailMap: Map<Long, ContactDetail>
    ): ContactEntity? {
        if (cursor == null) return null

        val name: String =
            cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))

        val contactId: Long = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))

        val detail: ContactDetail? = contactDetailMap[contactId]
        val number: List<NumberEntity>? = contactIdNumbersMap[contactId]
        return ContactEntity(
            name,
            number,
            readLastUpdateTime(cursor),
            detail?.contact_times,
            detail?.last_contact_time,
            detail?.nickname,
            detail?.relation,
            detail?.status
        )

    }

    /**
     * 读取最后一次修改时间
     */
    private fun readLastUpdateTime(cursor: Cursor?): Long {
        if (cursor == null) return 0

        var index: Int = cursor.getColumnIndex("contact_last_updated_timestamp")
        if (index > -1) {
            return cursor.getLong(index)
        }

        index = cursor.getColumnIndex("contact_status_ts")
        if (index > -1) {
            return cursor.getLong(index)
        }
        return 0
    }


    /**
     * 读取一条电话号码
     */
    private fun readOneContactNumber(context: Context, cursor: Cursor?): NumberEntity? {
        if (cursor == null) return null
        return NumberEntity(
            readNumber(cursor),
            readLastContactTime(cursor).toString(),
            readContactTimes(cursor),
            readCustomerData(context, cursor)
        )

    }

    /**
     * 读取联系人号码
     */
    private fun readNumber(cursor: Cursor?): String? {
        if (cursor == null) return ""
        var number: String? =
            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        number = number?.replace("-", "")
        number = number?.replace(" ", "")
        return number
    }

    /**
     * 读取联系人时间
     */
    private fun readContactTimes(cursor: Cursor?): Int? {
        if (cursor == null) return 0

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            @Suppress("DEPRECATION") val index: Int =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_USED)
            if (index > -1) {
                return cursor.getInt(index)
            }
        } else {
            val index: Int = cursor.getColumnIndex("times_contacted")
            if (index > -1) {
                return cursor.getInt(index)
            }
        }
        return 0
    }

    /**
     * 读取联系人最后一次时间
     */
    private fun readLastContactTime(cursor: Cursor?): Long? {
        if (cursor == null) return 0

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            @Suppress("DEPRECATION") val index: Int =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_USED)
            if (index > -1) {
                return cursor.getLong(index)
            }
        } else {
            val index: Int = cursor.getColumnIndex("last_time_contacted")
            if (index > -1) {
                return cursor.getLong(index)
            }
        }
        return 0
    }

    /**
     * 读取客户数据
     */
    private fun readCustomerData(context: Context, cursor: Cursor?): String? {
        if (cursor == null) return ""
        val data2Index: Int =
            cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2))
        if (data2Index > -1) {
            val typeLabel =
                ContactsContract.CommonDataKinds.Phone.getTypeLabel(
                    context.getResources(),
                    data2Index,
                    "CUSTOME"
                )
            if (typeLabel != null) {
                return typeLabel.toString()
            }
        }
        return ""
    }


    /**
     * 查询所有电话号码
     */
    fun queryAllContactNumbers(
        context: Context,
        resolver: ContentResolver
    ): HashMap<Long, ArrayList<NumberEntity>> {
        var cursor: Cursor? = null
        val idNumbersMap: HashMap<Long, ArrayList<NumberEntity>> =
            hashMapOf()

        try {
            cursor = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            if (cursor == null) return idNumbersMap
            while (cursor.moveToNext()) {
                val contactId: Long =
                    cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                val contactNumber: NumberEntity? =
                    readOneContactNumber(context, cursor)

                if (contactNumber != null) {
                    var numbers: ArrayList<NumberEntity>? = idNumbersMap[contactId]
                    if (numbers == null) {
                        numbers = arrayListOf(contactNumber)
                        idNumbersMap[contactId] = numbers
                    } else {
                        numbers.add(contactNumber)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor?.isClosed == false) {
                cursor.close()
            }
        }
        return idNumbersMap
    }

    /**
     * 查询所有联系人详情信息
     */
    fun queryAllContactDetail(resolver: ContentResolver): HashMap<Long, ContactDetail> {
        val contactIdDetailMap: HashMap<Long, ContactDetail> = hashMapOf()
        var cursor: Cursor? = null
        try {
            cursor =
                resolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                ) ?: return contactIdDetailMap

            while (cursor.moveToNext()) {
                val contactId =
                    cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))
                val contactDetail: ContactDetail? = queryContactDetail(resolver, contactId)
                if (contactDetail != null) {
                    contactIdDetailMap[contactId] = contactDetail
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor?.isClosed == false) {
                cursor.close()
            }
        }
        return contactIdDetailMap
    }

    /**
     * 查询联系人详情信息
     */
    private fun queryContactDetail(resolver: ContentResolver, contactId: Long): ContactDetail? {
        var contactDetail: ContactDetail? = null

        val cursor: Cursor =
            resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                "${ContactsContract.Data.CONTACT_ID} = ${contactId}",
                null,
                null
            ) ?: return contactDetail

        if (cursor.moveToNext()) {
            @Suppress("DEPRECATION") val contactTimes: Int =
                cursor.getInt(cursor.getColumnIndex(ContactsContract.Data.TIMES_CONTACTED))
            val status: String =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_STATUS))
            @Suppress("DEPRECATION") val lastContact: Long =
                cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.LAST_TIME_CONTACTED))

            var columnIndex: Int = cursor.getColumnIndex("nickname")
            var nicky: String? = ""
            if (columnIndex > -1) {
                nicky = cursor.getString(columnIndex)
            }

            columnIndex = cursor.getColumnIndex("data2")
            var relationShip: String? = ""
            if (columnIndex > -1) {
                relationShip = cursor.getString(columnIndex)
            }

            contactDetail = ContactDetail(contactTimes, lastContact, nicky, relationShip, status)
        }
        cursor.close()
        return contactDetail
    }

    /**
     * 根据自定的{@link Uri}地址，获取联系人信息
     */
    fun queryContactByUri(resolver: ContentResolver, uri: Uri?): Array<String>? {
        var cursor: Cursor? = null
        try {
            if (uri != null) {
                cursor = resolver.query(
                    uri, arrayOf(
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ), null, null, null
                )
                while (cursor != null && cursor.moveToNext()) {
                    val number: String = cursor.getString(0)
                    val name: String = cursor.getString(1)
                    return arrayOf(name, number)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor?.isClosed == false)
                cursor.close()
        }
        return null
    }
}