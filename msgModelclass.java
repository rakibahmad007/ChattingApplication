package com.example.chattingapplication;

public class msgModelclass {
    private String message;
    private String senderId;
    private String receiverId;
    private String senderName;
    private String receiverName;
    private String senderProfilePic;
    private String receiverProfilePic;
    private long timestamp;

    // Constructor
    public msgModelclass(String message, String senderId, String receiverId, String senderName, String receiverName,
                         String senderProfilePic, String receiverProfilePic, long timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderProfilePic = senderProfilePic;
        this.receiverProfilePic = receiverProfilePic;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderProfilePic() {
        return senderProfilePic;
    }

    public void setSenderProfilePic(String senderProfilePic) {
        this.senderProfilePic = senderProfilePic;
    }

    public String getReceiverProfilePic() {
        return receiverProfilePic;
    }

    public void setReceiverProfilePic(String receiverProfilePic) {
        this.receiverProfilePic = receiverProfilePic;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
