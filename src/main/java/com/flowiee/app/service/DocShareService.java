package com.flowiee.app.service;

import com.flowiee.app.entity.DocShare;

import java.util.List;

public interface DocShareService {

    List<DocShare> findAll();

    DocShare findById(int id);

    boolean isShared(int documentId);
}