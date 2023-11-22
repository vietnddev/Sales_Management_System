package com.flowiee.app.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer id;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
    @CreatedDate
    private Date createdAt;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    protected Integer createdBy;

    @Column(name = "last_updated_at", columnDefinition = "timestamp default current_timestamp")
    @LastModifiedDate
    private Date lastUpdatedAt;

    @Column(name = "last_updated_by")
    @LastModifiedBy
    private String lastUpdatedBy;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdatedAt = new Date();
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (createdBy == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            createdBy = Integer.parseInt(authentication.getName().substring(authentication.getName().indexOf("_") + 1));
        }
    }
}