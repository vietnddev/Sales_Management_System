package com.flowiee.app.service;

import java.util.Map;

import com.flowiee.app.entity.Language;

public interface LanguageService {
	Language findById(Integer langId);
	
	Map<String, String> findAllLanguageMessages(String langCode);
	
	Language update(Language language, Integer langId);

	void reloadMessage(String langCode);
}