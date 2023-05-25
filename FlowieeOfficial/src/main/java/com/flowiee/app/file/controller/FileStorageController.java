package com.flowiee.app.file.controller;

import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.log.service.SystemLogService;
import com.flowiee.app.products.services.SanPhamService;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileStorageController {

	@Autowired
	private FileStorageService fileService;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private SanPhamService productsService;
	@Autowired
	private AccountService accountService;

	@GetMapping("/files")
	public String getAllFiles(ModelMap modelMap) {
		String username = accountService.getUserName();
		if (username != null && !username.isEmpty()){
			// Lấy tất cả ảnh cho page thư viện
			modelMap.addAttribute("listFiles", fileService.getAllImage());
			// Lấy danh sách tên sản phẩm
			modelMap.addAttribute("listProducts", productsService.getAllProducts());
			return "pages/storage/files";
		}
		return PagesUtil.PAGE_LOGIN;
	}   
  
	@PostMapping("/files/uploads/products/{productVariantID}")
	public String uploadFilesVariant(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			@PathVariable("productVariantID") int productVariantID) {
		/*
		 * Thêm mới image trong màn hình biến thể sản phẩm
		 */
		try {
			String username = accountService.getUserName();
			if (username == null || username.isEmpty()){
				return PagesUtil.PAGE_LOGIN;
			}
			if (!file.isEmpty()) {
				String timeCurrent = DateUtil.now("yyyyMMdd_hhmmss");
				// "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\upload";
				Path path = Paths.get("src\\main\\resources\\static\\uploads\\products\\" + productVariantID + "_"
						+ timeCurrent + "_" + file.getOriginalFilename());

				file.transferTo(path);

				// Lấy phần mở rộng của file
				String originalFilename = file.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toString().toLowerCase();

				// Full path
				String fullPath = "D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\" + path;

//				BienTheSanPham p = new BienTheSanPham();
//				p.setProductVariantID(productVariantID);
//
//				// Lưu file vào database
//				FileEntity f = new FileEntity();
//				f.setProductVariant(p);
//				f.setTitle("title");
//				f.setExtension(extension);
//				f.setContentType(file.getContentType());
//				f.setContent(file.getBytes());
//				f.setOriginalName(file.getOriginalFilename());
//				f.setStorageName(productVariantID + "_" + timeCurrent + "_" + file.getOriginalFilename());
//				f.setDirectoryPath(fullPath);
//				f.setCreatedBy(username);
//				f.setSort(0);
//				f.setActive(false);
//				f.setStatus(true);
//				fileService.insertFiles(f);
			}
		} catch (Exception e) {
			System.out.println(e.getCause());
		}
		return "redirect:" + request.getHeader("referer");
	}
}