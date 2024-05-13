package com.flowiee.pms.service.system;

import java.util.Map;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.system.Language;

public interface LanguageService extends CrudService<Language> {
	Map<String, String> findAllLanguageMessages(String langCode);

	void reloadMessage(String langCode);
}