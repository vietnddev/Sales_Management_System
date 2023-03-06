package com.flowiee.app.controller.files;


import com.flowiee.app.model.storage.Gallery;
import com.flowiee.app.services.FilesService;
import com.flowiee.app.utils.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FilesController {
 
	@Autowired
	FilesService filesService;
	
	@GetMapping("/sales/products/product-variant/image/upload/{productVariantID}")
	public String uploadFilesVariant(@RequestParam("file") MultipartFile file, HttpServletRequest request,
							 @PathVariable("productVariantID") int productVariantID) {
		try { 
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				// "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\upload";
				Path path = Paths.get("src\\main\\resources\\static\\upload\\" + productVariantID + "_" + DatetimeUtil.now("yyyyMMdd_hhmmss") + "_"
						+ file.getOriginalFilename());
				java.nio.file.Files.write(path, bytes);

				// Lấy phần mở rộng của file
				String originalFilename = file.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toString();

				// Full path
				String asd = "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\" + path;
 
				// Lưu file vào database
				Gallery files = new Gallery();
				files.setProductVariantID(productVariantID);
				files.setFileName(productVariantID + "_" + DatetimeUtil.now("yyyyMMdd_hhmmss") + "_" + file.getOriginalFilename());
				files.setType(file.getContentType());
				files.setUrl(path.toString());
				files.setSort(0);
				files.setNote("");
				files.setStatus(false);
				files.setMain(false);
				files.setExtension(extension);

				filesService.insertGallery(files);
			}
		} catch (Exception e) {
			System.out.println(e.getCause()); 
		}
		return "redirect:" + request.getHeader("referer");
	}

	@GetMapping("/sales/products/product-variant/attributes/image/upload/{productVariantID}")
	public String uploadFilesAttributes(@RequestParam("file") MultipartFile file, HttpServletRequest request,
							 @PathVariable("productVariantID") int productVariantID) {
		try { 
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				// "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\upload";
				Path path = Paths.get("src\\main\\resources\\static\\upload\\" + productVariantID + "_" + DatetimeUtil.now("yyyyMMdd_hhmmss") + "_"
						+ file.getOriginalFilename());
				java.nio.file.Files.write(path, bytes);

				// Lấy phần mở rộng của file
				String originalFilename = file.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toString();

				// Full path
				String asd = "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\" + path;
 
				// Lưu file vào database
				Gallery files = new Gallery();
				files.setProductVariantID(productVariantID);
				files.setFileName(productVariantID + "_" + DatetimeUtil.now("yyyyMMdd_hhmmss") + "_" + file.getOriginalFilename());
				files.setType(file.getContentType());
				files.setUrl(path.toString());
				files.setSort(0);
				files.setNote("");
				files.setStatus(false);
				files.setMain(false);
				files.setExtension(extension);

				filesService.insertGallery(files);
			}
		} catch (Exception e) {
			System.out.println(e.getCause()); 
		}
		return "redirect:" + request.getHeader("referer");
	}		
}
