//package com.flowiee.app.common.utils;
//
//import com.flowiee.app.khotailieu.entity.Document;
//import com.flowiee.app.khotailieu.model.CayThuMuc;
//import com.flowiee.app.khotailieu.service.DocumentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class StorageUtil {
//    @Autowired
//    private DocumentService documentService;
//
//    public List<CayThuMuc> buildTree() {
//        List<Document> documents = documentService.findAllFolder();
//
//        // Tạo một map để lưu các cấu trúc cây thư mục dưới dạng key-value
//        Map<Integer, CayThuMuc> map = new HashMap<>();
//
//        // Tạo danh sách gốc cho cây thư mục
//        List<CayThuMuc> roots = new ArrayList<>();
//
//        // Đầu tiên, hãy tạo tất cả các nút (CayThuMuc) từ danh sách tài liệu (documents)
//        for (Document document : documents) {
//            CayThuMuc cayThuMuc = new CayThuMuc();
//            cayThuMuc.setId(document.getId());
//            cayThuMuc.setTenThuMuc(document.getTen());
//            cayThuMuc.setListSubThuMuc(new ArrayList<>()); // Khởi tạo danh sách con trống ban đầu
//            map.put(document.getId(), cayThuMuc);
//
//            // Nếu là thư mục gốc (parentId == 0), thêm vào danh sách gốc
//            if (document.getParentId() == 0) {
//                roots.add(cayThuMuc);
//            }
//        }
//
//        // Sau đó, lặp qua danh sách tài liệu lần nữa để xây dựng cây thư mục
//        for (Document document : documents) {
//            int parentId = document.getParentId();
//
//            // Bỏ qua các thư mục gốc vì chúng đã được thêm vào danh sách gốc
//            if (parentId == 0) {
//                continue;
//            }
//
//            // Lấy CayThuMuc đại diện cho thư mục cha
//            CayThuMuc parentCayThuMuc = map.get(parentId);
//
//            // Nếu thư mục cha không tồn tại (có thể do dữ liệu không hợp lệ), thì bỏ qua thư mục này
//            if (parentCayThuMuc == null) {
//                continue;
//            }
//
//            // Lấy danh sách con của thư mục cha và thêm CayThuMuc hiện tại vào danh sách đó
//            parentCayThuMuc.getListSubThuMuc().add(map.get(document.getId()));
//        }
//
//        return roots;
//    }
//}