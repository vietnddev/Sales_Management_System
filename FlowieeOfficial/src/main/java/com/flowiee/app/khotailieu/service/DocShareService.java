package com.flowiee.app.khotailieu.service;

import com.flowiee.app.khotailieu.entity.DocShare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocShareService {

    List<DocShare> findAll();

    DocShare findById(int id);

    boolean isShared(int documentId);
}