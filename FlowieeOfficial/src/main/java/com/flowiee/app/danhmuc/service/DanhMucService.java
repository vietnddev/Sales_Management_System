package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.DanhMuc;
import com.flowiee.app.danhmuc.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucService {
    @Autowired
    private DanhMucRepository categoryRepository;

    public List<DanhMuc> getListRootCategory(){
        return categoryRepository.findByType("0");
    }

    public List<DanhMuc> getListCategory(String code){
        return categoryRepository.findByCodeAndType(code, "1");
    }

    public String getNameItem(String code, String type){
        return categoryRepository.findNameItem(code, type);
    }

    public  void insertCategory(DanhMuc category){
        categoryRepository.save(category);
    }

    public  void update(DanhMuc category, int id){
        category.setId(id);
        categoryRepository.save(category);
    }

    public void delete(int id){
        categoryRepository.deleteById(id);
    }
}
