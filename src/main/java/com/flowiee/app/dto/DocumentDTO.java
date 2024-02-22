package com.flowiee.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowiee.app.entity.Document;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DocumentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String aliasName;
    private String description;
    private Integer parentId;
    private Integer docTypeId;
    private String docTypeName;
    private String isFolder;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createdAt;
    private FileDTO file;
    @JsonIgnore
    private MultipartFile fileUpload;
    //private List<DocField> fields;

    public static DocumentDTO fromDocument(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setAliasName(document.getAsName());
        dto.setDescription(document.getDescription());
        dto.setParentId(document.getParentId());
        if (document.getLoaiTaiLieu() != null) {
            dto.setDocTypeId(document.getLoaiTaiLieu().getId());
            dto.setDocTypeName(document.getLoaiTaiLieu().getName());
        }
        dto.setIsFolder(document.getIsFolder());
        dto.setCreatedAt(document.getCreatedAt());
        return dto;
    }

    public static List<DocumentDTO> fromDocuments(List<Document> documents) {
        List<DocumentDTO> list = new ArrayList<>();
        if (documents != null) {
            for (Document d : documents) {
                list.add(DocumentDTO.fromDocument(d));
            }
        }
        return list;
    }
}