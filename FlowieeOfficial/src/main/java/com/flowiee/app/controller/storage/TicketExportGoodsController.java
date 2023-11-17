package com.flowiee.app.controller.storage;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.author.ValidateModuleStorage;
import com.flowiee.app.service.storage.TicketExportGoodsService;
import com.flowiee.app.service.system.AccountService;
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
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (!validateModuleStorage.exportGoods()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_TICKET_EXPORT);
        return baseView(modelAndView);
    }
}