package com.flowiee.app.service;

import org.springframework.stereotype.Service;

import com.flowiee.app.entity.DocShare;

import java.util.List;

@Service
public interface DocShareService {

    List<DocShare> findAll();

    DocShare findById(int id);

    boolean isShared(int documentId);
}