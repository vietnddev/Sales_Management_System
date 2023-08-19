package com.flowiee.app.khotailieu.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.khotailieu.entity.DocData;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.model.DocMetaResponse;
import com.flowiee.app.khotailieu.model.DocumentType;
import com.flowiee.app.khotailieu.repository.DocumentRepository;
import com.flowiee.app.khotailieu.service.DocDataService;
import com.flowiee.app.khotailieu.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocDataService docDataService;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Document> findRootDocument() {
        List<Document> listDocument = documentRepository.findRootDocument();
        return listDocument;
    }

    @Override
    public List<Document> findRootFolder() {
        List<Document> listRootFolder = documentRepository.findRootFolder();
        return listRootFolder;
    }

    @Override
    public List<Document> findRootFile() {
        List<Document> listRootFile = documentRepository.findRootFile();
        return listRootFile;
    }

    @Override
    public List<Document> findDocumentByParentId(int parentId) {
        return documentRepository.findListDocumentByParentId(parentId);
    }

    @Override
    public List<Document> findFolderByParentId(int parentId) {
        return documentRepository.findListFolderByParentId(parentId);
    }

    @Override
    public List<Document> findFileByParentId(int parentId) {
        return documentRepository.findListFileByParentId(parentId);
    }

    @Override
    public List<Document> findAllFolder() {
        return documentRepository.findAllFolder(DocumentType.FOLDER.name());
    }

    @Override
    public Document findById(int id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public String update(Document data, int documentId) {
        Document document = this.findById(documentId);
        if (document != null) {
            document.setTen(data.getTen());
            document.setMoTa(data.getMoTa());
            documentRepository.save(document);
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String updateMetadata(Integer[] docDataIds, String[] docDataValues, int documentId) {
        Document document = this.findById(documentId);
        if (document == null) {
            throw new BadRequestException();
        }
        for (int i = 0; i < docDataIds.length; i++) {
            DocData docData = docDataService.findById(docDataIds[i]);
            if (docData != null) {
                docData.setNoiDung(docDataValues[i]);
                docDataService.save(docData);
            }
        }
        return "OK";
    }

    @Override
    public String delete(int id) {
        documentRepository.deleteById(id);
        if (findById(id) == null) {
            return "OK";
        } else {
            return "NOK";
        }
    }

    @Override
    public List<DocMetaResponse> getMetadata(int documentId) {
        List<DocMetaResponse> listReturn = new ArrayList<>();

        Query result = entityManager.createQuery("SELECT d.id, d.noiDung, f.tenField, f.loaiField, f.batBuocNhap " +
                                                        "FROM DocField f " +
                                                        "LEFT JOIN DocData d ON f.id = d.docField.id " +
                                                        "WHERE d.document.id = " + documentId);
        List<Object[]> listData = result.getResultList();

        if (!listData.isEmpty()) {
            for (Object[] data : listData) {
                DocMetaResponse metadata = new DocMetaResponse();
                metadata.setDocDataId(Integer.parseInt(String.valueOf(data[0])));
                metadata.setDocDataValue(data[1] != null ? String.valueOf(data[1]) : "");
                metadata.setDocFieldName(String.valueOf(data[2]));
                metadata.setDocFieldTypeInput(String.valueOf(data[3]));
                metadata.setDocFieldRequired(String.valueOf(data[4]).equals("1") ? true : false);
                listReturn.add(metadata);
            }
        }

        return listReturn;
    }

//    @Override
//    public void getCayThuMucSTG() {
//        List<Document> listThuMuc = documentRepository.findListFolder(DocumentType.FOLDER.name());
//
//        // Xây dựng cây thư mục và hiển thị
//        Document root = buildStorageTree(listThuMuc);
//        displayStorageTree(root, "");
//    }

//    public static Document buildStorageTree(List<Document> storages) {
//        Map<Integer, Document> storageMap = new HashMap<>();
//        Document root = null;
//
//        // Tạo danh sách thư mục và thêm vào map
//        for (Document storage : storages) {
//            storageMap.put(storage.getId(), storage);
//            if (storage.getParentId() == 0) {
//                root = storage; // Thư mục gốc
//            }
//        }
//
//        // Xây dựng cây thư mục
//        for (Document storage : storages) {
//            Integer parentStorageId = storage.getParentId();
//            if (parentStorageId != null) {
//                Document parentStorage = storageMap.get(parentStorageId);
//                if (parentStorage != null) {
//                    parentStorage.addSubStorage(storage);
//                }
//            }
//        }
//
//        return root;
//    }

    // Hiển thị cây thư mục
//    public static void displayStorageTree(Document storage, String indent) {
//        System.out.println(indent + storage.getTen());
//        for (Document subStorage : storage.getSubThuMuc()) {
//            displayStorageTree(subStorage, indent + "    ");
//        }
//    }
}