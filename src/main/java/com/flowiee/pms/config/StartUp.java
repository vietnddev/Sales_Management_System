package com.flowiee.pms.config;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.Branch;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.model.ShopInfo;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.service.sales.OrderService;
import com.flowiee.pms.service.system.*;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class StartUp {
	private final ConfigService       configService;
	private final BranchService 	  branchService;
	private final AccountService      accountService;
	private final LanguageService 	  languageService;
	private final CustomerService     customerService;
	private final CategoryRepository  categoryRepository;
	private final GroupAccountService groupAccountService;

	public StartUp(LanguageService languageService, ConfigService configService, @Lazy CategoryRepository categoryRepository,
				   @Lazy BranchService branchService, @Lazy AccountService accountService, @Lazy CustomerService customerService,
				   @Lazy GroupAccountService groupAccountService) {
		this.configService = configService;
		this.branchService = branchService;
		this.accountService = accountService;
		this.languageService = languageService;
		this.customerService = customerService;
		this.categoryRepository = categoryRepository;
		this.groupAccountService = groupAccountService;
	}

    @Bean
    CommandLineRunner init() {
    	return args -> {
			initData();
			loadShopInfo();
			initReportConfig();
            initEndPointConfig();
			loadLanguageMessages("en");
			loadLanguageMessages("vi");
			CommonUtils.START_APP_TIME = LocalDateTime.now();
        };
    }

    private void loadLanguageMessages(String langCode) {
        languageService.reloadMessage(langCode);
    }

	private void loadShopInfo() {
		ShopInfo shopInfo = new ShopInfo();
		List<String> listCode = List.of("shopName", "shopEmail", "shopPhoneNumber", "shopAddress", "shopLogoUrl");
		List<SystemConfig> systemConfigList = configService.getSystemConfigs(listCode);
		for (SystemConfig sysConfig : systemConfigList) {
			switch (sysConfig.getCode()) {
				case "shopName":
					shopInfo.setName(sysConfig.getName());
					break;
				case "shopEmail":
					shopInfo.setEmail(sysConfig.getName());
					break;
				case "shopPhoneNumber":
					shopInfo.setPhoneNumber(sysConfig.getName());
					break;
				case "shopAddress":
					shopInfo.setAddress(sysConfig.getName());
					break;
				case "shopLogoUrl":
					shopInfo.setLogoUrl(sysConfig.getName());
					break;
            }
		}
		CommonUtils.mvShopInfo = shopInfo;
	}

	private void initEndPointConfig() {
		CommonUtils.mvEndPointHeaderConfig.clear();
		CommonUtils.mvEndPointSideBarConfig.clear();
		for (AppConstants.ENDPOINT e : AppConstants.ENDPOINT.values()) {
			if (e.getType().equals("HEADER") && e.isStatus()) {
				CommonUtils.mvEndPointHeaderConfig.put(e.name(), e.getValue());
			}
			 if (e.getType().equals("SIDEBAR") && e.isStatus()) {
				CommonUtils.mvEndPointSideBarConfig.put(e.name(), e.getValue());
			}
		}
    }

	private void initReportConfig() {
		String templateExportTempStr = CommonUtils.excelTemplatePath + "/temp";
		Path templateExportTempPath = Paths.get(templateExportTempStr);
		if (!Files.exists(templateExportTempPath)) {
            try {
                Files.createDirectory(templateExportTempPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
	}

	private void initData() throws Exception {
		String flagConfigCode = "initData";
		SystemConfig flagConfigObj = configService.getSystemConfig(flagConfigCode);
		if (flagConfigObj == null) {
			CommonUtils.mvInitData = false;
			configService.save(new SystemConfig(flagConfigCode, "Initialize initial data for the system", "N"));
			configService.save(new SystemConfig("shopName", "Tên cửa hàng", "Flowiee"));
			configService.save(new SystemConfig("shopEmail", "Email", "nguyenducviet0684@gmail.com"));
			configService.save(new SystemConfig("shopPhoneNumber", "Số điện thoại", "(+84) 706 820 684"));
			configService.save(new SystemConfig("shopAddress", "Địa chỉ", "Phường 7, Quận 8, Thành phố Hồ Chí Minh"));
			configService.save(new SystemConfig("shopLogoUrl", "Logo", null));
			configService.save(new SystemConfig("emailHost", "Email host", "smtp"));
			configService.save(new SystemConfig("emailPort", "Email port", "587"));
			configService.save(new SystemConfig("emailUser", "Email username", null));
			configService.save(new SystemConfig("emailPass", "Email password", null));
			configService.save(new SystemConfig("sysTimeOut", "Thời gian timeout", "3600"));
			configService.save(new SystemConfig("pathFileUpload", "Thư mục lưu file upload", null));
			configService.save(new SystemConfig("maxSizeFileUpload", "Dung lượng file tối đa cho phép upload", null));
			configService.save(new SystemConfig("extensionAllowedFileUpload", "Định dạng file được phép upload", null));
			configService.save(new SystemConfig("sendEmailReportDaily", "Gửi mail báo cáo hoạt động kinh doanh hàng ngày", "N"));
		}
		SystemConfig systemConfigInitData = configService.getSystemConfig(flagConfigCode);
		if ("Y".equals(systemConfigInitData.getValue())) {
			return;
		}
		//Init category
		String fileNameCsvCategory = CommonUtils.initCsvDataPath + "/Category.csv";
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
		FileReader fileReader = new FileReader(fileNameCsvCategory);
		CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
		List<Category> listCategory = new ArrayList<>();
		for (String[] row : csvReader.readAll()) {
			//System.out.println(String.join(", ", row));
			Category category = new Category();
			category.setType(row[0]);
			category.setCode(row[1]);
			category.setName(row[2]);
			category.setStatus(Boolean.parseBoolean(row[3]));
			category.setIsDefault(row[4]);
			category.setEndpoint(row[5]);
			listCategory.add(category);
		}
		listCategory.remove(0);//header
		categoryRepository.saveAll(listCategory);
		fileReader.close();
		csvReader.close();
		//Init branch
		Branch branchSaved = branchService.save(new Branch(null, "MAIN", "Trụ sở"));
		//Init group account
		GroupAccount groupManagerSaved = groupAccountService.save(new GroupAccount(null, "MANAGER", "Quản lý cửa hàng"));
		GroupAccount groupStaffSaved = groupAccountService.save(new GroupAccount(null, "STAFF", "Nhân viên bán hàng"));
		//Init admin account
		Account adminAccount = new Account();
		adminAccount.setUsername(CommonUtils.ADMINISTRATOR);
		adminAccount.setPassword((new BCryptPasswordEncoder()).encode("123456"));
		adminAccount.setFullName("Administrator");
		adminAccount.setSex(true);
		adminAccount.setRole("ADMIN");
		adminAccount.setBranch(branchSaved);
		adminAccount.setGroupAccount(groupManagerSaved);
		adminAccount.setStatus(true);
		Account adminAccountSaved = accountService.save(adminAccount);
		Account staffAccount = new Account();
		staffAccount.setUsername("staff");
		staffAccount.setPassword((new BCryptPasswordEncoder()).encode("123456"));
		staffAccount.setFullName("Staff");
		staffAccount.setSex(true);
		staffAccount.setRole("USER");
		staffAccount.setBranch(branchSaved);
		staffAccount.setGroupAccount(groupStaffSaved);
		staffAccount.setStatus(true);
		Account staffAccountSaved = accountService.save(staffAccount);
		//Init customer
		CustomerDTO customer = new CustomerDTO();
		customer.setCustomerName("David Nguyen");
		customer.setDateOfBirth(LocalDate.of(2000, 1, 8));
		customer.setSex(true);
		customer.setPhoneDefault("0706820684");
		customer.setEmailDefault("nguyenducviet0684@gmail.com");
		customer.setAddressDefault("Ben Tre Province");
		customer.setCreatedBy(adminAccountSaved.getId());
		CustomerDTO customerSaved = customerService.save(customer);

		systemConfigInitData.setValue("Y");
		configService.save(systemConfigInitData);
		CommonUtils.mvInitData = true;
	}
}