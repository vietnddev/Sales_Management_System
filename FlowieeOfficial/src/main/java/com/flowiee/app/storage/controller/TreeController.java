package com.flowiee.app.storage.controller;

import com.flowiee.app.storage.entity.Document;
import com.flowiee.app.storage.service.DocumentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TreeController {
    @Autowired
    private DocumentService documentService;

    public List<Item> getAllItems() {
        List<Document> listRaw = documentService.findAllFolder();

        List<Item> items = new ArrayList<>();
        for (Document d : listRaw) {
            Item item = new Item();
            item.setId(d.getId());
            item.setName(d.getTen());
            item.setType("");
            item.setParentId(d.getParentId());
            items.add(item);
        }

        return buildTree(items);
    }

    private List<Item> buildTree(List<Item> items) {
        Map<Integer, Item> map = new HashMap<>();

        // Đầu tiên, tạo một map với key là id của từng item
        for (Item item : items) {
            map.put(item.getId(), item);
        }

        // Tiếp theo, duyệt qua danh sách items để cấu trúc lại cây thư mục
        List<Item> tree = new ArrayList<>();
        for (Item item : items) {
            int parentId = item.getParentId();
            if (parentId == 0) {
                // Nếu parentId == 0, đây là cấp thư mục root, thêm vào danh sách cây
                tree.add(item);
            } else {
                // Nếu parentId != 0, đây là thư mục con, thêm vào danh sách con của thư mục cha
                Item parent = map.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(item);
                }
            }
        }

        return tree;
    }

    @GetMapping("/tree")
    public String showTreeView(Model model) {
        List<Item> ls = getAllItems();
        model.addAttribute("folders", getAllItems());

        return "/pages/kho-tai-lieu/tree";
    }

    // Định nghĩa lớp Item
    @Data
    private static class Item {
        private int id;
        private String name;
        private String type;
        private int parentId;
        private List<Item> children = new ArrayList<>();

        public Item() {}

        public Item(String name, String type) {
            this.name = name;
            this.type = type;
            this.children = new ArrayList<>();
        }

        public Item(int id, String name, String type, int parentId) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.parentId = parentId;
            this.children = new ArrayList<>();
        }

        public void addChild(Item child) {
            children.add(child);
        }

        public List<Item> getChildren() {
            return children;
        }
    }
}
