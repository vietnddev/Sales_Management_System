package com.flowiee.app.khotailieu.service;

import com.flowiee.app.khotailieu.entity.DocData;

import java.util.List;

public interface DocDataService {

    List<DocData> findAll();

    DocData findById(int id);

    DocData save(DocData docData);

    DocData delete(int id);
}