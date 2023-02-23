package com.flowiee.app.services;

import com.flowiee.app.model.category.Category;
import com.flowiee.app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getListRootCategory(){
        return categoryRepository.findByType("0");
    }

    public List<Category> getListCategory(String code){
        return categoryRepository.findByCodeAndType(code, "1");
    }

    public String getNameItem(String code, String type){
        return categoryRepository.findNameItem(code, type);
    }

    public  void insertCategory(Category category){
        categoryRepository.save(category);
    }

    public  void updateCategory(Category category){
        categoryRepository.save(category);
    }

    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }
}
