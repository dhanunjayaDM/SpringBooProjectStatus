package com.lara.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lara.exception.DuplicateException;
import com.lara.exception.ReActivateExteption;
import com.lara.model.Project;
import com.lara.service.ProjectService;
@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/project")
public class ProjectController 
{
	@Autowired
	private ProjectService projectService;
	
	
	@RequestMapping(value="/saveProject",method=RequestMethod.POST)
	public ResponseEntity<String> saveProject(@RequestBody Project project, HttpSession session,HttpServletRequest req) throws Exception 
	{
		System.out.println("controller");
		System.out.println(project);
		try {
			if(projectService.duplicateChecking(project, req))
			{
				throw new DuplicateException();
			}
			else {
				    String username=SecurityContextHolder.getContext().getAuthentication().getName();
					System.out.println(username);
					Integer userid=com.lara.TractingTasksApplication.map.get(username);
				    project.setCreatedBy(userid.toString());
				    projectService.saveProject(project);
			}
		}catch(ReActivateExteption  e) {
			
			if(e instanceof ReActivateExteption) {
				System.out.println(e.getMessage());
				throw new ReActivateExteption(e.getMessage());
			}
		}
		return  new ResponseEntity<String>( "record is creatd successfully",HttpStatus.CREATED);	
	}

	
	@RequestMapping(value="/getAllProjects",method=RequestMethod.GET)
	public ResponseEntity<List<Project>> getAllProject( ) 
	{	
		List<Project> projects=projectService.getAllProject();
		return new ResponseEntity<List<Project>>(projects,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getProject/{project_id}",method=RequestMethod.GET)
	public ResponseEntity<Project> getProject(@PathVariable Integer project_id ) 
	{
		Project project=projectService.getProjectById(project_id);
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/updateProject/{project_id}",method=RequestMethod.PUT)
	public ResponseEntity<Project> editProject(@PathVariable Integer project_id ,@RequestBody Project project, HttpSession session) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		project.setModifiedBy(userid.toString());
		System.out.println(project);  
		Project project2=projectService.updateProject(project_id,project);
		return  new ResponseEntity<Project>(project2,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/deleteProject/{project_id}",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteProject(@PathVariable Integer project_id ) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		projectService.deleteProject(project_id,userid);
		return new ResponseEntity<String>( "record is deleted successfully",HttpStatus.OK);	
	}
	
	
	
	@RequestMapping(value="/reactivateProject/{project_id}",method=RequestMethod.GET)
	public ResponseEntity<Void> reactivateProject(@PathVariable Integer project_id) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		projectService.reactivateRecord(project_id, userid);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	


}
