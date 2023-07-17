package com.flowiee.app.khotailieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;

@Builder
@Entity
@Table(name = "stg_doc_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "noi_dung", length = 2000)
    private String noiDung;

    @JsonIgnoreProperties("listDocData")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_field_id", nullable = false)
    private DocField docField;

    @JsonIgnoreProperties("listDocData")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @CreatedDate
    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  updatable = false)
    private Date createdAt;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        if (createdAt == null) {
            createdAt = new Date();
        }
    }

    @Override
    public String toString() {
        return "DocData{" +
            "id=" + id +
            ", noiDung='" + noiDung + '\'' +
            ", docField=" + docField +
            ", document=" + document +
            '}';
    }
}