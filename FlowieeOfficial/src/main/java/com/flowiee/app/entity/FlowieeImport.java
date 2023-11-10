package com.flowiee.app.entity;

import com.flowiee.app.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "entity_import")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlowieeImport extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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

    @Column(name = "sucess_record")
    private Integer successRecord;

    @Column(name = "total_record")
    private Integer totalRecord;

    @Column(name = "file_id")
    private Integer fileId;
}