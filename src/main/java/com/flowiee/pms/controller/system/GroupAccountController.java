package com.flowiee.pms.controller.system;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/sys/group-account")
@Tag(name = "Group account API", description = "Quản lý nhóm người dùng")
public class GroupAccountController extends BaseController {
    @Autowired
    private GroupAccountService groupAccountService;

    @Operation(summary = "Find all group account")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSystem.readGroupAccount(true)")
    public AppResponse<List<GroupAccount>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        try {
            Page<GroupAccount> groupAccounts = groupAccountService.findAll(pageSize, pageNum - 1);
            return success(groupAccounts.getContent(), pageNum, pageSize, groupAccounts.getTotalPages(), groupAccounts.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "group account"), ex);
        }
    }

    @Operation(summary = "Find detail group")
    @GetMapping("/{groupId}")
    @PreAuthorize("@vldModuleSystem.readGroupAccount(true)")
    public AppResponse<GroupAccount> findDetailAccount(@PathVariable("groupId") Integer groupId) {
        try {
            Optional<GroupAccount> groupAcc = groupAccountService.findById(groupId);
            if (groupAcc.isEmpty()) {
                throw new BadRequestException("Group not found");
            }
            return success(groupAcc.get());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "group account"), ex);
        }
    }

    @Operation(summary = "Create group account")
    @PostMapping(value = "/create")
    @PreAuthorize("@vldModuleSystem.insertGroupAccount(true)")
    public AppResponse<GroupAccount> save(@RequestBody GroupAccount groupAccount) {
        try {
            if (groupAccount == null) {
                throw new BadRequestException("Invalid group account");
            }
            return success(groupAccountService.save(groupAccount));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "group account"), ex);
        }
    }

    @Operation(summary = "Update group account")
    @PutMapping(value = "/update/{groupId}")
    @PreAuthorize("@vldModuleSystem.updateGroupAccount(true)")
    public AppResponse<GroupAccount> update(@RequestBody GroupAccount groupAccount, @PathVariable("groupId") Integer groupId) {
        try {
            if (groupAccountService.findById(groupId).isEmpty()) {
                throw new BadRequestException("Group not found");
            }
            return success(groupAccountService.update(groupAccount, groupId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "group account"), ex);
        }
    }

    @Operation(summary = "Delete group account")
    @DeleteMapping(value = "/delete/{groupId}")
    @PreAuthorize("@vldModuleSystem.deleteGroupAccount(true)")
    public AppResponse<String> delete(@PathVariable("groupId") Integer groupId) {
        return success(groupAccountService.delete(groupId));
    }
}