package com.flowiee.app.controller.storage;

import com.flowiee.app.model.storage.Gallery;
import com.flowiee.app.services.GalleryService;
import com.flowiee.app.utils.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class GalleryController {

	@Autowired
	GalleryService galleryService;

	@PostMapping("/gallery/upload-idproduct={id}")
	public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			@PathVariable("id") int id) {
		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				// "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\upload";
				Path path = Paths.get("src\\main\\resources\\static\\upload\\" + id + "_" + DatetimeUtil.now("yyyyMMdd_hhmmss") + "_"
						+ file.getOriginalFilename());
				Files.write(path, bytes);

				// Lấy phần mở rộng của file
				String originalFilename = file.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toString();

				// Full path
				String asd = "E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\" + path;

				// Lưu file vào database
				Gallery gallery = new Gallery();
				gallery.setProductID(id);
				gallery.setFileName(id + "_" + DatetimeUtil.now("yyyyMMdd_hhmmss") + "_" + file.getOriginalFilename());
				gallery.setType(file.getContentType());
				gallery.setUrl(path.toString());
				gallery.setSort(0);
				gallery.setNote("");
				gallery.setStatus(false);
				gallery.setMain(false);
				gallery.setExtension(extension);

				galleryService.insertGallery(gallery);
			}
		} catch (Exception e) {
			System.out.println(e.getCause());
		}
		return "redirect:" + request.getHeader("referer");
	}
}
