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
    CategoryRepository        categoryRepository;
    CategoryHistoryService    categoryHistoryService;
    CategoryHistoryRepository categoryHistoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Integer entityId) {
        return categoryRepository.findById(entityId);
    }

    @Override
    public Category save(Category entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        Category categorySaved = categoryRepository.save(entity);
        systemLogService.writeLogCreate(MODULE.CATEGORY, ACTION.CTG_I, MasterObject.Category, "Thêm mới danh mục " + categorySaved.getType(), categorySaved.getName());
        return categorySaved;
    }

    @Transactional
    @Override
    public Category update(Category inputCategory, Integer categoryId) {
        Optional<Category> categoryOptional = this.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new BadRequestException();
        }
        Category categoryBefore = ObjectUtils.clone(categoryOptional.get());

        categoryOptional.get().setName(inputCategory.getName());
        categoryOptional.get().setNote(inputCategory.getNote());
        if (inputCategory.getSort() != null) categoryOptional.get().setSort(inputCategory.getSort());

        Category categorySaved = categoryRepository.save(categoryOptional.get());

        String logTitle = "Cập nhật thông tin danh mục " + categorySaved.getType() + ": " + categorySaved.getName();
        ChangeLog changeLog = new ChangeLog(categoryBefore, categorySaved);
        categoryHistoryService.save(changeLog.getLogChanges(), logTitle, categoryId);
        systemLogService.writeLogUpdate(MODULE.CATEGORY, ACTION.CTG_U, MasterObject.Category, logTitle, changeLog.getOldValues(), changeLog.getNewValues());
        logger.info("Update Category success! {}", categorySaved);

        return categorySaved;
    }

    @Transactional
    @Override
    public String delete(Integer categoryId) {
        Optional<Category> categoryToDelete = this.findById(categoryId);
        if (categoryToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Category not found!");
        }
        if (categoryInUse(categoryId)) {
            throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        categoryHistoryRepository.deleteAllByCategory(categoryId);
        categoryRepository.deleteById(categoryId);
        systemLogService.writeLogDelete(MODULE.CATEGORY, ACTION.CTG_D, MasterObject.Category, "Xóa danh mục " + categoryToDelete.get().getType(), categoryToDelete.get().getName());
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<Category> findRootCategory() {
        try {
            List<Category> roots = categoryRepository.findRootCategory();
            List<Object[]> recordsOfEachType = categoryRepository.totalRecordsOfEachType();
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
    public Page<Category> findSubCategory(CategoryType categoryType, Integer parentId, List<Integer> ignoreIds, int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<Category> categoryPage = categoryRepository.findSubCategory(categoryType.name(), parentId, ignoreIds, pageable);
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
    public List<Category> findOrderStatus(Integer ignoreId) {
        return findSubCategory(CategoryType.ORDER_STATUS, null, List.of(ignoreId), -1, -1).getContent();
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
    public boolean categoryInUse(Integer categoryId) {
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
                if (ObjectUtils.isNotEmpty(category.get().getListTrangThaiDonHang())) {
                    return true;
                }
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
                if (ObjectUtils.isNotEmpty(category.get().getListTrangThaiDonHang())) {
                    return true;
                }
                break;
            default:
                //throw new IllegalStateException("Unexpected value: " + category.get().getType());
                logger.info("Unexpected value: " + category.get().getType());
        }
        return false;
    }
}