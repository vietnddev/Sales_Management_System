package com.flowiee.app.hethong.entity;

import com.flowiee.app.file.entity.FileStorage;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "thong_bao_he_thong")
public class Notification {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "send", nullable = false)
    private int send;

    @Column(name = "receive", nullable = false)
    private int receive;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "readed", nullable = false)
    private boolean readed;

    @OneToOne
    @JoinColumn(name = "import_info")
    private ImportInfo importInfo;
}