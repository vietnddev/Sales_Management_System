package com.flowiee.app.services;

import com.flowiee.app.model.storage.STGDocType;
import com.flowiee.app.repositories.STGDocTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class STGDocTypeService {
    @Autowired
    private  STGDocTypeRepository stgDocTypeRepository;

    public List<STGDocType> getAllSTGDocType(){

        return stgDocTypeRepository.findAll();
    }
}
