package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.LedgerPayment;
import com.flowiee.pms.entity.sales.LedgerReceipt;
import com.flowiee.pms.repository.sales.LedgerPaymentRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.LedgerPaymentService;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.LedgerPaymentStatus;
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
public class LedgerPaymentServiceImpl extends BaseService implements LedgerPaymentService {
    @Autowired
    private LedgerPaymentRepository paymentRepository;
    
    @Override
    public List<LedgerPayment> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<LedgerPayment> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<LedgerPayment> ledgerPayments = paymentRepository.findAll(pageable);
        for (LedgerPayment ledgerPayment : ledgerPayments) {
            for (LedgerPaymentStatus paymentStatus : LedgerPaymentStatus.values()) {
                if (paymentStatus.name().equals(ledgerPayment.getStatus())) {
                    ledgerPayment.setStatus(paymentStatus.getDescription());
                }
            }
            if (ledgerPayment.getPayerGroup() != null) {
                ledgerPayment.setPayerGroupName(ledgerPayment.getPayerGroup().getName());
            }
            if (ledgerPayment.getPaymentType() != null) {
                ledgerPayment.setPaymentTypeName(ledgerPayment.getPaymentType().getName());
            }
        }
        return ledgerPayments;
    }
    
    @Override
    public Optional<LedgerPayment> findById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public LedgerPayment save(LedgerPayment ledgerPayment) {
        Integer lastIndex = paymentRepository.findLastIndex();
        if (ObjectUtils.isEmpty(lastIndex)) {
            lastIndex = 1;
        }
        lastIndex = lastIndex + 1;
        if (ObjectUtils.isEmpty(ledgerPayment.getPaymentCode())) {
            ledgerPayment.setPaymentCode(getNextPaymentCode(lastIndex));
        }
        ledgerPayment.setPaymentIndex(lastIndex);
        ledgerPayment.setStatus(LedgerReceiptStatus.COMPLETED.name());
        return paymentRepository.save(ledgerPayment);
    }

    @Override
    public LedgerPayment update(LedgerPayment ledgerPayment, Integer paymentId) {
        ledgerPayment.setId(paymentId);
        return paymentRepository.save(ledgerPayment);
    }

    @Override
    public String delete(Integer paymentId) {
        paymentRepository.deleteById(paymentId);
        return MessageUtils.DELETE_SUCCESS;
    }

    private String getNextPaymentCode(int lastIndex) {
        return "PC" + String.format("%05d", lastIndex);
    }
}