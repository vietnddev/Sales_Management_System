package com.flowiee.app.service.impl;

import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.entity.DocShare;
import com.flowiee.app.repository.DocShareRepository;
import com.flowiee.app.service.DocShareService;
import com.flowiee.app.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocShareServiceImpl implements DocShareService {
    @Autowired
    private DocShareRepository docShareRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public List<DocShare> findAll() {
        return docShareRepository.findAll();
    }

    @Override
    public DocShare findById(int id) {
        return docShareRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isShared(int documentId) {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (docShareRepository.findByDocmentAndTaiKhoan(documentId, accountId) != null) {
            return true;
        }
        return false;
    }
}