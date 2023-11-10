package com.flowiee.app.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_contact")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerContact extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "value", length = 500, nullable = false)
    private String value;

    @Column(name = "note")
    private String note;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "status", nullable = false)
    private boolean status;
}