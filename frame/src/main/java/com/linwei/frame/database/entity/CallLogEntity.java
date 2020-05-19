package com.linwei.frame.database.entity;
/**
 * 跟通话记录数据库中的字段直接对应的pojo
 * */
public class CallLogEntity {
    private String cachedName;
    private String number;
    private int type;
    private long date;
    private long duration;

    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String cachedName) {
        this.cachedName = cachedName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CallLogEntity{" +
                "cachedName='" + cachedName + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }

    private static String[] callLogType = {
            "UNKNOW_TYPE", //placeholder
            "INCOMING_TYPE",
            "OUTGOING_TYPE",
            "MISSED_TYPE",
            "VOICEMAIL_TYPE",
            "REJECTED_TYPE",
            "BLOCKED_TYPE",
            "ANSWERED_EXTERNALLY_TYPE"
    };
    public static String getCallLogTypeDesc(int type){
        if(type > 0 && type < callLogType.length){
            return callLogType[type];
        }else{
            return callLogType[0];
        }
    }
}
