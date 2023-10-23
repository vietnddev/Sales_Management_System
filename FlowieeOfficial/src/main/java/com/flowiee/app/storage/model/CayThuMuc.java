package com.flowiee.app.storage.model;

import lombok.Data;

import java.util.List;

@Data
public class CayThuMuc {
    private int id;
    private String tenThuMuc;
    private List<CayThuMuc> listSubThuMuc;
}