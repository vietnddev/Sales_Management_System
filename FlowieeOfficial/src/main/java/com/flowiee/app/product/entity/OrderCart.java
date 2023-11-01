package com.flowiee.app.product.entity;

import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "don_hang_cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCart extends BaseEntity implements Serializable {
    @OneToMany(mappedBy = "orderCart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Items> listItems;

    @Override
    public String toString() {
        return "Cart{" +
                "listItems=" +
                '}';
    }
}