package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vw_general_ledger_transaction")
public class LedgerTransactionView {
    @Id
    @Column(name = "tran_id")
    private Integer tranId;

    @Column(name = "tran_code")
    private String tranCode;

    @Column(name = "tran_type")
    private String tranType;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private BigDecimal value;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "tran_time")
    private LocalDateTime tranTime;
}