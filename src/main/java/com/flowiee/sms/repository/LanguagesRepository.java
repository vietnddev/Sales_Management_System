package com.flowiee.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.Language;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Integer> {	
	List<Language> findByCode(String code);	
}