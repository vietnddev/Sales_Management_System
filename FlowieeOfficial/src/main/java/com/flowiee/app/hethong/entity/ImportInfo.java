package com.flowiee.app.hethong.entity;

import com.flowiee.app.file.entity.FileStorage;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "import")
public class ImportInfo {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "module", nullable = false)
    private String module;

    @Column(name = "entity", nullable = false)
    private String entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private Account account;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "completed", nullable = false)
    private Date completed;

    @Column(name = "result", nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "stg_file")
    private FileStorage stgFile;
}