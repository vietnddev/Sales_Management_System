package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.TicketImport;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.request.TicketImportGoodsRequest;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.repository.TicketImportRepository;
import com.flowiee.app.service.TicketImportService;

import com.flowiee.app.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketImportServiceImpl implements TicketImportService {
    private static final Logger logger = LoggerFactory.getLogger(TicketImportServiceImpl.class);

    @Autowired
    private TicketImportRepository ticketImportRepository;

    private static String STATUS_DRAFT = "DRAFT";
//    private static String STATUS_APPROVING = "APPROVING";
//    private static String STATUS_APPROVED = "APPROVED";
//    private static String STATUS_REJECT = "REJECT";

    @Override
    public List<TicketImport> findAll() {
        return ticketImportRepository.findAll();
    }

    @Override
    public Page<TicketImport> findAll(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        return ticketImportRepository.findAll(pageable);
    }

    @Override
    public List<TicketImport> findAll(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus) {
        return ticketImportRepository.findAll(text, supplierId, paymentMethod, payStatus, importStatus);
    }

    @Override
    public TicketImport findById(Integer entityId) {
        return ticketImportRepository.findById(entityId).orElse(null);
    }

    @Override
    public TicketImport save(TicketImport entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        return ticketImportRepository.save(entity);
    }

    @Override
    public TicketImport update(TicketImport entity, Integer entityId) {
        if (entity == null || this.findById(entityId) == null) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return ticketImportRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        try {
            ticketImportRepository.deleteById(entityId);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "ticket import"), ex);
            throw new ApiException();
        }
    }
;

    @Override
    public TicketImport saveDraft(TicketImportGoodsRequest request) {
        if (request == null || request.getId() == null || request.getId() <= 0) {
            throw new BadRequestException();
        }
        TicketImport ticketImport = this.findById(request.getId());
        if (ticketImport == null) {
            throw new BadRequestException();
        }
        ticketImport.setId(request.getId());
        ticketImport.setTitle(request.getTitle());
        if (request.getSupplierId() != null && request.getSupplierId() > 0) {
            ticketImport.setSupplier(new Supplier(request.getSupplierId(), null));
        }
        if (request.getPaymentMethodId() != null && request.getPaymentMethodId() > 0) {
            ticketImport.setPaymentMethod(new Category(request.getPaymentMethodId(), null));
        }
        if (request.getReceivedBy() != null && request.getReceivedBy() > 0) {
            ticketImport.setReceivedBy(new Account(request.getReceivedBy()));
        }
        ticketImport.setDiscount(request.getDiscount());
        ticketImport.setPaidAmount(request.getPaidAmount());
        ticketImport.setPaidStatus(request.getPaidStatus());
//        if (request.getOrderTime() != null) {
//            ticketImport.setOrderTime(request.getOrderTime());
//        }
        if (request.getReceivedTime() != null) {
            ticketImport.setReceivedTime(request.getReceivedTime());
        }
        ticketImport.setNote(request.getNote());
        ticketImport.setStatus(STATUS_DRAFT);
        return ticketImportRepository.save(ticketImport);
    }

//    @Override
//    public List<TicketImportGoods> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus) {
//        List<TicketImportGoods> listData = new ArrayList<>();
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT i.ID, i.TITLE, s.NAME as SUPPLIER_NAME, pm.TEN_LOAI as PAYMENT_METHOD_NAME, i.PAY_STATUS, a.FULLNAME as RECEIVED_NAME, i.STATUS, ");
//        sql.append("       s.ID as SUPPLIER_ID, pm.ID as PAYMENT_METHOD_ID, a.ID as RECEIVED_ID, ");
//        sql.append("       i.ORDER_TIME, i.RECEIVED_TIME ");
//        sql.append("FROM goods_import i ");
//        sql.append("LEFT JOIN supplier s ON s.ID = i.SUPPLIER_ID ");
//        sql.append("LEFT JOIN dm_hinh_thuc_thanh_toan pm ON pm.ID = i.PAYMENT_METHOD ");
//        sql.append("LEFT JOIN account a ON a.ID = i.RECEIVED_BY ");
//        sql.append("WHERE i.TITLE LIKE '%").append(text != null ? text : "").append("%' ");
//        if (supplierId != null) {
//            sql.append("AND s.ID = ? ");
//        }
//        if (paymentMethod != null) {
//            sql.append("AND pm.ID = ? ");
//        }
//        if (payStatus != null) {
//            sql.append("i.PAY_STATUS = ? ");
//        }
//        if (importStatus != null) {
//            sql.append("i.STATUS = ?");
//        }
//        System.out.println(sql.toString());
//        Query query = entityManager.createNativeQuery(sql.toString());
//        int i = 1;
//        if (supplierId != null) {
//            query.setParameter(i++, supplierId);
//        }
//        if (paymentMethod != null) {
//            query.setParameter(i++, paymentMethod);
//        }
//        if (payStatus != null) {
//            query.setParameter(i++, payStatus);
//        }
//        if (importStatus != null) {
//            query.setParameter(i++, importStatus);
//        }
//        @SuppressWarnings("unchecked")
//		List<Object[]> data = query.getResultList();
//        for (Object[] o : data) {
//            TicketImportGoods ticketImportGoods = new TicketImportGoods();
//            ticketImportGoods.setId(Integer.parseInt(o[0].toString()));
//            ticketImportGoods.setTitle(String.valueOf(o[1]));
//            ticketImportGoods.setSupplier(new Supplier(Integer.parseInt(String.valueOf(o[7])), String.valueOf(o[2])));
//            ticketImportGoods.setPaymentMethod(new Category(Integer.parseInt(String.valueOf(o[8])), String.valueOf(o[3])));
//            ticketImportGoods.setPaidStatus(String.valueOf(o[4]));
//            ticketImportGoods.setReceivedBy(new Account(Integer.parseInt(String.valueOf(o[9])), null, String.valueOf(o[5])));
//            ticketImportGoods.setStatus(String.valueOf(o[6]));
//            ticketImportGoods.setOrderTime(CommonUtil.convertStringToDate(String.valueOf(o[10])));
//            ticketImportGoods.setReceivedTime(CommonUtil.convertStringToDate(String.valueOf(o[11])));
//            listData.add(ticketImportGoods);
//        }
//        entityManager.close();
//        return listData;
//    }

    @Override
    public List<TicketImport> findByMaterialId(Integer materialId) {
        List<TicketImport> listData = new ArrayList<>();
        if (materialId != null && materialId >= 0) {
            //listData = goodsImportRepository.findByMaterialId(materialId);
        }
        return listData;
    }

    @Override
    public List<TicketImport> findBySupplierId(Integer supplierId) {
        List<TicketImport> listData = new ArrayList<>();
        if (supplierId != null && supplierId >= 0) {
            listData = ticketImportRepository.findBySupplierId(supplierId);
        }
        return listData;
    }

    @Override
    public List<TicketImport> findByPaymentMethod(String paymentMethod) {
        List<TicketImport> listData = new ArrayList<>();
        if (paymentMethod != null) {
            listData = ticketImportRepository.findByPaymentMethod(paymentMethod);
        }
        return listData;
    }

    @Override
    public List<TicketImport> findByPaidStatus(String paidStatus) {
        List<TicketImport> listData = new ArrayList<>();
        if (paidStatus != null && CommonUtils.getPaymentStatusCategory().containsKey(paidStatus)) {
            listData = ticketImportRepository.findByPaidStatus(paidStatus);
        }
        return listData;
    }

    @Override
    public List<TicketImport> findByAccountId(Integer accountId) {
        List<TicketImport> listData = new ArrayList<>();
        if (accountId != null && accountId > 0) {
            listData = ticketImportRepository.findByReceiveBy(accountId);
        }
        return listData;
    }

    @Override
    public TicketImport findDraftImportPresent(Integer createdBy) {
        return ticketImportRepository.findDraftGoodsImportPresent(STATUS_DRAFT, createdBy);
    }

    @Override
    public TicketImport createDraftTicketImport(String title) {
        TicketImport ticketImport = new TicketImport();
        ticketImport.setTitle(title);
        ticketImport.setStatus(STATUS_DRAFT);
        ticketImport.setCreatedBy(CommonUtils.getCurrentAccountId());
        ticketImport.setImporter(CommonUtils.getCurrentAccountUsername());
        ticketImport.setImportTime(new Date());
        ticketImport.setReceivedTime(new Date());
        ticketImport = ticketImportRepository.save(ticketImport);
        return ticketImport;
    }

    @Override
    public TicketImport updateStatus(Integer entityId, String status) {
        if (entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        TicketImport ticketImport = this.findById(entityId);
        if (ticketImport == null) {
            throw new BadRequestException();
        }
        ticketImport.setStatus(status);
        return ticketImportRepository.save(ticketImport);
    }
}