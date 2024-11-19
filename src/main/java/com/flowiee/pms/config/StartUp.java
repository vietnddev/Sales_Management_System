package com.flowiee.pms.config;

import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.Branch;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.model.ServerInfo;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.repository.system.BranchRepository;
import com.flowiee.pms.repository.system.ConfigRepository;
import com.flowiee.pms.repository.system.GroupAccountRepository;
import com.flowiee.pms.service.system.*;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flowiee.pms.utils.FileUtils;
import com.flowiee.pms.utils.constants.AccountStatus;
import com.flowiee.pms.utils.constants.ConfigCode;
import com.flowiee.pms.utils.constants.EndPoint;
import com.flowiee.pms.utils.constants.NotificationType;
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

	ConfigRepository       mvConfigRepository;
	BranchRepository       mvBranchRepository;
	AccountRepository      mvAccountRepository;
	LanguageService        mvLanguageService;
	CustomerRepository     mvCustomerRepository;
	CategoryRepository     mvCategoryRepository;
	GroupAccountRepository mvGroupAccountRepository;
	ConfigService          configService;
	TemplateSendEmail      templateSendEmail;

	public static LocalDateTime                                     START_APP_TIME;
	public static String                                            mvResourceUploadPath      = null;
	public static Map<NotificationType, TemplateSendEmail.Template> mvGeneralEmailTemplateMap = new HashMap<>();

    @Bean
    CommandLineRunner init() {
    	return args -> {
			initData();
			configReport();
            configEndPoint();
            configService.refreshApp();
			logger.info("Finish loads system configs");

			//logger.info("Start downloading vi messages");
			loadLanguageMessages("en");
			logger.info("Finish downloading vi messages");
			//logger.info("Start downloading en message");
			loadLanguageMessages("vi");
			logger.info("Finish downloading en message");

			List<TemplateSendEmail.Template> lvGeneralMailTemplates = templateSendEmail.getGeneralMailTemplates();
			lvGeneralMailTemplates.forEach(lvTemplate -> {
				NotificationType lvNotificationType = NotificationType.valueOf(lvTemplate.getType());
				String lvEncoding = lvTemplate.getEncoding();
				String lvTemplatePath = lvTemplate.getPath();
				StringBuilder lvTemplateContent = new StringBuilder("");
				if (Files.exists(Path.of(lvTemplatePath))) {
					byte[] lvBuf = new byte[1024];
					try (BufferedInputStream lvIs = new BufferedInputStream(new FileInputStream(new File(lvTemplatePath)));
						 ByteArrayOutputStream lvOs = new ByteArrayOutputStream()) {
						int lvBytesRead = -1;
						while ((lvBytesRead = lvIs.read(lvBuf)) != -1) {
							lvOs.write(lvBuf, 0, lvBytesRead);
						}
						lvTemplateContent.append(new String(lvOs.toByteArray(), lvEncoding));
					} catch (IOException e) {
						logger.warn(e.getMessage(), e);
					}
				}
				lvTemplate.setTemplateContent(lvTemplateContent.toString());
				mvGeneralEmailTemplateMap.put(lvNotificationType, lvTemplate);
			});

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

	private void initData() throws Exception {
		String flagConfigCode = ConfigCode.initData.name();
		SystemConfig flagConfigObj = mvConfigRepository.findByCode(flagConfigCode);
		if (flagConfigObj == null) {
			List<SystemConfig> cnf = new ArrayList<>();
			cnf.add(initConfigModel(ConfigCode.initData, "Initialize initial data for the system", "Y"));
			cnf.add(initConfigModel(ConfigCode.shopName, "Tên cửa hàng", "Flowiee"));
			cnf.add(initConfigModel(ConfigCode.shopEmail, "Email", "nguyenducviet0684@gmail.com"));
			cnf.add(initConfigModel(ConfigCode.shopPhoneNumber, "Số điện thoại", "(+84) 706 820 684"));
			cnf.add(initConfigModel(ConfigCode.shopAddress, "Địa chỉ", "Phường 7, Quận 8, Thành phố Hồ Chí Minh"));
			cnf.add(initConfigModel(ConfigCode.shopLogoUrl, "Logo", null));
			cnf.add(initConfigModel(ConfigCode.emailHost, "Email host", "smtp"));
			cnf.add(initConfigModel(ConfigCode.emailPort, "Email port", "587"));
			cnf.add(initConfigModel(ConfigCode.emailUser, "Email username", null));
			cnf.add(initConfigModel(ConfigCode.emailPass, "Email password", null));
			cnf.add(initConfigModel(ConfigCode.sysTimeOut, "Thời gian timeout", "3600"));
			cnf.add(initConfigModel(ConfigCode.maxSizeFileUpload, "Dung lượng file tối đa cho phép upload", null));
			cnf.add(initConfigModel(ConfigCode.extensionAllowedFileUpload, "Định dạng file được phép upload", null));
			cnf.add(initConfigModel(ConfigCode.sendEmailReportDaily, "Gửi mail báo cáo hoạt động kinh doanh hàng ngày", "N"));
			cnf.add(initConfigModel(ConfigCode.resourceUploadPath, "Thư mực chứa tệp upload", null));
			cnf.add(initConfigModel(ConfigCode.deleteSystemLog, "Xóa nhật ký hệ thống tự động", "N"));
			cnf.add(initConfigModel(ConfigCode.dayDeleteSystemLog, "Thời gian xóa nhật ký hệ thống, các nhật ký có thời gian tạo từ >= ? ngày sẽ được xóa tự động", "100"));
			cnf.add(initConfigModel(ConfigCode.sendNotifyCustomerOnOrderConfirmation, "Gửi email thông báo đến khách hàng khi đơn hàng đã được xác nhận", "N"));
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
			Category category = Category.builder()
				.type(row[0])
				.code(row[1])
				.name(row[2])
				.status(Boolean.parseBoolean(row[3]))
				.isDefault(row[4])
				.endpoint(row[5]).build();
				initModelWithDefaultAudit(category);
			listCategory.add(category);
		}
		listCategory.remove(0);//header
		mvCategoryRepository.saveAll(listCategory);
		fileReader.close();
		csvReader.close();
		//Init branch
		Branch branch = Branch.builder().branchCode("MAIN").branchName("Trụ sở").build();
		initModelWithDefaultAudit(branch);
		Branch branchSaved = mvBranchRepository.save(branch);
		//Init group account
		GroupAccount groupAccountManager = GroupAccount.builder().groupCode("MANAGER").groupName("Quản lý cửa hàng").build();
		initModelWithDefaultAudit(groupAccountManager);
		GroupAccount groupManagerSaved = mvGroupAccountRepository.save(groupAccountManager);

		GroupAccount groupAccountStaff = GroupAccount.builder().groupCode("STAFF").groupName("Nhân viên bán hàng").build();
		initModelWithDefaultAudit(groupAccountStaff);
		GroupAccount groupStaffSaved = mvGroupAccountRepository.save(groupAccountStaff);
		//Init admin account
		Account adminAccount = Account.builder()
			.username(AppConstants.ADMINISTRATOR).password(CommonUtils.encodePassword("123456"))
			.fullName("Administrator").sex(true)
			.role("ADMIN")
			.branch(branchSaved).groupAccount(groupManagerSaved)
			.status(AccountStatus.N.name())
			.build();
			initModelWithDefaultAudit(adminAccount);
		mvAccountRepository.save(adminAccount);

		Account staffAccount = Account.builder()
			.username("staff").password(CommonUtils.encodePassword("123456"))
			.fullName("Staff").sex(true)
			.role("USER")
			.branch(branchSaved).groupAccount(groupStaffSaved)
			.status(AccountStatus.N.name())
			.build();
			initModelWithDefaultAudit(staffAccount);
		mvAccountRepository.save(staffAccount);
		//Init customer
		Customer customer = Customer.builder().customerName("Khách vãng lai")
				.dateOfBirth(LocalDate.of(2000, 1, 8)).sex(true).build();
			initModelWithDefaultAudit(customer);
		mvCustomerRepository.save(customer);

		systemConfigInitData.setValue("Y");
		mvConfigRepository.save(systemConfigInitData);
	}

	private SystemConfig initConfigModel(ConfigCode code, String name, String value) {
		SystemConfig systemConfig = new SystemConfig(code, name, value);
		initModelWithDefaultAudit(systemConfig);
		return systemConfig;
	}

	private BaseEntity initModelWithDefaultAudit(BaseEntity baseEntity) {
		baseEntity.setCreatedBy(-1l);
		baseEntity.setLastUpdatedBy("SA");
		return baseEntity;
	}

	public static String getResourceUploadPath() {
		return mvResourceUploadPath;
	}
}