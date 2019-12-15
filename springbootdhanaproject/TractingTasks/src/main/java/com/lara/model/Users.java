package com.lara.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="users")
public class Users 
{
	

	
    

	@Override
	public String toString() {
		return "Users [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", isActive=" + isActive + ", username=" + username
				+ ", password=" + password + ", roles=" + roles + ", permissions=" + permissions + "]";
	}

	//@Size(max=11)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//@CreationTimestamp
	@Column(name="created_date")
	private Date   createdDate;
	 
	//@UpdateTimestamp
	@Column(name="modified_date")
	private Date modifiedDate;
	//@Size(max=50)
	@Column(name="created_by")
	private String createdBy;
	//@Size(max=50)
	@Column(name="modified_by")
	private String modifiedBy;
	 
	@Column(name="is_active")
	private Boolean isActive;
	//@Column(unique = true)
	//@Size(max=50)
	@Column(name="user_name")
	private String username;
	//@Size(max=150)
	@Column
	private String password;
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
		})
	@JoinTable(name = "users_role",joinColumns = { @JoinColumn(name = "users_id") },
    inverseJoinColumns = { @JoinColumn(name = "roles_id")})
	private Set<Role> roles = new HashSet<>();


	//@Size(max=250)
    private String permissions = "";
	
    

	public Users(String username, String password, String roleslist) {
		super();
		this.username = username;
		this.password = password;
		//this.roleslist = roleslist;
	
	}

//	public String getRoleslist() {
//		return roleslist;
//	}
//
//	public void setRoleslist(String roleslist) {
//		this.roleslist = roleslist;
//	}

	public Users() {
		// TODO Auto-generated constructor stub
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
//	public String getRoles() {
//		return roles;
//	}

//	public void setRoles(String roles) {
//		this.roles = roles;
//	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
//	 public List<String> getRoleList(){
//	        if(this.roleslist.length() > 0){
//	            return Arrays.asList(this.roleslist.split(","));
//	        }
//	        return new ArrayList<>();
//	    }

	    public List<String> getPermissionList(){
	        if(this.permissions.length() > 0){
	            return Arrays.asList(this.permissions.split(","));
	        }
	        return new ArrayList<>();
	    }

		public Set<Role> getRoles() {
			return roles;
		}

		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}
	    
	    
	    
	

}
