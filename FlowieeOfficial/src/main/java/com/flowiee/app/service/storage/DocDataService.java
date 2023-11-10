package com.flowiee.app.service.storage;

import java.util.List;

import com.flowiee.app.entity.storage.DocData;

public interface DocDataService {

    List<DocData> findAll();

    DocData findById(int id);

    DocData save(DocData docData);

    DocData delete(int id);
}