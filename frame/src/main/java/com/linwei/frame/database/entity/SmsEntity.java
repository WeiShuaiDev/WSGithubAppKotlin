package com.linwei.frame.database.entity;

/**
 * 跟短信记录数据库中的字段直接对应的pojo
 * */
public class SmsEntity {

    private String address;

    private int type;

    private long date;

    private String body;

    private String subject;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "SmsEntity{" +
                "type=" + type +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    private static String[] smsType = {
            "MESSAGE_TYPE_ALL",
            "MESSAGE_TYPE_INBOX",
            "MESSAGE_TYPE_SENT",
            "MESSAGE_TYPE_DRAFT",
            "MESSAGE_TYPE_OUTBOX",
            "MESSAGE_TYPE_FAILED",
            "MESSAGE_TYPE_QUEUED",
            "MESSAGE_UNKNOW_TYPE"
    };
    public static String getSmsTypeDesc(int type){
        if(type > 0 && type < smsType.length){
            return smsType[type];
        }else{
            return smsType[0];
        }
    }
}