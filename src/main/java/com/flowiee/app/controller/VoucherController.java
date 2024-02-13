package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.entity.VoucherInfo;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.VoucherService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/voucher")
@Tag(name = "Voucher API", description = "Quản lý voucher")
public class VoucherController extends BaseController {
    @Autowired private VoucherService voucherService;

    @Operation(summary = "Find all voucher")
    @GetMapping("/all")
    public ApiResponse<List<VoucherInfoDTO>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        try {
            if (!super.validateModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            Page<VoucherInfoDTO> voucherInfos = voucherService.findAll(null, null, null, null, pageSize, pageNum - 1);
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
            if (!super.validateModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.findById(voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Create voucher")
    @PostMapping("/create")
    public ApiResponse<VoucherInfoDTO> createVoucher(@RequestBody VoucherInfo voucherInfo) throws ParseException {
        try {
            if (!super.validateModuleProduct.insertVoucher(true)) {
                throw new BadRequestException();
            }
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //voucherInfo.setStartTime(dateFormat.parse(request.getParameter("startTime_")));
            //voucherInfo.setEndTime(dateFormat.parse(request.getParameter("endTime_")));

            //List<Integer> listProductToApply = new ArrayList<>();
            //String[] pbienTheSP = request.getParameterValues("productToApply");
            //if (pbienTheSP != null) {
            //    for (String id : pbienTheSP) {
            //        listProductToApply.add(Integer.parseInt(id));
            //    }
            //}
            //if (!listProductToApply.isEmpty()) {
            //    voucherService.save(voucherInfo, listProductToApply);
            //}
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Update voucher")
    @PutMapping("/update/{voucherInfoId}")
    public ApiResponse<VoucherInfoDTO> updateVoucher(@RequestBody VoucherInfo voucherInfo, @PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            if (!super.validateModuleProduct.updateVoucher(true)) {
                throw new BadRequestException();
            }
            if (voucherInfo == null || voucherService.findById(voucherInfoId) == null) {
                throw new BadRequestException();
            }
            voucherService.update(voucherInfo ,voucherInfoId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Delete voucher")
    @PostMapping("/delete/{voucherInfoId}")
    public ApiResponse<String> deleteVoucher(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            if(!super.validateModuleProduct.deleteVoucher(true)) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.detele(voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "voucher"));
        }
    }

    @Operation(summary = "Check the voucher is available")
    @GetMapping("/check/{voucherCode}")
    public ApiResponse<VoucherInfoDTO> isAvailableVoucher(@PathVariable("voucherCode") String voucherCode) {
        try {
            if(!super.validateModuleProduct.readVoucher(true)) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(voucherService.isAvailable(voucherCode));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"));
        }
    }
}