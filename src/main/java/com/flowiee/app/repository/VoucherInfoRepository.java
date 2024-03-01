package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.VoucherInfo;

@Repository
public interface VoucherInfoRepository extends JpaRepository<VoucherInfo, Integer> {
//    @Query("select " +
//           "v.id as id_0, " +
//           "v.title as title_1, " +
//           "v.description as description_2, " +
//           "v.doiTuongApDung as applicable_to_3, " +
//           "v.discount as discount_percent_4, " +
//           "v.maxPriceDiscount as discount_max_price_5, " +
//           "v.quantity as quantity_6, " +
//           "v.voucherType as code_type_7, " +
//           "v.lengthOfKey as code_length_8, " +
//           "v.startTime as start_time_9, " +
//           "v.endTime as end_time_10, " +
//           "(case when ((trunc(v.startTime) <= trunc(current_date)) and (trunc(v.endTime) >= trunc(current_date))) then 'ACTIVE' else 'INACTIVE' end) AS status_11, " +
//           "v.createdAt as created_at_12, " +
//           "v.createdBy as created_by_13 " +
//           "from VoucherInfo v " +
//           "where 1=1 " +
//           "and (:voucherIds is null or v.id in :voucherIds) " +
//           "and (:voucherId is null or v.id=:voucherId) " +
//           "and (:status is null or (:status = 'INACTIVE' or (case when ((trunc(v.startTime) <= trunc(current_date)) and (trunc(v.endTime) >= trunc(current_date))) then 'ACTIVE' else 'INACTIVE' end) = 'ACTIVE')) " +
//           "and (:status is null or (:status = 'ACTIVE' or (case when ((trunc(v.startTime) <= trunc(current_date)) and (trunc(v.endTime) >= trunc(current_date))) then 'ACTIVE' else 'INACTIVE' end) = 'INACTIVE')) " +
//           "and (:title is null or v.title like %:title%) " +
//           "and ((:startTime is null and :endTime is null) or ((v.startTime >=:startTime) or (v.endTime <=:endTime)))")
//    List<Object[]> findAll(@Param("voucherIds") List<Integer> voucherIds, @Param("voucherId") Integer voucherId,
//                           @Param("status") String status,
//                           @Param("startTime") Date startTime,
//                           @Param("endTime") Date endTime,
//                           @Param("title") String title
//    );
}