package com.example.chat_service.model.Group_Messages_Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity 
@Table(name="group_info")
public class GroupInfo {
    @Id 
    private Long id;
    @NotEmpty
    @Size(min=2, message="Group name should be at least 2 characters long")
    private String groupName;
    @NotNull 
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAtDateTime;
    private LocalDateTime updatedAtDateTime;
    private String groupDiscussion;
    private Long[] adminIds;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }
    public long getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public LocalDateTime getCreatedAtDateTime() {
        return createdAtDateTime;
    }
    public void setCreatedAtDateTime(LocalDateTime createdAtDateTime) {
        this.createdAtDateTime = createdAtDateTime;
    }
    public LocalDateTime getUpdatedAtDateTime() {
        return updatedAtDateTime;
    }
    public void setUpdatedAtDateTime(LocalDateTime updatedAtDateTime) {
        this.updatedAtDateTime = updatedAtDateTime;
    }
    public String getGroupDiscussion() {
        return groupDiscussion;
    }
    public void setGroupDiscussion(String groupDiscussion) {
        this.groupDiscussion = groupDiscussion;
    }
    public Long[] getAdminIds() {
        return adminIds;
    }
    public void setAdminIds(Long[] adminIds) {
        this.adminIds = adminIds;
    }
    public GroupInfo(Long id, String groupName, Long createdBy, Long updatedBy, LocalDateTime createdAtDateTime,
            LocalDateTime updatedAtDateTime, String groupDiscussion, Long[] adminIds) {
        this.id = id;
        this.groupName = groupName;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAtDateTime = createdAtDateTime;
        this.updatedAtDateTime = updatedAtDateTime;
        this.groupDiscussion = groupDiscussion;
        this.adminIds = adminIds;
    }
    public GroupInfo() {
    }
    



}
