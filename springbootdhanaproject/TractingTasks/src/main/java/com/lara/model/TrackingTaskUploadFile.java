package com.lara.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="trackingtask_uploadfiles")
public class TrackingTaskUploadFile 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
		
	@Column(name="created_date")
	private Date   createdDate;
	 
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;
	 
	@Column(name="is_active")
	private Boolean isActive;
	
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_download_url")
	private String filedownloadUrl;
	
	@Column(name="original_file_name")
	private String originalfileName;
	
	



	public String getOriginalfileName() {
		return originalfileName;
	}


	public void setOriginalfileName(String originalfileName) {
		this.originalfileName = originalfileName;
	}


	public TrackingTaskUploadFile(String fileName, String filedownloadUrl, String originalfileName) {
		super();
		this.fileName = fileName;
		this.filedownloadUrl = filedownloadUrl;
		this.originalfileName = originalfileName;
	}


	public TrackingTaskUploadFile() {
		super();
	}


	public TrackingTaskUploadFile(String fileName, String filedownloadUrl) {
		super();
		this.fileName = fileName;
		this.filedownloadUrl = filedownloadUrl;
	}


	public String getFiledownloadUrl() {
		return filedownloadUrl;
	}


	public void setFiledownloadUrl(String filedownloadUrl) {
		this.filedownloadUrl = filedownloadUrl;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	@Override
	public String toString() {
		return "UploadFile [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", isActive=" + isActive + ", fileName="
				+ fileName + ", filedownloadUrl=" + filedownloadUrl + "]";
	}

}
