package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.hethong.entity.FlowieeConfig;
import com.flowiee.app.hethong.repository.FlowieeConfigRepository;
import com.flowiee.app.hethong.service.FlowieeConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowieeConfigServiceImpl implements FlowieeConfigService {
    private static final Logger logger = LoggerFactory.getLogger(FlowieeConfigServiceImpl.class);

    @Autowired
    private FlowieeConfigRepository flowieeConfigRepository;

    @Override
    public FlowieeConfig findById(Integer id) {
        return flowieeConfigRepository.findById(id).orElse(null);
    }

    @Override
    public FlowieeConfig findByKey(String key) {
        return flowieeConfigRepository.findByKey(key);
    }

    @Override
    public List<FlowieeConfig> findAll() {
        return flowieeConfigRepository.findAll();
    }

    @Override
    public String save(FlowieeConfig flowieeConfig) {
        flowieeConfigRepository.save(flowieeConfig);
        return "OK";
    }

    @Override
    public String update(FlowieeConfig flowieeConfig, Integer id) {
        flowieeConfig.setId(id);
        flowieeConfigRepository.save(flowieeConfig);
        return "OK";
    }

    @Override
    public String delete(Integer id) {
        flowieeConfigRepository.deleteById(id);
        return "OK";
    }

    @Override
    public void defaultConfig() {
        this.save(new FlowieeConfig(0,TagName.EMAIL_HOST,"Email host", "smtp",0));
        this.save(new FlowieeConfig(0,TagName.EMAIL_PORT,"Email port", "587",0));
        this.save(new FlowieeConfig(0,TagName.EMAIL_USERNAME,"Email username", "",0));
        this.save(new FlowieeConfig(0,TagName.EMAIL_PASSWORD,"Email password", "",0));
        this.save(new FlowieeConfig(0,TagName.TIMEOUT,"Thời gian timeout", "",0));
        this.save(new FlowieeConfig(0,TagName.PATH_UPLOAD,"Thư mục lưu file upload", "",0));
        this.save(new FlowieeConfig(0,TagName.SEND_MAIL_REPORT_DAILY,"Gửi mail báo cáo hoạt động kinh doanh hàng ngày", "false",0));
        this.save(new FlowieeConfig(0,TagName.MAX_SIZE_UPLOAD,"Dung lượng file tối đa cho phép upload", "",0));
        this.save(new FlowieeConfig(0,TagName.EXTENSION_ALLOWED_UPLOAD,"Định dạng file được phép upload", "",0));
    }
}