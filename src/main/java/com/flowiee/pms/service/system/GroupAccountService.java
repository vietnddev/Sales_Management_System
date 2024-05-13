package com.flowiee.pms.service.system;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.system.GroupAccount;
import org.springframework.data.domain.Page;

public interface GroupAccountService extends CrudService<GroupAccount> {
    Page<GroupAccount> findAll(int pageSize, int pageNum);
}