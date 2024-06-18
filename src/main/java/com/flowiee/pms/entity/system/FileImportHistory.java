package com.flowiee.pms.entity.system;

import com.flowiee.pms.entity.BaseEntity;

import com.flowiee.pms.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "file_import_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileImportHistory extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

    @Column(name = "title", nullable = false, length = 999)
    private String title;

	@Column(name = "module")
    private String module;

    @Column(name = "entity")
    private String entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private Account account;

    @Column(name = "begin_time", nullable = false)
    private LocalTime beginTime;

    @Column(name = "finish_time", nullable = false)
    private LocalTime finishTime;

    @Column(name = "total_record")
    private Integer totalRecord;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "file_path", nullable = false)
    private String filePath;

	@Override
	public String toString() {
		return "FileImportHistory [id=" + super.id + ", module=" + module + ", entity=" + entity + ", account=" + account.getUsername()
                + ", beginTime=" + beginTime + ", finishTime=" + finishTime + ", totalRecord=" + totalRecord + ", result=" + result + "]";
	}
}