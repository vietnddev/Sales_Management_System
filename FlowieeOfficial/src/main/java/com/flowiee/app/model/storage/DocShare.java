package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DocShare")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocShare {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DocShareID", unique = true, nullable = false)
	private int DocShareID;
	private int IDUser;
	private int StorageID;
}