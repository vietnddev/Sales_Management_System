package com.flowiee.app.services;

import com.flowiee.app.model.storage.Gallery;
import com.flowiee.app.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    private final ResourceLoader resourceLoader;

    public GalleryService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Gallery> getAllGallery(){
        return galleryRepository.findAll();
    }

    public List<Gallery> getSubImage(int productID){
        return galleryRepository.findByProductID(productID);
    }

    public void insertGallery(Gallery gallery) {
    	galleryRepository.save(gallery);
    }
}
