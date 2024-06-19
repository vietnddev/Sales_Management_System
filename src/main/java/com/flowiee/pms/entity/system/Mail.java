package com.flowiee.pms.entity.system;

import com.flowiee.pms.entity.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "mail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mail extends BaseEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @Column(name = "send_from", nullable = false)
    String from;

    @Column(name = "send_to", nullable = false)
    String to;

    @Column(name = "cc")
    String cc;

    @Column(name = "bcc")
    String bcc;

    @Column(name = "subject", nullable = false)
    String subject;

    @Column(name = "content", nullable = false)
    String content;
}