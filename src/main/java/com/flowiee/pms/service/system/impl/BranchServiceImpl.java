package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.Branch;
import com.flowiee.pms.repository.system.BranchRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.BranchService;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BranchServiceImpl extends BaseService implements BranchService {
    BranchRepository mvBranchRepository;

    @Override
    public List<Branch> findAll() {
        return mvBranchRepository.findAll();
    }

    @Override
    public Optional<Branch> findById(Integer branchId) {
        return mvBranchRepository.findById(branchId);
    }

    @Override
    public Branch save(Branch branch) {
        return mvBranchRepository.save(branch);
    }

    @Override
    public Branch update(Branch branch, Integer branchId) {
        branch.setId(branchId);
        return mvBranchRepository.save(branch);
    }

    @Override
    public String delete(Integer branchId) {
        mvBranchRepository.deleteById(branchId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}