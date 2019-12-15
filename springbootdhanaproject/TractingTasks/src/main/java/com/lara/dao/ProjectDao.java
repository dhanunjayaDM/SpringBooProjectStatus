package com.lara.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;


import com.lara.model.Project;

@Repository
public interface ProjectDao 
{

	void saveProject(Project project);
	
	

	List<Project> getAllProject();

	Project getProjectById(Integer project_id);

	Project updateProject(Integer project_id, Project project);

	void deleteProject(Integer project_id, Integer userid);

	void reActivateRecord(Integer project_id, Integer userid);

	boolean duplicateChecking(Project project, HttpServletRequest req);

}
