package com.flowiee.app.services;

import com.flowiee.app.model.storage.Storage;
import com.flowiee.app.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    public List<Storage> getRootDoc(int IDParent, int IDUer){
        return storageRepository.getRootDoc(IDParent, IDUer);
    }
}
