package com.flowiee.sms.service.impl;

import com.flowiee.sms.model.dto.ProductDTO;
import com.flowiee.sms.entity.*;
import com.flowiee.sms.model.dto.VoucherInfoDTO;
import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.core.exception.DataInUseException;
import com.flowiee.sms.repository.VoucherInfoRepository;
import com.flowiee.sms.repository.VoucherTicketlRepository;
import com.flowiee.sms.service.*;

import com.flowiee.sms.utils.AppConstants;
import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.utils.DateUtils;
import com.flowiee.sms.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoucherInfoServiceImpl implements VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherInfoServiceImpl.class);

    @Autowired private VoucherInfoRepository voucherInfoRepo;
    @Autowired private VoucherTicketlRepository voucherTicketRepo;
    @Autowired private VoucherApplyService voucherApplyService;
    @Autowired private ProductService productService;
    @Autowired private EntityManager entityManager;

    @Override
    public Page<VoucherInfoDTO> findAllVouchers(String status, Date startTime, Date endTime, String title, Integer pageSize, Integer pageNum) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        if (endTime == null) {
            endTime = DateUtils.convertStringToDate("2100-12-31", "YYYY-MM-dd");
        }
        if (startTime == null) {
            startTime = DateUtils.convertStringToDate("1900-01-01", "YYYY-MM-dd");
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return this.findData(null, null, status, startTime, endTime, title, pageable);
    }

    @Override
    public Page<VoucherTicket> findTickets(Integer voucherId, Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").ascending());
        return voucherTicketRepo.findByVoucherId(voucherId, pageable);
    }

    @Override
    public List<VoucherTicket> findTickets(Integer voucherId) {
        return voucherTicketRepo.findByVoucherId(voucherId, Pageable.unpaged()).getContent();
    }

    @Override
    public VoucherInfoDTO findVoucherDetail(Integer voucherId) {
        return this.findData(voucherId, null, null, null, null, null, Pageable.unpaged()).getContent().get(0);
    }

    @Override
    public List<VoucherInfoDTO> findAllVouchers(List<Integer> voucherIds, String status) {
        return this.findData(null, voucherIds, status, null, null, null, Pageable.unpaged()).getContent();
    }

    @Transactional
    @Override
    public VoucherInfo saveVoucher(VoucherInfoDTO voucherInfo) {
        try {
            if (voucherInfo == null) {
                throw new BadRequestException();
            }
            VoucherInfo voucherSaved = voucherInfoRepo.save(VoucherInfo.fromVoucherDTO(voucherInfo));
            //
            for (ProductDTO product : voucherInfo.getApplicableProducts()) {
                VoucherApply voucherApply = new VoucherApply();
                voucherApply.setVoucherId(voucherSaved.getId());
                voucherApply.setProductId(product.getId());
                voucherApplyService.save(voucherApply);
            }
            //Gen list voucher detail
            List<String> listKeyVoucher = new ArrayList<>();
            while (listKeyVoucher.size() < voucherInfo.getQuantity()) {
                String randomKey = "";
                while (randomKey.isEmpty()) {
                    String tempKey = generateRandomKeyVoucher(voucherInfo.getLength(), voucherInfo.getVoucherType());
                    if (voucherTicketRepo.findByCode(tempKey) == null) {
                        randomKey = tempKey;
                    }
                }
                if (!listKeyVoucher.contains(randomKey)) {
                    VoucherTicket voucherTicket = new VoucherTicket();
                    voucherTicket.setCode(randomKey);
                    voucherTicket.setLength(voucherInfo.getLength());
                    voucherTicket.setVoucherInfo(voucherSaved);
                    voucherTicket.setStatus(false);
                    if (this.saveTicket(voucherTicket) != null) {
                        listKeyVoucher.add(randomKey);
                    }
                }
            }
            return voucherSaved;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AppException();
        }
    }

    @Override
    public VoucherInfo updateVoucher(VoucherInfo voucherInfo, Integer voucherId) {
        try {
            if (voucherInfo == null || voucherId == null || voucherId <= 0) {
                throw new BadRequestException();
            }
            voucherInfo.setId(voucherId);
            return voucherInfoRepo.save(voucherInfo);
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "voucher"), ex);
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Override
    public VoucherTicket findTicketById(Integer voucherTicketId) {
        return voucherTicketRepo.findById(voucherTicketId).orElse(null);
    }

    @Override
    public VoucherTicket findTicketByCode(String code) {
        return voucherTicketRepo.findByCode(code);
    }

    @Transactional
    @Override
    public VoucherTicket saveTicket(VoucherTicket voucherTicket) {
        if (voucherTicket == null) {
            throw new BadRequestException();
        }
        if (this.findTicketByCode(voucherTicket.getCode()) == null) {
            return voucherTicketRepo.save(voucherTicket);
        } else {
            throw new AppException();
        }
    }

    @Transactional
    @Override
    public VoucherTicket updateTicket(VoucherTicket voucherTicket, Integer voucherDetailId) {
        if (voucherTicket == null || voucherDetailId == null || voucherDetailId <= 0) {
            throw new BadRequestException();
        }
        voucherTicket.setId(voucherDetailId);
        return voucherTicketRepo.save(voucherTicket);
    }

    @Override
    public String deteleVoucher(Integer voucherId) {
        if (voucherId <= 0 || this.findVoucherDetail(voucherId) == null) {
            return MessageUtils.DELETE_SUCCESS;
        }
        if (!voucherApplyService.findByVoucherId(voucherId).isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        voucherInfoRepo.deleteById(voucherId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public String deteleTicket(Integer ticketId) {
        VoucherTicket voucherTicket = this.findTicketById(ticketId);
        if (voucherTicket == null || voucherTicket.isStatus()) {
            throw new AppException();
        }
        voucherTicketRepo.deleteById(ticketId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public VoucherTicket isAvailable(String voucherTicketCode) {
        return voucherTicketRepo.findByCode(voucherTicketCode);
    }

    private String generateRandomKeyVoucher(int lengthOfKey, String voucherType) {
        String characters = "";
        if (AppConstants.VOUCHER_TYPE.NUMBER.name().equals(voucherType)) {
            characters = "0123456789";
        }
        if (AppConstants.VOUCHER_TYPE.TEXT.name().equals(voucherType)) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (AppConstants.VOUCHER_TYPE.BOTH.name().equals(voucherType)) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        }

        SecureRandom random = new SecureRandom();
        StringBuilder keyVoucher = new StringBuilder();
        for (int i = 0; i < lengthOfKey; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            keyVoucher.append(randomChar);
        }
        return keyVoucher.toString();
    }

//    private List<VoucherInfoDTO> extractDataQuery(List<Object[]> objects) {
//        List<VoucherInfoDTO> listVoucherInfoDTO = new ArrayList<>();
//        for (Object[] data : objects) {
//            VoucherInfoDTO dto = new VoucherInfoDTO();
//            dto.setId(Integer.parseInt(String.valueOf(data[0])));
//            dto.setTitle(String.valueOf(data[1]));
//            dto.setDescription(String.valueOf(data[2]));
//            dto.setDoiTuongApDung(String.valueOf(data[3]));
//            dto.setDiscount(Integer.parseInt(String.valueOf(data[4])));
//            dto.setMaxPriceDiscount(Float.parseFloat(String.valueOf(data[5])));
//            dto.setQuantity(Integer.parseInt(String.valueOf(data[6])));
//            dto.setVoucherType(String.valueOf(data[7]));
//            dto.setLengthOfKey(Integer.parseInt(String.valueOf(data[8])));
//            dto.setStartTime(String.valueOf(data[9]).substring(0, 10));
//            dto.setEndTime(String.valueOf(data[10]).substring(0, 10));
//            if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(String.valueOf(data[11]))) {
//                dto.setStatus(AppConstants.VOUCHER_STATUS.ACTIVE.getLabel());
//            } else if (AppConstants.VOUCHER_STATUS.INACTIVE.name().equals(String.valueOf(data[11]))) {
//                dto.setStatus(AppConstants.VOUCHER_STATUS.INACTIVE.getLabel());
//            }
//            dto.setCreatedAt(DateUtils.convertStringToDate(String.valueOf(data[12]), "yyyy-MM-dd"));
//            dto.setCreatedBy(Integer.parseInt(String.valueOf(data[13])));
//            dto.setListVoucherTicket(null);
//
//            List<VoucherApply> listVoucherApply = voucherApplyService.findByVoucherId(dto.getId());
//            List<Product> listSanPhamApDung = new ArrayList<>();
//            for (VoucherApply vSanPham : listVoucherApply) {
//                Product productApplied = productService.findProductById(vSanPham.getSanPhamId());
//                if (productApplied != null) {
//                    listSanPhamApDung.add(productApplied);
//                }
//            }
//            dto.setListSanPhamApDung(listSanPhamApDung);
//
//            listVoucherInfoDTO.add(dto);
//        }
//        return listVoucherInfoDTO;
//    }

    private Page<VoucherInfoDTO> findData(Integer voucherId, List<Integer> voucherIds, String status, Date startTime, Date endTime, String title, Pageable pageable) {
        try {
            List<VoucherInfoDTO> listVoucherInfoDTO = new ArrayList<>();
            String strSQL = "SELECT v.ID as ID_0, " +
                            "v.TITLE as TITLE_1, " +
                            "v.DESCRIPTION as DESCRIPTION_2, " +
                            "v.APPLICABLE_OBJECTS as APPLICABLE_TO_3, " +
                            "v.DISCOUNT as DISCOUNT_PERCENT_4, " +
                            "v.DISCOUNT_PRICE_MAX as DISCOUNT_MAX_PRICE_5, " +
                            "v.QUANTITY as QUANTITY_6, " +
                            "v.TYPE as CODE_TYPE_7, " +
                            "v.START_TIME as START_TIME_8, " +
                            "v.END_TIME as END_TIME_9, " +
                            "v.STATUS AS STATUS_10, " +
                            "v.CREATED_AT as CREATED_AT_11, " +
                            "v.CREATED_BY as CREATED_BY_12 " +
                            "FROM PRO_VOUCHER_INFO_VIEW v " +
                            "WHERE 1=1 ";
            if (voucherId != null) {
                strSQL += "AND v.ID = " + voucherId + " ";
            }
            if (voucherIds != null) {
                strSQL += "AND v.ID IN (" + CommonUtils.convertListIntToStr(voucherIds) + ") ";
            }
            if (AppConstants.VOUCHER_STATUS.A.name().equals(status)) {
                strSQL += "AND v.STATUS = '" + AppConstants.VOUCHER_STATUS.A.name() + "' ";
            } else if (AppConstants.VOUCHER_STATUS.I.name().equals(status)) {
                strSQL += "AND v.STATUS = '" + AppConstants.VOUCHER_STATUS.I.name() + "' ";
            }
            if (title != null) {
                strSQL += "AND v.TITLE LIKE '%" + title + "%' ";
            }
            //strSQL += "AND (TRUNC(START_TIME) >= TO_DATE('" + startTime + "', 'YYYY-MM-dd') OR TRUNC(END_TIME) <= TO_DATE('" + startTime + "', 'YYYY-MM-dd')) ";
            logger.info("[SQL findData]: " + strSQL);
            Query query;
            if (pageable.isUnpaged()) {
                query = entityManager.createNativeQuery(strSQL);
            } else {
                query = entityManager.createNativeQuery(strSQL)
                                     .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                                     .setMaxResults(pageable.getPageSize());
            }
            List<Object[]> rawData = query.getResultList();
            for (Object[] data : rawData) {
                VoucherInfoDTO dto = new VoucherInfoDTO();
                dto.setId(Integer.parseInt(String.valueOf(data[0])));
                dto.setTitle(String.valueOf(data[1]));
                dto.setDescription(String.valueOf(data[2]));
                dto.setApplicableObjects(String.valueOf(data[3]));
                dto.setDiscount(Integer.parseInt(String.valueOf(data[4])));
                dto.setDiscountPriceMax(new BigDecimal(String.valueOf(data[5])));
                dto.setQuantity(Integer.parseInt(String.valueOf(data[6])));
                dto.setVoucherType(String.valueOf(data[7]));
                dto.setStartTime(DateUtils.convertStringToDate(data[8].toString(), "yyyy-MM-dd HH:mm:ss.S"));
                dto.setEndTime(DateUtils.convertStringToDate(data[9].toString(), "yyyy-MM-dd HH:mm:ss.S"));
                if (AppConstants.VOUCHER_STATUS.A.name().equals(String.valueOf(data[10]))) {
                    dto.setStatus(AppConstants.VOUCHER_STATUS.A.getLabel());
                } else if (AppConstants.VOUCHER_STATUS.I.name().equals(String.valueOf(data[10]))) {
                    dto.setStatus(AppConstants.VOUCHER_STATUS.I.getLabel());
                }
                dto.setCreatedAt(DateUtils.convertStringToDate(String.valueOf(data[11]), "yyyy-MM-dd"));
                dto.setCreatedBy(Integer.parseInt(String.valueOf(data[12])));
                dto.setListVoucherTicket(null);

                List<VoucherApply> listVoucherApply = voucherApplyService.findByVoucherId(dto.getId());
                List<Product> listSanPhamApDung = new ArrayList<>();
                for (VoucherApply vSanPham : listVoucherApply) {
                    Product productApplied = productService.findProductById(vSanPham.getProductId());
                    if (productApplied != null) {
                        listSanPhamApDung.add(productApplied);
                    }
                }
                dto.setApplicableProducts(ProductDTO.fromProducts(listSanPhamApDung));

                listVoucherInfoDTO.add(dto);
            }

            // Đếm tổng số lượng bản ghi
            String countQueryString = "SELECT COUNT(*) FROM PRO_VOUCHER_INFO_VIEW";
            long totalCount = Long.parseLong(entityManager.createNativeQuery(countQueryString).getSingleResult().toString());
            return new PageImpl<>(listVoucherInfoDTO, pageable, totalCount);
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Override
    public String checkTicketToUse(String code) {
        String statusTicket = "";
        VoucherTicket ticket = voucherTicketRepo.findByCode(code);
        if (ticket != null) {
            VoucherInfoDTO voucherInfo = this.findVoucherDetail(ticket.getId());
            if (AppConstants.VOUCHER_STATUS.A.name().equals(voucherInfo.getStatus())) {
                statusTicket = AppConstants.VOUCHER_STATUS.A.getLabel();
            } else if (AppConstants.VOUCHER_STATUS.I.name().equals(voucherInfo.getStatus())) {
                statusTicket = AppConstants.VOUCHER_STATUS.I.getLabel();
            }
        } else {
            statusTicket = "Invalid!";
        }
        return statusTicket;
    }
}