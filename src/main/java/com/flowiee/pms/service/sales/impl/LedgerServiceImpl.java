package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.LedgerTransaction;
import com.flowiee.pms.model.GeneralLedger;
import com.flowiee.pms.repository.sales.LedgerTransactionRepository;
import com.flowiee.pms.service.sales.LedgerService;
import com.flowiee.pms.service.sales.LedgerTransactionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class LedgerServiceImpl implements LedgerService {
    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final LedgerTransactionService    ledgerTransactionService;

    public LedgerServiceImpl(LedgerTransactionRepository ledgerTransactionRepository, @Qualifier("ledgerTransactionServiceImpl") LedgerTransactionService ledgerTransactionService) {
        this.ledgerTransactionRepository = ledgerTransactionRepository;
        this.ledgerTransactionService = ledgerTransactionService;
    }

    @Override
    public GeneralLedger findGeneralLedger(int pageSize, int pageNum, LocalDate pFromDate, LocalDate pToDate) {
        LocalDate currentMonth = LocalDate.now();
        if (ObjectUtils.isEmpty(pFromDate)) {
            pFromDate = currentMonth.withDayOfMonth(1);
        }
        if (ObjectUtils.isEmpty(pToDate)) {
            pToDate = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth());
        }
        LocalDateTime fromDate = LocalDateTime.of(pFromDate, LocalTime.of(0, 0, 0, 0));
        LocalDateTime toDate = LocalDateTime.of(pToDate, LocalTime.of(23, 59, 59, 999999999));
        Page<LedgerTransaction> ledgerTransactions = ledgerTransactionService.findAll(pageSize, pageNum, LocalDate.now(), LocalDate.now());

        LocalDateTime fromDateBeginBal = fromDate.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        BigDecimal beginBal = ledgerTransactionRepository.calBeginBalance(fromDateBeginBal);
        BigDecimal[] totalReceiptPayment = ledgerTransactionRepository.calTotalReceiptAndTotalPayment(fromDate, toDate).get(0);
        BigDecimal totalReceipt = totalReceiptPayment[0];
        BigDecimal totalPayment = totalReceiptPayment[1];
        BigDecimal endBal = beginBal.add(totalReceipt).subtract(totalPayment);

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setBeginBalance(beginBal);
        generalLedger.setTotalReceipt(totalReceipt);
        generalLedger.setTotalPayment(totalPayment);
        generalLedger.setEndBalance(endBal);
        generalLedger.setListTransactions(ledgerTransactions.getContent());
        generalLedger.setTotalPages(ledgerTransactions.getTotalPages());
        generalLedger.setTotalElements(ledgerTransactions.getTotalElements());

        return generalLedger;
    }
}