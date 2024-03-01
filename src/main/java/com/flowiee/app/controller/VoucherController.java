package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.model.dto.VoucherInfoDTO;
import com.flowiee.app.entity.VoucherInfo;
import com.flowiee.app.entity.VoucherTicket;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.repository.VoucherInfoRepository;
import com.flowiee.app.service.VoucherService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/voucher")
@Tag(name = "Voucher API", description = "Quản lý voucher")
public class VoucherController extends BaseController {
    @Autowired private VoucherService voucherService;
    @Autowired private VoucherInfoRepository voucherInfoRepository;

    @Operation(summary = "Find all voucher")
    @GetMapping("/all")
    public ApiResponse<List<VoucherInfoDTO>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        try {
            if (!super.vldModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            Page<VoucherInfoDTO> voucherInfos = voucherService.findAllVouchers(null, null, null, null, pageSize, pageNum - 1);
            return ApiResponse.ok(voucherInfos.getContent(), pageNum, pageSize, voucherInfos.getTotalPages(), voucherInfos.getTotalElements());
        } catch (Exception ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Find detail voucher")
    @GetMapping("/{voucherInfoId}")
    public ApiResponse<VoucherInfoDTO> findDetailVoucherInfo(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            if (!super.vldModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.findVoucherDetail(voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Create voucher")
    @PostMapping("/create")
    public ApiResponse<VoucherInfo> createVoucher(@RequestBody VoucherInfoDTO voucherInfoDTO) {
        try {
            if (!super.vldModuleProduct.insertVoucher(true)) {
                throw new BadRequestException();
            }
            if (voucherInfoDTO.getApplicableProducts().isEmpty()) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.saveVoucher(voucherInfoDTO));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Update voucher")
    @PutMapping("/update/{voucherInfoId}")
    public ApiResponse<VoucherInfo> updateVoucher(@RequestBody VoucherInfo voucherInfo, @PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            if (!super.vldModuleProduct.updateVoucher(true)) {
                throw new BadRequestException();
            }
            if (voucherInfo == null || voucherService.findVoucherDetail(voucherInfoId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.updateVoucher(voucherInfo ,voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Delete voucher")
    @PostMapping("/delete/{voucherInfoId}")
    public ApiResponse<String> deleteVoucher(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            if(!super.vldModuleProduct.deleteVoucher(true)) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.deteleVoucher(voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Check the voucher is available")
    @GetMapping("/check/{voucherCode}")
    public ApiResponse<VoucherTicket> isAvailableVoucher(@PathVariable("voucherCode") String voucherCode) {
        try {
            if(!super.vldModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.isAvailable(voucherCode));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Get tickets of voucher by voucherId")
    @GetMapping("/{voucherInfoId}/tickets")
    public ApiResponse<List<VoucherTicket>> getTicketsByVoucherInfo(@PathVariable("voucherInfoId") Integer voucherInfoId,
                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (!super.vldModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            if (pageSize != null && pageNum != null) {
                Page<VoucherTicket> voucherTickets = voucherService.findTickets(voucherInfoId, pageSize, pageNum - 1);
                return ApiResponse.ok(voucherTickets.getContent(), pageNum, pageSize, voucherTickets.getTotalPages(), voucherTickets.getTotalElements());
            }
            return ApiResponse.ok(voucherService.findTickets(voucherInfoId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher ticket"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher ticket"));
        }
    }
}