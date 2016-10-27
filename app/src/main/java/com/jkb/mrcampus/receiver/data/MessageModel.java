package com.jkb.mrcampus.receiver.data;

/**
 * 消息的model类
 * Created by JustKiddingBaby on 2016/10/23.
 */

public class MessageModel {


    /**
     * contentType : operation
     * senderId : 1
     * targetId : 2
     * targetType : user
     * targetName : hello1213
     * targetPicture : http://lorempixel.com/640/480/?73711
     * senderType : user
     * senderName : lyf1
     * senderPicture : http://lorempixel.com/640/480/?70794
     */

    private String contentType;
    private int senderId;
    private int targetId;
    private String targetType;
    private String targetName;
    private String targetPicture;
    private String senderType;
    private String senderName;
    private String senderPicture;
    private String relationContent;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetPicture() {
        return targetPicture;
    }

    public void setTargetPicture(String targetPicture) {
        this.targetPicture = targetPicture;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }

    public String getRelationContent() {
        return relationContent;
    }

    public void setRelationContent(String relationContent) {
        this.relationContent = relationContent;
    }
}
