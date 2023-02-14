package com.flowiee.app.model.storage;

public class DocShare_Account {
	private int ID;
	private int IDDoc;
	private int IDAccount;
	private String NameAccount;

	public DocShare_Account() {
	}
	
	public DocShare_Account(int iD, int iDDoc, int iDAccount, String nameAccount) {
		ID = iD;
		IDDoc = iDDoc;
		IDAccount = iDAccount;
		NameAccount = nameAccount;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getIDDoc() {
		return IDDoc;
	}

	public void setIDDoc(int iDDoc) {
		IDDoc = iDDoc;
	}

	public int getIDAccount() {
		return IDAccount;
	}

	public void setIDAccount(int iDAccount) {
		IDAccount = iDAccount;
	}

	public String getNameAccount() {
		return NameAccount;
	}

	public void setNameAccount(String nameAccount) {
		NameAccount = nameAccount;
	}
}
