package com.flowiee.app.hethong.entity;

import com.flowiee.app.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {
    @Column(name = "send", nullable = false)
    private Integer send;

    @Column(name = "receive", nullable = false)
    private Integer receive;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "readed", nullable = false)
    private Boolean readed;

    @Column(name = "import_id")
    private Integer importId;
}