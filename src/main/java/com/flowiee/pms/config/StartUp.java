package com.flowiee.pms.config;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.Branch;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.model.ServerInfo;
import com.flowiee.pms.model.ShopInfo;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.repository.system.BranchRepository;
import com.flowiee.pms.repository.system.ConfigRepository;
import com.flowiee.pms.repository.system.GroupAccountRepository;
import com.flowiee.pms.service.system.*;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.flowiee.pms.utils.FileUtils;
import com.flowiee.pms.utils.constants.ConfigCode;
import com.flowiee.pms.utils.constants.EndPoint;
import com.opencsv.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StartUp {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	ConfigRepository mvConfigRepository;
	BranchRepository mvBranchRepository;
	AccountRepository mvAccountRepository;
	LanguageService mvLanguageService;
	CustomerRepository mvCustomerRepository;
	CategoryRepository mvCategoryRepository;
	GroupAccountRepository mvGroupAccountRepository;

	public static LocalDateTime START_APP_TIME;
	public static String mvResourceUploadPath = null;

    @Bean
    CommandLineRunner init() {
    	return args -> {
			initData();
			loadShopInfo();
			configReport();
            configEndPoint();
            configResourcePath();

			logger.info("Start downloading vi messages");
			loadLanguageMessages("en");
			logger.info("Finish downloading vi messages");
			logger.info("Start downloading en message");
			loadLanguageMessages("vi");
			logger.info("Finish downloading en message");

			START_APP_TIME = LocalDateTime.now();
        };
    }

	@EventListener
	private void loadServerInfo(WebServerInitializedEvent event) {
		int serverPort = event.getWebServer().getPort();
		String ipAddress = "localhost";
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			Log.info("Can't get local host address");
		}
		System.out.println("Server is running on IP: " + ipAddress + ", Port: " + serverPort);
		CommonUtils.mvServerInfo = new ServerInfo(ipAddress, serverPort);
	}

    private void loadLanguageMessages(String langCode) {
        mvLanguageService.reloadMessage(langCode);
    }

	private void loadShopInfo() {
		ShopInfo shopInfo = new ShopInfo();
		List<String> listCode = List.of("shopName", "shopEmail", "shopPhoneNumber", "shopAddress", "shopLogoUrl");
		List<SystemConfig> systemConfigList = mvConfigRepository.findByCode(listCode);
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

	private void configReport() {
		String templateExportTempStr = FileUtils.excelTemplatePath + "/temp";
		Path templateExportTempPath = Paths.get(templateExportTempStr);
		if (!Files.exists(templateExportTempPath)) {
            try {
                Files.createDirectories(templateExportTempPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
	}

	private void configEndPoint() {
		CommonUtils.mvEndPointHeaderConfig.clear();
		CommonUtils.mvEndPointSideBarConfig.clear();
		for (EndPoint e : EndPoint.values()) {
			if (e.getType().equals("HEADER") && e.isStatus()) {
				CommonUtils.mvEndPointHeaderConfig.put(e.name(), e.getValue());
			}
			if (e.getType().equals("SIDEBAR") && e.isStatus()) {
				CommonUtils.mvEndPointSideBarConfig.put(e.name(), e.getValue());
			}
		}
	}

	private void configResourcePath() {
		SystemConfig systemConfig = mvConfigRepository.findByCode(ConfigCode.resourceUploadPath.name());
		if (systemConfig != null) {
			mvResourceUploadPath = systemConfig.getValue();
		}
	}

	private void initData() throws Exception {
		String flagConfigCode = ConfigCode.initData.name();
		SystemConfig flagConfigObj = mvConfigRepository.findByCode(flagConfigCode);
		if (flagConfigObj == null) {
			List<SystemConfig> cnf = new ArrayList<>();
			cnf.add(initDefaultAudit(ConfigCode.initData, "Initialize initial data for the system", "Y"));
			cnf.add(initDefaultAudit(ConfigCode.shopName, "Tên cửa hàng", "Flowiee"));
			cnf.add(initDefaultAudit(ConfigCode.shopEmail, "Email", "nguyenducviet0684@gmail.com"));
			cnf.add(initDefaultAudit(ConfigCode.shopPhoneNumber, "Số điện thoại", "(+84) 706 820 684"));
			cnf.add(initDefaultAudit(ConfigCode.shopAddress, "Địa chỉ", "Phường 7, Quận 8, Thành phố Hồ Chí Minh"));
			cnf.add(initDefaultAudit(ConfigCode.shopLogoUrl, "Logo", null));
			cnf.add(initDefaultAudit(ConfigCode.emailHost, "Email host", "smtp"));
			cnf.add(initDefaultAudit(ConfigCode.emailPort, "Email port", "587"));
			cnf.add(initDefaultAudit(ConfigCode.emailUser, "Email username", null));
			cnf.add(initDefaultAudit(ConfigCode.emailPass, "Email password", null));
			cnf.add(initDefaultAudit(ConfigCode.sysTimeOut, "Thời gian timeout", "3600"));
			cnf.add(initDefaultAudit(ConfigCode.maxSizeFileUpload, "Dung lượng file tối đa cho phép upload", null));
			cnf.add(initDefaultAudit(ConfigCode.extensionAllowedFileUpload, "Định dạng file được phép upload", null));
			cnf.add(initDefaultAudit(ConfigCode.sendEmailReportDaily, "Gửi mail báo cáo hoạt động kinh doanh hàng ngày", "N"));
			cnf.add(initDefaultAudit(ConfigCode.resourceUploadPath, "Thư mực chứa tệp upload", null));
			mvConfigRepository.saveAll(cnf);
		}
		SystemConfig systemConfigInitData = mvConfigRepository.findByCode(flagConfigCode);
		if ("Y".equals(systemConfigInitData.getValue())) {
			return;
		}
		//Init category
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
		FileReader fileReader = new FileReader(FileUtils.getFileDataCategoryInit());
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
			category.setCreatedBy(-1);
			category.setLastUpdatedBy("SA");
			listCategory.add(category);
		}
		listCategory.remove(0);//header
		mvCategoryRepository.saveAll(listCategory);
		fileReader.close();
		csvReader.close();
		//Init branch
		Branch branch = new Branch(null, "MAIN", "Trụ sở");
		branch.setCreatedBy(-1);
		branch.setLastUpdatedBy("SA");
		Branch branchSaved = mvBranchRepository.save(branch);
		//Init group account
		GroupAccount groupAccountManager = new GroupAccount(null, "MANAGER", "Quản lý cửa hàng");
		groupAccountManager.setCreatedBy(-1);
		groupAccountManager.setLastUpdatedBy("SA");
		GroupAccount groupManagerSaved = mvGroupAccountRepository.save(groupAccountManager);

		GroupAccount groupAccountStaff = new GroupAccount(null, "STAFF", "Nhân viên bán hàng");
		groupAccountStaff.setCreatedBy(-1);
		groupAccountStaff.setLastUpdatedBy("SA");
		GroupAccount groupStaffSaved = mvGroupAccountRepository.save(groupAccountStaff);
		//Init admin account
		Account adminAccount = new Account();
		adminAccount.setUsername(AppConstants.ADMINISTRATOR);
		adminAccount.setPassword(CommonUtils.encodePassword("123456"));
		adminAccount.setFullName("Administrator");
		adminAccount.setSex(true);
		adminAccount.setRole("ADMIN");
		adminAccount.setBranch(branchSaved);
		adminAccount.setGroupAccount(groupManagerSaved);
		adminAccount.setStatus(true);
		adminAccount.setCreatedBy(-1);
		adminAccount.setLastUpdatedBy("SA");
		mvAccountRepository.save(adminAccount);

		Account staffAccount = new Account();
		staffAccount.setUsername("staff");
		staffAccount.setPassword(CommonUtils.encodePassword("123456"));
		staffAccount.setFullName("Staff");
		staffAccount.setSex(true);
		staffAccount.setRole("USER");
		staffAccount.setBranch(branchSaved);
		staffAccount.setGroupAccount(groupStaffSaved);
		staffAccount.setStatus(true);
		staffAccount.setCreatedBy(-1);
		staffAccount.setLastUpdatedBy("SA");
		mvAccountRepository.save(staffAccount);
		//Init customer
		Customer customer = new Customer();
		customer.setCustomerName("Khách vãng lai");
		customer.setDateOfBirth(LocalDate.of(2000, 1, 8));
		customer.setSex(true);
		customer.setCreatedBy(-1);
		customer.setLastUpdatedBy("SA");
		mvCustomerRepository.save(customer);

		systemConfigInitData.setValue("Y");
		mvConfigRepository.save(systemConfigInitData);
	}

	private SystemConfig initDefaultAudit(ConfigCode code, String name, String value) {
		SystemConfig systemConfig = new SystemConfig(code, name, value);
		systemConfig.setCreatedBy(-1);
		systemConfig.setLastUpdatedBy("SA");
		return systemConfig;
	}

	public static String getResourceUploadPath() {
		return mvResourceUploadPath;
	}
}