package com.lara.model;

import java.util.Date;
//,uniqueConstraints = {@UniqueConstraint(columnNames = {"project_name", "description"})}

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="module")
public class Module 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="created_date")
	private Date  createdDate;
	 
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;
	 
	@Column(name="is_active")
	private Boolean isActive;
	
	@Column(name="module_name")
	private String moduleName;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="project_id",referencedColumnName="id")
	private Project project;
	
	
	
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "Module [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", isActive=" + isActive + ", moduleName=" + moduleName
				+ ", project=" + project + "]";
	}
	
	
	

}
