package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lara.model.Project;

@Service
public interface ProjectService {

	void saveProject(Project project);
	
	public boolean duplicateChecking(Project project,HttpServletRequest req) ;

	List<Project> getAllProject();

	Project getProjectById(Integer project_id);

	Project updateProject(Integer project_id, Project project);

	void deleteProject(Integer project_id, Integer userid);


	void reactivateRecord(Integer project_id, Integer userid);

}
