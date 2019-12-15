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
import com.lara.model.Module;
import com.lara.model.Project;
import com.lara.service.ModuleService;
import com.lara.service.ProjectService;
@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
@RequestMapping("/module")
public class ModuleController 
{
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value="/saveModule",method=RequestMethod.POST)
	public ResponseEntity<String> saveModule(@RequestBody Module module, HttpSession session,HttpServletRequest req) 
	{
		System.out.println("module controller");
		System.out.println(module.getModuleName());
		System.out.println(module.getProject());
		String projectid=req.getParameter("project");
		System.out.println(projectid);
		try {
			if(moduleService.duplicateChecking(module, req))
			{
				throw new DuplicateException();
			}
			else {
				System.out.println("module controller");
				System.out.println(module.getProject());
				System.out.println(module.getProject().getId());
				Project project=projectService.getProjectById(module.getProject().getId());
				module.setProject(project);
				String username=SecurityContextHolder.getContext().getAuthentication().getName();
			    System.out.println(username);
				Integer userid=com.lara.TractingTasksApplication.map.get(username);
				module.setCreatedBy(userid.toString());
				moduleService.saveModule(module);
				new ResponseEntity<String>("record is created successfully", HttpStatus.CREATED);	
			}
		}catch(ReActivateExteption  e) {
			
			if(e instanceof ReActivateExteption) {
				System.out.println(e.getMessage());
				throw new ReActivateExteption(e.getMessage());
			}
		}
		return  new ResponseEntity<String>( "record is creatd successfully",HttpStatus.CREATED);	
	}
	
	
	@RequestMapping(value="/getAllModules",method=RequestMethod.GET)
	public ResponseEntity<List<Module>> getAllModules( ) 
	{
		List<Module> modules=moduleService.getAllModules() ;
		return new ResponseEntity<List<Module>>(modules,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getModule/{module_id}",method=RequestMethod.GET)
	public ResponseEntity<Module> getModule(@PathVariable Integer module_id ) 
	{
		Module module=moduleService.getModuleById(module_id);
		return new ResponseEntity<Module>(module,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/updateModule/{module_id}",method=RequestMethod.PUT)
	public ResponseEntity<Module> editModule(@PathVariable Integer module_id ,@RequestBody Module module, HttpSession session) 
	{
		System.out.println(module.getProject().getId());
		Project project=projectService.getProjectById(module.getProject().getId());
		module.setProject(project);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		module.setModifiedBy(userid.toString());
		Module module2=moduleService.updateModule(module_id,module);
		return  new ResponseEntity<Module>(module2,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/deleteModule/{module_id}",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteModule(@PathVariable Integer module_id ) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		moduleService.deleteModule(module_id,userid);
		return new ResponseEntity<String>("record is deleted successfully" ,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/reactivateModule/{module_id}",method=RequestMethod.GET)
	public ResponseEntity<Void> reactivateModule(@PathVariable Integer module_id) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		System.out.println("controller");
		moduleService.reactivateRecord(module_id,userid);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getModuleByProjectId/{project_id}",method=RequestMethod.GET)
	public ResponseEntity<List<Module>> getAllModuleByProjectId(@PathVariable Integer project_id ) 
	{
		System.out.println("  getModuleByProjectId  ");
		System.out.println(project_id);
		List<Module> modules=	moduleService.getAllModuleByProjectId(project_id);
		return new ResponseEntity<List<Module>>(modules,HttpStatus.OK);
	}
	


}
