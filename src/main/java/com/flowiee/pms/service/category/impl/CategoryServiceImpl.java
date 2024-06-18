package com.flowiee.pms.service.category.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.repository.category.CategoryHistoryRepository;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.category.CategoryHistoryService;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.product.ProductInfoService;
import com.flowiee.pms.service.product.MaterialService;
import com.flowiee.pms.utils.*;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryServiceImpl extends BaseService implements CategoryService {
    private final CategoryRepository categoryRepo;
    private final CategoryHistoryService categoryHistoryService;
    private final CategoryHistoryRepository categoryHistoryRepository;
    private final ProductInfoService productInfoService;
    private final MaterialService materialService;

    public CategoryServiceImpl(CategoryRepository categoryRepo, ProductInfoService productInfoService, MaterialService materialService,
                               CategoryHistoryService categoryHistoryService, CategoryHistoryRepository categoryHistoryRepository) {
        this.categoryRepo = categoryRepo;
        this.productInfoService = productInfoService;
        this.materialService = materialService;
        this.categoryHistoryService = categoryHistoryService;
        this.categoryHistoryRepository = categoryHistoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findById(Integer entityId) {
        return categoryRepo.findById(entityId);
    }

    @Override
    public Category save(Category entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        Category categorySaved = categoryRepo.save(entity);
        systemLogService.writeLogCreate(MODULE.CATEGORY, ACTION.CTG_I, MasterObject.Category, "Thêm mới danh mục", categorySaved.getName());
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
        categoryOptional.get().setNote(inputCategory.getName());
        if (inputCategory.getSort() != null) categoryOptional.get().setSort(inputCategory.getSort());

        Category categorySaved = categoryRepo.save(categoryOptional.get());

        String logTitle = "Cập nhật thông tin danh mục: " + categorySaved.getName();
        Map<String, Object[]> logChanges = LogUtils.logChanges(categoryBefore, categorySaved);
        categoryHistoryService.save(logChanges, logTitle, categoryId);
        systemLogService.writeLogUpdate(MODULE.CATEGORY, ACTION.CTG_U, MasterObject.Category, logTitle, logChanges);
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
        categoryRepo.deleteById(categoryId);
        systemLogService.writeLogDelete(MODULE.CATEGORY, ACTION.CTG_D, MasterObject.Category, "Xóa danh mục", categoryToDelete.get().getName());
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<Category> findRootCategory() {
        try {
            List<Category> roots = categoryRepo.findRootCategory();
            List<Object[]> recordsOfEachType = categoryRepo.totalRecordsOfEachType();
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
    public List<Category> findSubCategory(String categoryType, Integer parentId) {
        return categoryRepo.findSubCategory(categoryType, parentId, Pageable.unpaged()).getContent();
    }

    @Override
    public Page<Category> findSubCategory(String categoryType, Integer parentId, int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        return categoryRepo.findSubCategory(categoryType, parentId, pageable);
    }

    @Override
    public List<Category> findUnits() {
        return categoryRepo.findSubCategory(CategoryType.UNIT.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findColors() {
        return categoryRepo.findSubCategory(CategoryType.COLOR.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findSizes() {
        return categoryRepo.findSubCategory(CategoryType.SIZE.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findSalesChannels() {
        return categoryRepo.findSubCategory(CategoryType.SALES_CHANNEL.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findPaymentMethods() {
        return categoryRepo.findSubCategory(CategoryType.PAYMENT_METHOD.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findOrderStatus(Integer ignoreId) {
        List<Category> categories = categoryRepo.findSubCategory(CategoryType.ORDER_STATUS.name(), null, Pageable.unpaged()).getContent();
        List<Category> lsRes = new ArrayList<>();
        for (Category c : categories) {
           if (!c.getId().equals(ignoreId)) {
               lsRes.add(c);
           }
        }
        return lsRes;
    }

    @Override
    public List<Category> findLedgerGroupObjects() {
        return categoryRepo.findSubCategory(CategoryType.GROUP_OBJECT.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findLedgerReceiptTypes() {
        return categoryRepo.findSubCategory(CategoryType.RECEIPT_TYPE.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public List<Category> findLedgerPaymentTypes() {
        return categoryRepo.findSubCategory(CategoryType.PAYMENT_TYPE.name(), null, Pageable.unpaged()).getContent();
    }

    @Override
    public Boolean categoryInUse(Integer categoryId) {
        Optional<Category> category = this.findById(categoryId);
        if (category.isEmpty()) {
            throw new AppException();
        }
        switch (category.get().getType()) {
            case "UNIT":
                List<ProductDTO> productByUnits = productInfoService.findAll(-1, -1, null, null, null, null, null, categoryId, null).getContent();
                List<Material> materials = materialService.findAll(-1, -1, null, categoryId, null, null, null, null).getContent();
                if (!productByUnits.isEmpty() || !materials.isEmpty()) {
                    return true;
                }
                break;
            case "FABRIC_TYPE":
//                if (!productDetailRepo.findAll(null, null, null, null, categoryId).isEmpty()) {
//                    return true;
//                }
                break;
            case "PAYMENT_METHOD":
//                if (!orderService.findOrdersByPaymentMethodId(categoryId).isEmpty()) {
//                    return true;
//                }
                break;
            case "SALES_CHANNEL":
//                if (!orderService.findOrdersBySalesChannelId(categoryId).isEmpty()) {
//                    return true;
//                }
                break;
            case "SIZE":
//                if (!productDetailRepo.findAll(null, null, null, categoryId, null).isEmpty()) {
//                    return true;
//                }
                break;
            case "COLOR":
//                if (!productDetailRepo.findAll(null, null, categoryId, null, null).isEmpty()) {
//                    return true;
//                }
                break;
            case "PRODUCT_TYPE":
                List<ProductDTO> productByTypes = productInfoService.findAll(-1, -1, null, null, null, null, null, categoryId, null).getContent();
                if (!productByTypes.isEmpty()) {
                    return true;
                }
                break;
            case "ORDER_STATUS":
//                if (!orderService.findOrdersByStatus(categoryId).isEmpty()) {
//                    return true;
//                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category.get().getType());
        }
        return false;
    }
}