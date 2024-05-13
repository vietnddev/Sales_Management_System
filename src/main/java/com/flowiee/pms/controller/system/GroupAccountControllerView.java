package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/sys/group-account")
public class GroupAccountControllerView extends BaseController {
    @Autowired
    private GroupAccountService groupAccountService;

    @GetMapping
    @PreAuthorize("@vldModuleSystem.readGroupAccount(true)")
    public ModelAndView findAllGroup() {
        return baseView(new ModelAndView(PagesUtils.SYS_GR_ACC));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("@vldModuleSystem.readGroupAccount(true)")
    public ModelAndView findDetailGroup(@PathVariable("id") Integer groupId) {
        Optional<GroupAccount> groupAcc = groupAccountService.findById(groupId);
        if (groupAcc.isEmpty()) {
            throw new NotFoundException("Group account not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_GR_ACC_DETAIL);
        modelAndView.addObject("groupAccount", groupAcc.get());
        return baseView(modelAndView);
    }
}