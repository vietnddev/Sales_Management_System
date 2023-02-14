package com.flowiee.app.model.storage;

public class DocField_DocData {
	private int IDDoc;
	private int IDDocType;
	private int IDDocField;
	private int IDDocData;
	private String TypeDocField;
	private String NameDocField;
	private String Value;
	private boolean Required;
	private int Sort;

	public DocField_DocData() {
	}

	public DocField_DocData(int iDDoc, int iDDocType, int iDDocField, int iDDocData, String typeDocField,
			String nameDocField, String value, boolean required, int sort) {
		IDDoc = iDDoc;
		IDDocType = iDDocType;
		IDDocField = iDDocField;
		IDDocData = iDDocData;
		TypeDocField = typeDocField;
		NameDocField = nameDocField;
		Value = value;
		Required = required;
		Sort = sort;
	}

	public int getIDDoc() {
		return IDDoc;
	}

	public void setIDDoc(int iDDoc) {
		IDDoc = iDDoc;
	}

	public int getIDDocType() {
		return IDDocType;
	}

	public void setIDDocType(int iDDocType) {
		IDDocType = iDDocType;
	}

	public int getIDDocField() {
		return IDDocField;
	}

	public void setIDDocField(int iDDocField) {
		IDDocField = iDDocField;
	}

	public int getIDDocData() {
		return IDDocData;
	}

	public void setIDDocData(int iDDocData) {
		IDDocData = iDDocData;
	}

	public String getTypeDocField() {
		return TypeDocField;
	}

	public void setTypeDocField(String typeDocField) {
		TypeDocField = typeDocField;
	}

	public String getNameDocField() {
		return NameDocField;
	}

	public void setNameDocField(String nameDocField) {
		NameDocField = nameDocField;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public boolean isRequired() {
		return Required;
	}

	public void setRequired(boolean required) {
		Required = required;
	}

	public int getSort() {
		return Sort;
	}

	public void setSort(int sort) {
		Sort = sort;
	}
}
