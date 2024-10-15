package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.product.MaterialRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.MaterialHistoryService;
import com.flowiee.pms.service.product.MaterialService;

import com.flowiee.pms.utils.constants.MasterObject;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MaterialServiceImpl extends BaseService implements MaterialService {
    MaterialRepository     mvMaterialRepository;
    MaterialHistoryService mvMaterialHistoryService;

    @Override
    public List<Material> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<Material> findAll(int pageSize, int pageNum, Integer supplierId, Integer unitId, String code, String name, String location, String status) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("name").ascending());
        }
        return mvMaterialRepository.findAll(supplierId, unitId, code, name, location, status, pageable);
    }

    @Override
    public Optional<Material> findById(Integer entityId) {
        return mvMaterialRepository.findById(entityId);
    }

    @Override
    public Material save(Material entity) {
        Material materialSaved = mvMaterialRepository.save(entity);
        systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.STG_MAT_C, MasterObject.Material, "Thêm mới nguyên vật liệu", materialSaved.getName());
        return materialSaved;
    }

    @Override
    public Material update(Material entity, Integer materialId) {
        if (entity == null || materialId == null || materialId <= 0) {
            throw new BadRequestException();
        }
        Optional<Material> materialOptional = this.findById(materialId);
        if (materialOptional.isEmpty()) {
            throw new BadRequestException();
        }
        Material materialBefore = ObjectUtils.clone(materialOptional.get());
        entity.setId(materialId);
        Material materialUpdated = mvMaterialRepository.save(entity);

        String logTitle = "Cập nhật nguyên vật liệu: " + materialUpdated.getName();
        ChangeLog changeLog = new ChangeLog(materialBefore, materialUpdated);
        mvMaterialHistoryService.save(changeLog.getLogChanges(), logTitle, materialId);
        systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.STG_MAT_U, MasterObject.Material, logTitle, changeLog);
        logger.info(logTitle);

        return materialUpdated;
    }

    @Override
    public String delete(Integer entityId) {
        Optional<Material> materialToDelete = this.findById(entityId);
        if (materialToDelete.isEmpty()) {
            throw new BadRequestException("Material not found!");
        }
        mvMaterialRepository.deleteById(entityId);

        String logTitle = "Xóa nguyên vật liệu";
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.STG_MAT_U, MasterObject.Material, "Xóa nguyên vật liệu", materialToDelete.get().getName());
        logger.info("{}: {}", logTitle, materialToDelete.get().getName());

        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Transactional
    @Override
    public void updateQuantity(Integer quantity, Integer materialId, String type) {
        String logTitle = "Cập nhật số lượng nguyên vật liệu";
        if ("I".equals(type)) {
            mvMaterialRepository.updateQuantityIncrease(quantity, materialId);
            systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.STG_MAT_U, MasterObject.Material, logTitle, " + " + quantity);
        } else if ("D".equals(type)) {
            mvMaterialRepository.updateQuantityDecrease(quantity, materialId);
            systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.STG_MAT_U, MasterObject.Material, logTitle, " - " + quantity);
        }
    }
}