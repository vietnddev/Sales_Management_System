package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.utils.constants.Pages;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/sys/group-account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GroupAccountControllerView extends BaseController {
    GroupAccountService groupAccountService;

    @GetMapping
    @PreAuthorize("@vldModuleSystem.readGroupAccount(true)")
    public ModelAndView findAllGroup() {
        return baseView(new ModelAndView(Pages.SYS_GR_ACC.getTemplate()));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("@vldModuleSystem.readGroupAccount(true)")
    public ModelAndView findDetailGroup(@PathVariable("id") Long groupId) {
        Optional<GroupAccount> groupAcc = groupAccountService.findById(groupId);
        if (groupAcc.isEmpty()) {
            throw new ResourceNotFoundException("Group account not found!");
        }
        ModelAndView modelAndView = new ModelAndView(Pages.SYS_GR_ACC_DETAIL.getTemplate());
        modelAndView.addObject("groupAccount", groupAcc.get());
        return baseView(modelAndView);
    }
}