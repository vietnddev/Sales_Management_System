package com.flowiee.app.hethong.entity;

import com.flowiee.app.common.entity.BaseEntity;
import com.flowiee.app.file.entity.FileStorage;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "FLOWIEE_IMPORT")
public class FlowieeImport extends BaseEntity {
    @Column(name = "module", nullable = false)
    private String module;

    @Column(name = "entity", nullable = false)
    private String entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private Account account;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "detail", nullable = false, length = 999)
    private String detail;

    @OneToOne
    @JoinColumn(name = "stg_file")
    private FileStorage stgFile;
}