package com.flowiee.app.service.storage;

import org.springframework.stereotype.Service;

import com.flowiee.app.entity.storage.DocShare;

import java.util.List;

@Service
public interface DocShareService {

    List<DocShare> findAll();

    DocShare findById(int id);

    boolean isShared(int documentId);
}