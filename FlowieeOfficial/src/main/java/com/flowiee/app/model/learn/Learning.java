package com.flowiee.app.model.learn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "UserRole")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Learning {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private String Type;
	private String Name;
	private String Pronounce;
	private String Translate;
	private String Note;
	private String Created;
	private boolean Status;
	private boolean IsGrammar;
	private boolean IsVocabulary;
}
