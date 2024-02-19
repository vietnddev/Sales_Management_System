package com.flowiee.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FileDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer productId;
    private Integer productVariantId;
    private Integer documentId;
    private Integer orderId;
    private Integer sort;
    private String name;
    private String storageName;
    private String originalName;
    private String extension;
    private String contentType;
    private String module;
    private String note;
    private String uploadBy;
    private String src;
    private Boolean isActive;
    private Boolean status;
    private long size;
    private byte[] content;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date uploadAt;

    public static FileDTO fromFileStorage(FileStorage fileStorage) {
        FileDTO dto = new FileDTO();
        if (ObjectUtils.isNotEmpty(fileStorage)) {
            dto.setId(fileStorage.getId());
            if (ObjectUtils.isNotEmpty(fileStorage.getProduct())) {
                dto.setProductId(fileStorage.getProduct().getId());
            }
            if (ObjectUtils.isNotEmpty(fileStorage.getProductVariant())) {
                dto.setProductVariantId(fileStorage.getProductVariant().getId());
            }
            if (ObjectUtils.isNotEmpty(fileStorage.getDocument())) {
                dto.setDocumentId(fileStorage.getDocument().getId());
            }
            if (ObjectUtils.isNotEmpty(fileStorage.getOrder())) {
                dto.setOrderId(fileStorage.getOrder().getId());
            }
            dto.setSort(fileStorage.getSort());
            dto.setName(fileStorage.getTenFileCustomize());
            dto.setStorageName(fileStorage.getTenFileKhiLuu());
            dto.setOriginalName(fileStorage.getTenFileGoc());
            dto.setExtension(fileStorage.getExtension());
            dto.setContentType(fileStorage.getContentType());
            dto.setModule(fileStorage.getModule());
            dto.setNote(fileStorage.getGhiChu());
            dto.setUploadBy(fileStorage.getAccount().getUsername());
            dto.setSrc(fileStorage.getDirectoryPath() + "/" + fileStorage.getTenFileKhiLuu());
            dto.setIsActive(fileStorage.isActive());
            dto.setStatus(fileStorage.isStatus());
            dto.setSize(fileStorage.getKichThuocFile());
            dto.setContent(fileStorage.getNoiDung());
            dto.setUploadAt(fileStorage.getCreatedAt());
        }
        return dto;
    }

    public static List<FileDTO> fromFileStorages(List<FileStorage> fileStorages) {
        List<FileDTO> list = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(fileStorages)) {
            for (FileStorage f : fileStorages) {
                list.add(FileDTO.fromFileStorage(f));
            }
        }
        return list;
    }
}