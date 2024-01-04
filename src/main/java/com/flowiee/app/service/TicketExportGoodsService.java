package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.TicketExportGoods;

import java.util.List;

public interface TicketExportGoodsService extends BaseService<TicketExportGoods> {
    List<TicketExportGoods> findAll();
}