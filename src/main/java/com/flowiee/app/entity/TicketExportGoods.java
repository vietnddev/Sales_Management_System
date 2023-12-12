package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "stg_ticket_export_goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketExportGoods extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "title")
    private String title;

    @Column(name = "exporter", nullable = false)
    private String exporter;

    @Column(name = "export_time", nullable = false)
    private Date exportTime;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "note", length = 500)
    private String note;

	@Override
	public String toString() {
		return "TicketExportGoods [id=" + super.id + ", title=" + title + ", exporter=" + exporter + ", exportTime=" + exportTime
				+ ", orderId=" + orderId + ", note=" + note + "]";
	}
}