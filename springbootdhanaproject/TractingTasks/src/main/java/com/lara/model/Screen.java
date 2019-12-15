package com.lara.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="screen")
public class Screen implements Serializable
{
	/**
	 * 
	 */
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
	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name="project_id",referencedColumnName="id")
//	private Project project;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="module_id",referencedColumnName="id")
	private Module module;
	
	@Column(name="screen_name")
	private String screenName;
	
	@Column(name="description")
	private String description;
	
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=UploadFile.class, cascade=CascadeType.ALL)
	@JoinColumn(name = "screen_id", referencedColumnName="id")
	private Set<UploadFile> files;

	
	

	public Screen() {
		super();
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


//	public Project getProject() {
//		return project;
//	}
//
//
//	public void setProject(Project project) {
//		this.project = project;
//	}


	public Module getModule() {
		return module;
	}


	public void setModule(Module module) {
		this.module = module;
	}


	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Set<UploadFile> getUploadfiles() {
		return files;
	}


	public void setUploadfiles(Set<UploadFile> files) {
		this.files = files;
	}


	@Override
	public String toString() {
		return "Screen [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", isActive=" + isActive + ",  module=" + module + ", screenName=" + screenName + ", description=" + description
				+ ", files=" + files + "]";
	}
	
	

}
