package com.flowiee.sms.service;

import java.util.Map;

import com.flowiee.sms.entity.Language;

public interface LanguageService {
	Language findById(Integer langId);
	
	Map<String, String> findAllLanguageMessages(String langCode);
	
	Language update(Language language, Integer langId);

	void reloadMessage(String langCode);
}