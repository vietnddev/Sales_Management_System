package com.flowiee.app.storage.service;

import com.flowiee.app.storage.entity.DocShare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocShareService {

    List<DocShare> findAll();

    DocShare findById(int id);

    boolean isShared(int documentId);
}