package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.author.ValidateModuleStorage;
import com.flowiee.app.service.TicketExportGoodsService;
import com.flowiee.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/storage")
public class TicketExportGoodsController extends BaseController {
    @Autowired
    private TicketExportGoodsService ticketExportGoodsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ValidateModuleStorage validateModuleStorage;

    @GetMapping("/ticket-export")
    public ModelAndView showDashboardOfSTG() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleStorage.exportGoods()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.STG_TICKET_EXPORT);
        return baseView(modelAndView);
    }
}