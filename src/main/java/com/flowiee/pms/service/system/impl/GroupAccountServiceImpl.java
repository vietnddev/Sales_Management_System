package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.system.GroupAccountRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.utils.constants.MessageCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupAccountServiceImpl extends BaseService implements GroupAccountService {
    private final GroupAccountRepository groupAccountRepository;

    public GroupAccountServiceImpl(GroupAccountRepository groupAccountRepository) {
        this.groupAccountRepository = groupAccountRepository;
    }

    @Override
    public List<GroupAccount> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<GroupAccount> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("groupName").ascending());
        }
        return groupAccountRepository.findAll(pageable);
    }

    @Override
    public Optional<GroupAccount> findById(Integer groupId) {
        if (groupId == null || groupId <= 0) {
            return Optional.empty();
        }
        return groupAccountRepository.findById(groupId);
    }

    @Override
    public GroupAccount save(GroupAccount groupAccount) {
        return groupAccountRepository.save(groupAccount);
    }

    @Override
    public GroupAccount update(GroupAccount groupAccount, Integer groupId) {
        if (this.findById(groupId).isEmpty()) {
            throw new BadRequestException();
        }
        groupAccount.setId(groupId);
        return groupAccountRepository.save(groupAccount);
    }

    @Override
    public String delete(Integer groupId) {
        if (this.findById(groupId).isEmpty()) {
            throw new BadRequestException();
        }
        groupAccountRepository.deleteById(groupId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}