package com.linwei.frame.database.entity;

/**
 * @Author: ws
 * @Time: 2019/11/11
 * @Description: 安装应用信息
 */
public class ApplistEntity {
    private String firstTime;
    private String lastTime;
    private String name;
    private String packageName;
    private String versionCode;

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "ApplistEntity{" +
                "firstTime='" + firstTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}';
    }
}
