package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.CauHinhHeThong;

import java.util.List;

public interface FlowieeConfigService {
    CauHinhHeThong findById(Integer id);

    CauHinhHeThong findByKey(String key);

    List<CauHinhHeThong> findAll();

    String save(CauHinhHeThong cauHinhHeThong);

    String update(CauHinhHeThong cauHinhHeThong, Integer id);

    String delete(Integer id);

    void defaultConfig();
}