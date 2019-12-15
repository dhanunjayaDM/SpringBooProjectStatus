package com.lara.model;

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
import javax.persistence.ManyToOne;

@Entity
@Table(name="tracking_tasks")
public class TrackingTask 
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
	
	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name="project_id",referencedColumnName="id")
//	private Project project;
//	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name="module_id",referencedColumnName="id")
//	private Module module;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="screen_id",referencedColumnName="id")
	private Screen screen;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="tasktype_id",referencedColumnName="id")
	private TaskType tasktype;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private Users user;
	
	@Column(name="task_name")
	private String taskName;
	
	@Column(name="task_desc")
	private String taskDesc;
	
	@Column(name="summary")
	private String summary;
	
	@Column(name="date_of_release")
	private Date dateOfRelease;
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=TrackingTaskUploadFile.class, cascade=CascadeType.ALL)
	@JoinColumn(name = "trackingtask_id", referencedColumnName="id")
	private Set<TrackingTaskUploadFile> uploadfiles;
	
	
	
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




	




	public Screen getScreen() {
		return screen;
	}




	public void setScreen(Screen screen) {
		this.screen = screen;
	}




	public TaskType getTasktype() {
		return tasktype;
	}




	public void setTasktype(TaskType tasktype) {
		this.tasktype = tasktype;
	}




	public Users getUser() {
		return user;
	}




	public void setUser(Users user) {
		this.user = user;
	}




	public String getTaskName() {
		return taskName;
	}




	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}




	public String getTaskDesc() {
		return taskDesc;
	}




	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}




	public String getSummary() {
		return summary;
	}




	public void setSummary(String summary) {
		this.summary = summary;
	}




	public Date getDateOfRelease() {
		return dateOfRelease;
	}




	public void setDateOfRelease(Date dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}




	public Set<TrackingTaskUploadFile> getUploadfiles() {
		return uploadfiles;
	}




	public void setUploadfiles(Set<TrackingTaskUploadFile> uploadfiles) {
		this.uploadfiles = uploadfiles;
	}




	public TrackingTask() {
	}




	@Override
	public String toString() {
		return "TrackingTask [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate+", screen=" + screen + ", tasktype=" + tasktype + ", user=" + user
				+ ", taskName=" + taskName + ", taskDesc=" + taskDesc + ", summary=" + summary + ", dateOfRelease="
				+ dateOfRelease + ", uploadfiles=" + uploadfiles + "]";
	}
	
	
}
