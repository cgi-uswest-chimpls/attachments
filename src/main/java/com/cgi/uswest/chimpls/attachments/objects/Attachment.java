package com.cgi.uswest.chimpls.attachments.objects;

import java.math.BigDecimal;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
public class Attachment {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private BigDecimal id;
	
	@Column(name="idmessage")
	private String idmessage;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="binaryfile", columnDefinition="blob")
	private Blob binaryfile;
	
	
	Attachment() {}
	
	public Attachment(String idmessage, String filename, Blob binaryfile) {
		this.idmessage = idmessage;
		this.filename = filename;
		this.binaryfile = binaryfile;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getIdmessage() {
		return idmessage;
	}

	public void setIdmessage(String idmessage) {
		this.idmessage = idmessage;
	}

	public Blob getBinaryfile() {
		return binaryfile;
	}

	public void setBinaryfile(Blob binaryfile) {
		this.binaryfile = binaryfile;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}
	
}
