package com.flowiee.app.services;

import com.flowiee.app.model.Image;
import com.flowiee.app.model.Product_Variants;
import com.flowiee.app.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AccountService accountService;

    public List<Image> getAllImage(){
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()){
            return imageRepository.getAllImage();
        }
        return null;
    }

    public List<Image> getFilesByProductVariant(int productVariantID){
        return imageRepository.getImageByPVariantID(productVariantID);
    }

    public void insertFiles(Image filess) {
        imageRepository.save(filess);
    }

    public void deleteFiles(int fileID){
        imageRepository.deleteById(fileID);
    }
}
 