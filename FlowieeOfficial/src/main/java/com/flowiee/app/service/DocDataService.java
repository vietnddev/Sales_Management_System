package com.flowiee.app.service;

import java.util.List;

import com.flowiee.app.entity.DocData;

public interface DocDataService {

    List<DocData> findAll();

    DocData findById(int id);

    DocData save(DocData docData);

    DocData delete(int id);
}