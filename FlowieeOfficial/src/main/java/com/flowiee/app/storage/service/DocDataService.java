package com.flowiee.app.storage.service;

import com.flowiee.app.storage.entity.DocData;

import java.util.List;

public interface DocDataService {

    List<DocData> findAll();

    DocData findById(int id);

    DocData save(DocData docData);

    DocData delete(int id);
}