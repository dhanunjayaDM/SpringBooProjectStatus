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
import com.lara.model.TaskType;
import com.lara.service.TaskTypeService;
@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
@RequestMapping("/tasktype")
public class TaskTypeController
{
	@Autowired
	private TaskTypeService taskTypeService;
	
	@RequestMapping(value="/saveTaskType",method=RequestMethod.POST)
	public ResponseEntity<String> saveTaskType(@RequestBody TaskType taskType, HttpSession session,HttpServletRequest req) 
	{
		try {
			if(taskTypeService.duplicateChecking(taskType, req))
			{
				throw new DuplicateException();
			}
			else {
				String username=SecurityContextHolder.getContext().getAuthentication().getName();
				System.out.println(username);
				Integer userid=com.lara.TractingTasksApplication.map.get(username);
				taskType.setCreatedBy(userid.toString());
				taskTypeService.saveTaskType(taskType);
				new ResponseEntity<String>("record is creatd successfully", HttpStatus.CREATED);	
			}
		}catch(ReActivateExteption  e) {
			
			if(e instanceof ReActivateExteption) {
				System.out.println(e.getMessage());
				throw new ReActivateExteption(e.getMessage());
			}
		}
		return  new ResponseEntity<String>( "record is creatd successfully",HttpStatus.CREATED);	
	
	}
	@RequestMapping(value="/getAllTaskTypes",method=RequestMethod.GET)
	public ResponseEntity<List<TaskType>> getAllTaskType( ) 
	{
		List<TaskType> taskTypes=taskTypeService.getAllTaskType() ;
		return  new ResponseEntity<List<TaskType>>(taskTypes,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getTaskType/{tasktype_id}",method=RequestMethod.GET)
	public ResponseEntity<TaskType> getTaskType(@PathVariable Integer tasktype_id ) 
	{
		TaskType tasktype=taskTypeService.getTaskTypeById(tasktype_id);
		return new ResponseEntity<TaskType>(tasktype,HttpStatus.OK);
	}
	@RequestMapping(value="/updateTaskType/{tasktype_id}",method=RequestMethod.PUT)
	public ResponseEntity<TaskType> editTaskType(@PathVariable Integer tasktype_id ,@RequestBody TaskType taskType, HttpSession session) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		taskType.setModifiedBy(userid.toString());
		TaskType taskType2=taskTypeService.updateTaskType(tasktype_id,taskType);
		return new ResponseEntity<TaskType>(taskType2,HttpStatus.OK);
	}
	@RequestMapping(value="/deleteTaskType/{tasktype_id}",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteTaskType(@PathVariable Integer tasktype_id ) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		taskTypeService.deleteTaskType(tasktype_id,userid);
		return new ResponseEntity<String>("record is deleted successfully", HttpStatus.OK);
	}
	
	@RequestMapping(value="/reactivateTaskType/{tasktype_id}",method=RequestMethod.GET)
	public ResponseEntity<Void> reactivateTaskType(@PathVariable Integer tasktype_id) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		System.out.println("controller");
		taskTypeService.reactivateRecord(tasktype_id,userid);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


}
