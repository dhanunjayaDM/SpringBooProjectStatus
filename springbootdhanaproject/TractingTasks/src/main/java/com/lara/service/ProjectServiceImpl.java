package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.ProjectDao;
import com.lara.model.Project;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService 
{
	@Autowired
	private ProjectDao projectDao;

	@Override
	public void saveProject(Project project) {
		projectDao.saveProject(project);
		
	}

	@Override
	public List<Project> getAllProject() {
		return projectDao.getAllProject();
	}

	@Override
	public Project getProjectById(Integer project_id) {
		return projectDao.getProjectById(project_id);
	}

	@Override
	public Project updateProject(Integer project_id, Project project) {
		return projectDao.updateProject(project_id,project);
	}

	@Override
	public void deleteProject(Integer project_id,Integer userid) {
		projectDao.deleteProject(project_id,userid );
	}

	

	

	@Override
	public void reactivateRecord(Integer project_id,Integer userid ) {
		projectDao.reActivateRecord(project_id,userid);
		
	}

	@Override
	public boolean duplicateChecking(Project project, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return projectDao.duplicateChecking(project,req);
	}

	

}
