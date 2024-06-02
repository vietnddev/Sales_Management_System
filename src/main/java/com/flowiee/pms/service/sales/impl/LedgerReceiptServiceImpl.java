package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.LedgerReceipt;
import com.flowiee.pms.repository.sales.LedgerReceiptRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.LedgerReceiptService;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.LedgerReceiptStatus;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LedgerReceiptServiceImpl extends BaseService implements LedgerReceiptService {
    @Autowired
    private LedgerReceiptRepository receiptRepository;

    @Override
    public List<LedgerReceipt> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<LedgerReceipt> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<LedgerReceipt> ledgerReceipts = receiptRepository.findAll(pageable);
        for (LedgerReceipt ledgerReceipt : ledgerReceipts) {
            for (LedgerReceiptStatus receiptStatus : LedgerReceiptStatus.values()) {
                if (receiptStatus.name().equals(ledgerReceipt.getStatus())) {
                    ledgerReceipt.setStatus(receiptStatus.getDescription());
                }
            }
            if (ledgerReceipt.getReceiverGroup() != null) {
                ledgerReceipt.setReceiverGroupName(ledgerReceipt.getReceiverGroup().getName());
            }
            if (ledgerReceipt.getReceiptType() != null) {
                ledgerReceipt.setReceiptTypeName(ledgerReceipt.getReceiptType().getName());
            }
        }
        return ledgerReceipts;
    }

    @Override
    public Optional<LedgerReceipt> findById(Integer receiptId) {
        return receiptRepository.findById(receiptId);
    }

    @Override
    public LedgerReceipt save(LedgerReceipt ledgerReceipt) {
        Integer lastIndex = receiptRepository.findLastIndex();
        if (ObjectUtils.isEmpty(lastIndex)) {
            lastIndex = 1;
        }
        lastIndex = lastIndex + 1;
        if (ObjectUtils.isEmpty(ledgerReceipt.getReceiptCode())) {
            ledgerReceipt.setReceiptCode(getNextReceiptCode(lastIndex));
        }
        ledgerReceipt.setReceiptIndex(lastIndex);
        ledgerReceipt.setStatus(LedgerReceiptStatus.COMPLETED.name());
        return receiptRepository.save(ledgerReceipt);
    }

    @Override
    public LedgerReceipt update(LedgerReceipt ledgerReceipt, Integer receiptId) {
        ledgerReceipt.setId(receiptId);
        return receiptRepository.save(ledgerReceipt);
    }

    @Override
    public String delete(Integer receiptId) {
        receiptRepository.deleteById(receiptId);
        return MessageUtils.DELETE_SUCCESS;
    }

    private String getNextReceiptCode(int lastIndex) {
        return "PT" + String.format("%05d", lastIndex);
    }
}