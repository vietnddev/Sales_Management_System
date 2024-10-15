package com.flowiee.pms.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.system.Language;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Long> {
	List<Language> findByCode(String code);	
}