package com.flowiee.app.services;

import com.flowiee.app.model.storage.Filess;
import com.flowiee.app.repositories.FilessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilessService {
    @Autowired
    private FilessRepository filesRepository;    

    public List<Filess> getAllFiles(){
        return filesRepository.getAllFiless(); 
    }

//    public List<Filess> getFilesByProductVariant(int productVariantID){
//        return filesRepository.findByproductVariantID(productVariantID);
//    }

    public void insertFiles(Filess filess) { 
    	filesRepository.save(filess);
    }

    public void deleteFiles(int fileID){ 
        filesRepository.deleteById(fileID);
    }
}
 