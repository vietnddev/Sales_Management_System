package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataExistsException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.BranchService;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.utils.constants.AccountStatus;
import com.flowiee.pms.utils.constants.Pages;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.model.role.ActionModel;
import com.flowiee.pms.model.role.RoleModel;
import com.flowiee.pms.service.system.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sys/tai-khoan")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountControllerView extends BaseController {
    RoleService         roleService;
    BranchService       branchService;
    AccountService      accountService;
    GroupAccountService groupAccountService;

    @GetMapping
    @PreAuthorize("@vldModuleSystem.readAccount(true)")
    public ModelAndView findAllAccount() {
        ModelAndView modelAndView = new ModelAndView(Pages.SYS_ACCOUNT.getTemplate());
        modelAndView.addObject("account", new Account());
        modelAndView.addObject("listAccount", accountService.findAll());
        modelAndView.addObject("groupAccount", groupAccountService.findAll());
        modelAndView.addObject("listBranch", branchService.findAll());
        return baseView(modelAndView);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("@vldModuleSystem.readAccount(true)")
    public ModelAndView findDetailAccountById(@PathVariable("id") Long accountId) {
        Optional<Account> account = accountService.findById(accountId);
        if (accountId <= 0 || account.isEmpty()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        ModelAndView modelAndView = new ModelAndView(Pages.SYS_ACCOUNT_DETAIL.getTemplate());
        List<RoleModel> roleOfAccount = roleService.findAllRoleByAccountId(accountId);
        modelAndView.addObject("listRole", roleOfAccount);
        modelAndView.addObject("accountInfo", account.get());
        modelAndView.addObject("groupAccount", groupAccountService.findAll());
        modelAndView.addObject("listBranch", branchService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("@vldModuleSystem.insertAccount(true)")
    public ModelAndView save(@ModelAttribute("account") Account account) {
        if (accountService.findByUsername(account.getUsername()) != null) {
            throw new DataExistsException("Username exists!");
        }
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String password = account.getPassword();
        account.setPassword(bCrypt.encode(password));
        accountService.save(account);
        return new ModelAndView("redirect:/sys/tai-khoan");
    }

    @PostMapping(value = "/update/{id}")
    @PreAuthorize("@vldModuleSystem.updateAccount(true)")
    public ModelAndView update(@ModelAttribute("account") Account accountEntity,
                               @PathVariable("id") Long accountId,
                               HttpServletRequest request) {
        if (accountId <= 0 || accountService.findById(accountId).isEmpty()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        Optional<Account> acc = accountService.findById(accountId);
        if (acc.isEmpty()) {
            throw new BadRequestException();
        }
        accountService.update(accountEntity, accountId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping(value = "/delete/{id}")
    @PreAuthorize("@vldModuleSystem.deleteAccount(true)")
    public ModelAndView deleteAccount(@PathVariable("id") Long accountId) {
        Optional<Account> account = accountService.findById(accountId);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        account.get().setStatus(AccountStatus.C.name());
        accountService.save(account.get());
        return new ModelAndView("redirect:/sys/tai-khoan");
    }

    @PostMapping("/update-permission/{id}")
    @PreAuthorize("@vldModuleSystem.updateAccount(true)")
    public ModelAndView updatePermission(@PathVariable("id") Long accountId, HttpServletRequest request) {
        if (accountId <= 0 || accountService.findById(accountId).isEmpty()) {
            throw new ResourceNotFoundException("Account not found!");
        }
        roleService.deleteAllRole(null, accountId);
        List<ActionModel> listAction = roleService.findAllAction();
        for (ActionModel sysAction : listAction) {
            String clientActionKey = request.getParameter(sysAction.getActionKey());
            if (clientActionKey != null) {
                boolean isAuthorSelected = clientActionKey.equals("on");
                if (isAuthorSelected) {
                    roleService.updatePermission(sysAction.getModuleKey(), sysAction.getActionKey(), accountId);
                }
            }
        }
        return new ModelAndView("redirect:/sys/tai-khoan/" + accountId);
    }
}