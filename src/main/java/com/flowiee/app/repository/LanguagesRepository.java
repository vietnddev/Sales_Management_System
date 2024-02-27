package com.flowiee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Language;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Integer> {	
	List<Language> findByCode(String code);	
}