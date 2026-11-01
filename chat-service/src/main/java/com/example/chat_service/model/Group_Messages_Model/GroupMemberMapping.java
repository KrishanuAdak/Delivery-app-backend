package com.example.chat_service.model.Group_Messages_Model;

import java.time.LocalDateTime;

public class GroupMemberMapping {
    private Long id;
    private Long groupId;
    private Long memberId;
    private LocalDateTime addedDateTime;
    private LocalDateTime removedDateTime;
    private Long addedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(LocalDateTime addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public LocalDateTime getRemovedDateTime() {
        return removedDateTime;
    }

    public void setRemovedDateTime(LocalDateTime removedDateTime) {
        this.removedDateTime = removedDateTime;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

}
