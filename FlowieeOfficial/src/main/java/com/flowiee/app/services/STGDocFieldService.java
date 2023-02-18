package com.flowiee.app.services;

import com.flowiee.app.model.storage.STGDocField;
import com.flowiee.app.repositories.STGDocFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class STGDocFieldService {
    @Autowired
    STGDocFieldRepository stgDocFieldRepository;

    public List<STGDocField> getByIDDocType(int idDocType){
        return stgDocFieldRepository.findByidDocType(idDocType);
    }
}
