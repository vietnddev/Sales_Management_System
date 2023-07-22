package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.hethong.entity.CauHinhHeThong;
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
    public CauHinhHeThong findById(Integer id) {
        return flowieeConfigRepository.findById(id).orElse(null);
    }

    @Override
    public List<CauHinhHeThong> findAll() {
        return flowieeConfigRepository.findAll();
    }

    @Override
    public String save(CauHinhHeThong cauHinhHeThong) {
        flowieeConfigRepository.save(cauHinhHeThong);
        return "OK";
    }

    @Override
    public String update(CauHinhHeThong cauHinhHeThong, Integer id) {
        cauHinhHeThong.setId(id);
        flowieeConfigRepository.save(cauHinhHeThong);
        return "OK";
    }

    @Override
    public String delete(Integer id) {
        flowieeConfigRepository.deleteById(id);
        return "OK";
    }

    @Override
    public void defaultConfig() {
        this.save(new CauHinhHeThong(0,"Email host", "smtp",0));
        this.save(new CauHinhHeThong(0,"Email port", "587",0));
        this.save(new CauHinhHeThong(0,"Email username", "",0));
        this.save(new CauHinhHeThong(0,"Email password", "",0));
        this.save(new CauHinhHeThong(0,"Thời gian timeout", "",0));
        this.save(new CauHinhHeThong(0,"Thư mục lưu file upload", "",0));
        this.save(new CauHinhHeThong(0,"Gửi mail báo cáo hoạt động kinh doanh hàng ngày", "false",0));
        this.save(new CauHinhHeThong(0,"Dung lượng file tối đa cho phép upload", "",0));
        this.save(new CauHinhHeThong(0,"Định dạng file được phép upload", "",0));
    }
}