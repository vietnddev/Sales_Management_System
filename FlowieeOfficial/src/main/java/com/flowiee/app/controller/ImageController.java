package com.flowiee.app.controller;

import com.flowiee.app.model.Image;
import com.flowiee.app.model.Product_Variants;
import com.flowiee.app.services.AccountService;
import com.flowiee.app.services.ImageService;
import com.flowiee.app.services.SystemLogService;
import com.flowiee.app.services.ProductsService;
import com.flowiee.app.utils.DatetimeUtil;
import com.flowiee.app.utils.PagesUtil;
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
public class ImageController {

	@Autowired
	private ImageService imageService;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private AccountService accountService;

	@GetMapping("/files")
	public String getAllFiles(ModelMap modelMap) {
		String username = accountService.getUserName();
		if (username != null && !username.isEmpty()){
			// Lấy tất cả ảnh cho page thư viện
			modelMap.addAttribute("listFiles", imageService.getAllImage());
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
			if (!file.isEmpty()) {
				String timeCurrent = DatetimeUtil.now("yyyyMMdd_hhmmss");
				// "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\upload";
				Path path = Paths.get("src\\main\\resources\\static\\uploads\\products\\" + productVariantID + "_"
						+ timeCurrent + "_" + file.getOriginalFilename());

				file.transferTo(path);

				// Lấy phần mở rộng của file
				String originalFilename = file.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toString();

				// Full path
				String fullPath = "D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\" + path;

				Product_Variants p = new Product_Variants(); 
				p.setProductVariantID(productVariantID);
				 
				// Lưu file vào database
				Image filess = new Image();
				filess.setProductVariant(p);
				filess.setFileName(productVariantID + "_" + timeCurrent + "_" + file.getOriginalFilename());
				filess.setType(file.getContentType());
				filess.setUrl(fullPath);
				filess.setSort(0);
				filess.setNote("");
				filess.setStatus(false);
				filess.setMain(false);
				filess.setExtension(extension);
				imageService.insertFiles(filess);

//				String users = "Test log insert image";
//				String type = "Thay đổi nội dung";
//				String content = "Thêm mới hình ảnh sản phẩm";
//				String url = "/upload/sales/products/variants/" + productVariantID;
//				String created = DatetimeUtil.now("yyyy-MM-dd HH:mm:ss");
//				// String ip = IPUtil.getClientIpAddress(request);
//				systemLogService.writeLog(new SystemLog(users, type, content, url, created, "0.0.0.0"));
			}
		} catch (Exception e) {
			System.out.println(e.getCause());
		}
		return "redirect:" + request.getHeader("referer");
	}
}