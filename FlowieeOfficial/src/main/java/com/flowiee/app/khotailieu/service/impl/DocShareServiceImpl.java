package com.flowiee.app.khotailieu.service.impl;

import com.flowiee.app.common.utils.TaiKhoanUtil;
import com.flowiee.app.khotailieu.entity.DocShare;
import com.flowiee.app.khotailieu.repository.DocShareRepository;
import com.flowiee.app.khotailieu.service.DocShareService;
import com.flowiee.app.account.service.AccountService;
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
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (docShareRepository.findByDocmentAndTaiKhoan(documentId, accountId) != null) {
            return true;
        }
        return false;
    }
}