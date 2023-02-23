package com.flowiee.app.services;

import com.flowiee.app.model.storage.Gallery;
import com.flowiee.app.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    public List<Gallery> getAllGallery(){
        return galleryRepository.findAll();
    }
}
