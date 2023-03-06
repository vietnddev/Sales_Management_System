package com.flowiee.app.services;

import com.flowiee.app.model.storage.Gallery;
import com.flowiee.app.repositories.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesService {
    @Autowired
    private FilesRepository filesRepository;

    private final ResourceLoader resourceLoader;

    public FilesService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Gallery> getAllGallery(){
        return filesRepository.findAll();
    }

    public List<Gallery> getSubImage(int productVariantID){
        return filesRepository.findByProductVariantID(productVariantID);
    }

    public void insertGallery(Gallery gallery) {
    	filesRepository.save(gallery);
    }
}
