package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "stg_doc_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocData extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

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

    @OneToMany(mappedBy = "docData", fetch = FetchType.LAZY)
    private List<DocHistory> listDocHistory;

    public Map<String, String> compareTo(DocData entityToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.getNoiDung().equals(entityToCompare.getNoiDung())) {
            map.put("DocData - " + this.getDocField().getTenField(), this.getNoiDung() + "#" + entityToCompare.getNoiDung());
        }
        return map;
    }

	@Override
	public String toString() {
		return "DocData [id=" + super.id + ", noiDung=" + noiDung + ", docField=" + docField + ", document=" + document + "]";
	}        
}