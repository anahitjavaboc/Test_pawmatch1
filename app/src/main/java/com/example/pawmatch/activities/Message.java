package com.example.pawmatch.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

// Entity class to represent a message
public class Message implements Comparable<Message>, Cloneable {
    private String id;
    private String senderId;
    private String receiverId;
    private String content;
    private Date timestamp;
    private boolean isRead;
    private String imageUrl;
    private String messageType; // "text", "image", etc.

    // Enum for message status
    public enum MessageStatus {
        SENT, DELIVERED, READ
    }

    private MessageStatus status;

    public Message() {
        timestamp = new Date();
        isRead = false;
        status = MessageStatus.SENT; // Default to SENT
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    // Helper method to check if the message is valid
    public boolean isValid() {
        return senderId != null && !senderId.isEmpty() &&
                receiverId != null && !receiverId.isEmpty() &&
                content != null && !content.isEmpty();
    }

    // Check if this is an image-based message
    public boolean isImageMessage() {
        return messageType != null && messageType.equalsIgnoreCase("image");
    }

    // Format the timestamp for display
    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(timestamp);
    }

    // Convert to JSON string using Gson
    public String toJson() {
        return new gson().toJson(this);
    }

    // Create a Message object from JSON string
    public static Message fromJson(String json) {
        return new gson().fromJson(json, Message.class);
    }

    // Clone method for duplicating the object
    @Override
    public Message clone() {
        try {
            Message cloned = (Message) super.clone();
            cloned.timestamp = new Date(this.timestamp.getTime()); // Deep copy timestamp
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Shouldn't happen
        }
    }

    // Sorting by timestamp (oldest to newest)
    @Override
    public int compareTo(Message other) {
        return this.timestamp.compareTo(other.timestamp);
    }

    // Equality check based on message ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Debug-friendly string output
    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                ", imageUrl='" + imageUrl + '\'' +
                ", messageType='" + messageType + '\'' +
                ", status=" + status +
                '}';
    }

    // Builder pattern for creating Message objects
    public static class Builder {
        private final Message message;

        public Builder() {
            message = new Message();
        }

        public Builder setId(String id) {
            message.setId(id);
            return this;
        }

        public Builder setSenderId(String senderId) {
            message.setSenderId(senderId);
            return this;
        }

        public Builder setReceiverId(String receiverId) {
            message.setReceiverId(receiverId);
            return this;
        }

        public Builder setContent(String content) {
            message.setContent(content);
            return this;
        }

        public Builder setTimestamp(Date timestamp) {
            message.setTimestamp(timestamp);
            return this;
        }

        public Builder setRead(boolean isRead) {
            message.setRead(isRead);
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            message.setImageUrl(imageUrl);
            return this;
        }

        public Builder setMessageType(String messageType) {
            message.setMessageType(messageType);
            return this;
        }

        public Builder setStatus(MessageStatus status) {
            message.setStatus(status);
            return this;
        }

        public Message build() {
            return message;
        }
    }

    private static class Gson {
        public String toJson(Message message) {
            return "";
        }
    }

    private static class gson {
        public String toJson(Message message) {
            return "";
        }

        public Message fromJson(String json, Class<Message> messageClass) {
            return null;
        }
    }
}
