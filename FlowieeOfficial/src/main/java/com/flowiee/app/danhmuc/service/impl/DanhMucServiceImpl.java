package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.danhmuc.entity.DanhMuc;
import com.flowiee.app.danhmuc.repository.DanhMucRepository;
import com.flowiee.app.danhmuc.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucRepository categoryRepository;

    @Override
    public List<DanhMuc> getListRootCategory() {
        return categoryRepository.findByType("0");
    }

    @Override
    public List<DanhMuc> getListCategory(String code) {
        return categoryRepository.findByCodeAndType(code, "1");
    }

    @Override
    public String getNameItem(String code, String type) {
        return categoryRepository.findNameItem(code, type);
    }

    @Override
    public  void insertCategory(DanhMuc category) {
        categoryRepository.save(category);
    }

    @Override
    public  void update(DanhMuc category, int id) {
        category.setId(id);
        categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}