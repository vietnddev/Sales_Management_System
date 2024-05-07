package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.VoucherInfoDTO;
import com.flowiee.pms.entity.sales.VoucherInfo;
import com.flowiee.pms.entity.sales.VoucherTicket;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.VoucherTicketDTO;
import com.flowiee.pms.service.sales.VoucherService;
import com.flowiee.pms.service.sales.VoucherTicketService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/voucher")
@Tag(name = "Voucher API", description = "Quản lý voucher, voucher will be deducted from the value of the order")
public class VoucherController extends BaseController {
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherTicketService voucherTicketService;

    @Operation(summary = "Find all voucher")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public AppResponse<List<VoucherInfoDTO>> findAll(@RequestParam("pageSize") int pageSize,
                                                     @RequestParam("pageNum") int pageNum,
                                                     @RequestParam(value = "title", required = false) String pTitle,
                                                     @RequestParam(value = "startTime", required = false)
                                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime pStartTime,
                                                     @RequestParam(value = "endTime", required = false)
                                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime pEndTime,
                                                     @RequestParam(value = "status", required = false) String pStatus) {
        try {
            Page<VoucherInfoDTO> voucherInfos = voucherService.findAll(pageSize, pageNum - 1, null, pTitle, pStartTime, pEndTime, pStatus);
            return success(voucherInfos.getContent(), pageNum, pageSize, voucherInfos.getTotalPages(), voucherInfos.getTotalElements());
        } catch (Exception ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Operation(summary = "Find detail voucher")
    @GetMapping("/{voucherInfoId}")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public AppResponse<VoucherInfoDTO> findDetailVoucherInfo(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            Optional<VoucherInfoDTO> voucherInfoDTO = voucherService.findById(voucherInfoId);
            if (voucherInfoDTO.isPresent()) {
                return success(voucherInfoDTO.get());
            }
            throw new BadRequestException();
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Operation(summary = "Create voucher")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleSales.insertVoucher(true)")
    public AppResponse<VoucherInfo> createVoucher(@RequestBody VoucherInfoDTO voucherInfoDTO) {
        try {
            if (voucherInfoDTO.getApplicableProducts().isEmpty()) {
                throw new BadRequestException();
            }
            return success(voucherService.save(voucherInfoDTO));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Operation(summary = "Update voucher")
    @PutMapping("/update/{voucherInfoId}")
    @PreAuthorize("@vldModuleSales.updateVoucher(true)")
    public AppResponse<VoucherInfoDTO> updateVoucher(@RequestBody VoucherInfoDTO voucherInfo, @PathVariable("voucherInfoId") Integer voucherInfoId) {
        try {
            if (voucherInfo == null || voucherService.findById(voucherInfoId).isEmpty()) {
                throw new BadRequestException();
            }
            return success(voucherService.update(voucherInfo ,voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Operation(summary = "Delete voucher")
    @DeleteMapping("/delete/{voucherInfoId}")
    @PreAuthorize("@vldModuleSales.deleteVoucher(true)")
    public AppResponse<String> deleteVoucher(@PathVariable("voucherInfoId") Integer voucherInfoId) {
        return success(voucherService.delete(voucherInfoId));
    }

    @Operation(summary = "Check the voucher is available")
    @GetMapping("/check/{voucherCode}")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public AppResponse<VoucherTicketDTO> isAvailableVoucher(@PathVariable("voucherCode") String voucherCode) {
        try {
            VoucherTicket voucherTicket = voucherTicketService.isAvailable(voucherCode);
            if (voucherTicket == null) {
                VoucherTicketDTO dto = new VoucherTicketDTO();
                dto.setAvailable("N");
                return success(dto);
            }
            return success(voucherTicketService.isAvailable(voucherCode));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher"), ex);
        }
    }

    @Operation(summary = "Get tickets of voucher by voucherId")
    @GetMapping("/{voucherInfoId}/tickets")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public AppResponse<List<VoucherTicket>> getTicketsByVoucherInfo(@PathVariable("voucherInfoId") Integer voucherInfoId,
                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (pageSize != null && pageNum != null) {
                Page<VoucherTicket> voucherTickets = voucherTicketService.findAll(pageSize, pageNum - 1, voucherInfoId);
                return success(voucherTickets.getContent(), pageNum, pageSize, voucherTickets.getTotalPages(), voucherTickets.getTotalElements());
            }
            return success(voucherTicketService.findByVoucherId(voucherInfoId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "voucher ticket"), ex);
        }
    }
}