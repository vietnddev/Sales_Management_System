package com.flowiee.app.services;

import com.flowiee.app.model.storage.DocShare2;
import com.flowiee.app.repositories.DocShare2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocShare2Service {
    @Autowired
    DocShare2Repository docShare2Repository;

    public List<DocShare2> getAllDocShare2(){
        return docShare2Repository.findAll();
    }
}
