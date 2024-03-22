package com.flowiee.pms.service.system;

import java.util.Map;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.system.Language;

public interface LanguageService extends BaseService<Language> {
	Map<String, String> findAllLanguageMessages(String langCode);

	void reloadMessage(String langCode);
}