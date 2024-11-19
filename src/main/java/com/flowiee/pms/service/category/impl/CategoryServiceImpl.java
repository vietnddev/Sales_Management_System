package com.flowiee.pms.service.category.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.repository.category.CategoryHistoryRepository;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.category.CategoryHistoryService;
import com.flowiee.pms.service.category.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServiceImpl extends BaseService implements CategoryService {
    CategoryRepository mvCategoryRepository;
    CategoryHistoryService mvCategoryHistoryService;
    CategoryHistoryRepository mvCategoryHistoryRepository;

    @Override
    public List<Category> findAll() {
        return mvCategoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long entityId) {
        return mvCategoryRepository.findById(entityId);
    }

    @Override
    public Category save(Category entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        Category categorySaved = mvCategoryRepository.save(entity);
        systemLogService.writeLogCreate(MODULE.CATEGORY, ACTION.CTG_I, MasterObject.Category, "Thêm mới danh mục " + categorySaved.getType(), categorySaved.getName());
        return categorySaved;
    }

    @Transactional
    @Override
    public Category update(Category pCategory, Long categoryId) {
        Optional<Category> categoryOpt = this.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new BadRequestException();
        }
        Category categoryBefore = ObjectUtils.clone(categoryOpt.get());

        categoryOpt.get().setName(pCategory.getName());
        categoryOpt.get().setNote(pCategory.getNote());
        if (pCategory.getSort() != null) categoryOpt.get().setSort(pCategory.getSort());

        Category categorySaved = mvCategoryRepository.save(categoryOpt.get());

        String logTitle = "Cập nhật thông tin danh mục " + categorySaved.getType() + ": " + categorySaved.getName();
        ChangeLog changeLog = new ChangeLog(categoryBefore, categorySaved);
        mvCategoryHistoryService.save(changeLog.getLogChanges(), logTitle, categoryId);
        systemLogService.writeLogUpdate(MODULE.CATEGORY, ACTION.CTG_U, MasterObject.Category, logTitle, changeLog.getOldValues(), changeLog.getNewValues());
        logger.info("Update Category success! {}", categorySaved);

        return categorySaved;
    }

    @Transactional
    @Override
    public String delete(Long categoryId) {
        Optional<Category> categoryToDelete = this.findById(categoryId);
        if (categoryToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Category not found!");
        }
        if (categoryInUse(categoryId)) {
            throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        mvCategoryHistoryRepository.deleteAllByCategory(categoryId);
        mvCategoryRepository.deleteById(categoryId);
        systemLogService.writeLogDelete(MODULE.CATEGORY, ACTION.CTG_D, MasterObject.Category, "Xóa danh mục " + categoryToDelete.get().getType(), categoryToDelete.get().getName());
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<Category> findRootCategory() {
        try {
            List<Category> roots = mvCategoryRepository.findRootCategory();
            List<Object[]> recordsOfEachType = mvCategoryRepository.totalRecordsOfEachType();
            for (Category c : roots) {
                for (Object[] o : recordsOfEachType) {
                    if (c.getType().equals(o[0])) {
                        c.setTotalSubRecords(Integer.parseInt(String.valueOf(o[1])));
                        break;
                    }
                }
            }
            return roots;
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "category"), ex);
        }
    }

    @Override
    public Page<Category> findSubCategory(CategoryType categoryType, Long parentId, List<Long> ignoreIds, int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<Category> categoryPage = mvCategoryRepository.findSubCategory(categoryType.name(), parentId, ignoreIds, pageable);
        for (Category c : categoryPage.getContent()) {
            String statusName = CategoryStatus.I.getLabel();
            if (c.getStatus()) {
                statusName = CategoryStatus.A.getLabel();
            }
            boolean categoryInUse = categoryInUse(c.getId());
            c.setInUse(categoryInUse ? "Đang được sử dụng" : "Chưa sử dụng");
            c.setStatusName(statusName);
        }
        return new PageImpl<>(categoryPage.getContent(), pageable, categoryPage.getTotalElements());
    }

    @Override
    public List<Category> findUnits() {
        return findSubCategory(CategoryType.UNIT, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findColors() {
        return findSubCategory(CategoryType.COLOR, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findSizes() {
        return findSubCategory(CategoryType.SIZE, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findSalesChannels() {
        return findSubCategory(CategoryType.SALES_CHANNEL, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findPaymentMethods() {
        return findSubCategory(CategoryType.PAYMENT_METHOD, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findOrderStatus(Long ignoreId) {
        return findSubCategory(CategoryType.ORDER_STATUS, null, ignoreId != null ? List.of(ignoreId) : null, -1, -1).getContent();
    }

    @Override
    public List<Category> findLedgerGroupObjects() {
        return findSubCategory(CategoryType.GROUP_OBJECT, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findLedgerReceiptTypes() {
        return findSubCategory(CategoryType.RECEIPT_TYPE, null, null, -1, -1).getContent();
    }

    @Override
    public List<Category> findLedgerPaymentTypes() {
        return findSubCategory(CategoryType.PAYMENT_TYPE, null, null, -1, -1).getContent();
    }

    @Override
    public boolean categoryInUse(Long categoryId) {
        Optional<Category> category = this.findById(categoryId);
        if (category.isEmpty()) {
            throw new BadRequestException("Category not found!");
        }
        switch (category.get().getType()) {
            case "UNIT":
                if (ObjectUtils.isNotEmpty(category.get().getListUnit())) {
                    return true;
                }
                if (ObjectUtils.isNotEmpty(category.get().getListProductByUnit())) {
                    return true;
                }
                break;
            case "FABRIC_TYPE":
                if (ObjectUtils.isNotEmpty(category.get().getListFabricType()))
                    return true;
                break;
            case "PAYMENT_METHOD":
//                if (ObjectUtils.isNotEmpty(category.get().getListTrangThaiDonHang())) {
//                    return true;
//                }
                if (ObjectUtils.isNotEmpty(category.get().getListPaymentMethod())) {
                    return true;
                }
                break;
            case "SALES_CHANNEL":
                if (ObjectUtils.isNotEmpty(category.get().getListKenhBanHang())) {
                    return true;
                }
                break;
            case "SIZE":
                if (ObjectUtils.isNotEmpty(category.get().getListLoaiKichCo())) {
                    return true;
                }
                break;
            case "COLOR":
                if (ObjectUtils.isNotEmpty(category.get().getListLoaiMauSac())) {
                    return true;
                }
                break;
            case "PRODUCT_TYPE":
                if (ObjectUtils.isNotEmpty(category.get().getListProductByProductType())) {
                    return true;
                }
                break;
            case "ORDER_STATUS":
//                if (ObjectUtils.isNotEmpty(category.get().getListTrangThaiDonHang())) {
//                    return true;
//                }
                break;
            default:
                //throw new IllegalStateException("Unexpected value: " + category.get().getType());
                logger.info("Unexpected value: " + category.get().getType());
        }
        return false;
    }
}