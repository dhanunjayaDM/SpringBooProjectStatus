package com.lara.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lara.exception.DuplicateException;
import com.lara.exception.ReActivateExteption;
import com.lara.model.Screen;
import com.lara.model.TaskType;
import com.lara.model.TrackingTask;
import com.lara.model.TrackingTaskUploadFile;
import com.lara.model.Users;
import com.lara.service.ScreenService;
import com.lara.service.TaskTypeService;
import com.lara.service.TrackingTaskService;
import com.lara.service.TrackingTaskUploadFileService;
import com.lara.service.UserService;
@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/trackingtasks")
public class TrackingTaskController 
{
	
	/**** required code begin ****/
	@Autowired
	private TrackingTaskService trackingTaskService;
	
	@Autowired
	private TaskTypeService taskTypeService;
	
	
	@Autowired
	private ScreenService screenService;
	

	
	@Autowired
	private UserService userService;
	@Autowired
	private TrackingTaskUploadFileService trackingTaskUploadFileService;
	
	
	
	// @PostMapping("/uploadFile")
    public TrackingTaskUploadFile uploadFile(@RequestParam("file") MultipartFile file) 
    {
	    String newfilename=file.getOriginalFilename();
 /// begin 
		   		    
//		    String filename="Address.java";
		    String[] nameParts= newfilename.split("\\.");
		    System.out.println(Arrays.toString(nameParts));
		    String file2=nameParts[0];
		    String fileextention=nameParts[1];
		    System.out.println(file2); 
		    System.out.println(fileextention);
		    
		    StringBuilder sb = new StringBuilder();
	        MessageDigest md;
	        try
	        {
	            // Select the message digest for the hash computation -> SHA-256
	            md = MessageDigest.getInstance("SHA-256");

	            // Generate the random salt
	            SecureRandom random = new SecureRandom();
	            byte[] salt = new byte[16];
	            random.nextBytes(salt);
	            
	            System.out.println(salt);

	            // Passing the salt to the digest for the computation
	            md.update(salt);

	            // Generate the salted hash
	            byte[] hashedPassword = md.digest(file2.getBytes(StandardCharsets.UTF_8));

	           
	            for (byte b : hashedPassword)
	                sb.append(String.format("%02x", b));

	            System.out.println(sb.append("."+fileextention));
	        } catch (NoSuchAlgorithmException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	       String  originalFilename=sb.toString();
		   System.out.println(originalFilename);
		   String fileName2=null;
		try {
			fileName2 = trackingTaskService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		    
		    
	// end
    	
    	
        String fileName = trackingTaskService.store(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/trackingtasks/downloadTrackingTaskFile/")
                .path(fileName2)
                .toUriString();
        return new TrackingTaskUploadFile(fileName, fileDownloadUri,newfilename);
     }
		 
		 
		 @RequestMapping(value="/saveTrackingTask",method=RequestMethod.POST)
		 public ResponseEntity<String>  saveTrackingTask(@RequestParam("files") MultipartFile[] files,HttpServletRequest req) throws ParseException
		 {
			    String taskname=req.getParameter("taskname");
//			    Integer projectId=Integer.parseInt(req.getParameter("project"));
//				Integer modulename=Integer.parseInt(req.getParameter("module"));
				Integer screenid= Integer.parseInt(req.getParameter("screen"));
				try {
					if(trackingTaskService.duplicateChecking(taskname,screenid,req))
					{
						throw new DuplicateException();
					}
					else {
							 System.out.println("controller screen");
							 System.out.println(files.toString());
							
							 Integer user_id= Integer.parseInt(req.getParameter("user"));
							 Integer tasktypeid= Integer.parseInt(req.getParameter("tasktype"));
							
							
							 String taskdesc=req.getParameter("taskdesc");
							 String dateofrelease=req.getParameter("dateofrelease");
							 System.out.println("dateofrelease");
							 System.out.println(dateofrelease);
							 Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateofrelease);
							 System.out.println(date1);
							 String summary=req.getParameter("summary");
							 
							 
						
//							 Project project= projectService.getProjectById(projectId);
//							 Module  module= moduleService.getModuleById(modulename);
							 Screen screen=screenService.getScreenById(screenid);
							 TaskType tasktype=taskTypeService.getTaskTypeById(tasktypeid);
							 Users user=userService.getUserById(user_id);
							 
//							 System.out.println(project);
//							 System.out.println(module);
							 System.out.println(files);
							  
							 TrackingTask trackingtask= new TrackingTask();
//							 trackingtask.setProject(project);
//							 trackingtask.setModule(module);
							 trackingtask.setScreen(screen);
							 trackingtask.setTasktype(tasktype);
							 trackingtask.setUser(user);
							 trackingtask.setTaskName(taskname);
							 trackingtask.setSummary(summary);
							 trackingtask.setTaskDesc(taskdesc);
							 trackingtask.setDateOfRelease(date1);
							
							 String username=SecurityContextHolder.getContext().getAuthentication().getName();
							 System.out.println(username);
							 Integer userid=com.lara.TractingTasksApplication.map.get(username);
							 trackingtask.setCreatedBy(userid.toString());
						     Set<TrackingTaskUploadFile> fileslist=Arrays.asList(files)
						                .stream()
						                .map(file -> uploadFile(file))
						                .collect(Collectors.toSet());
						     System.out.println(fileslist); 
						     for(TrackingTaskUploadFile file:fileslist ) {
						    	 file.setCreatedBy(userid.toString());
						    	 file.setCreatedDate(new Date());
						     }
						     trackingtask.setUploadfiles(fileslist);
						     System.out.println(trackingtask.getUploadfiles());
						     trackingTaskService.saveTrackingTask(trackingtask);
					}
				}catch(ReActivateExteption  e) {
					
					if(e instanceof ReActivateExteption) {
						System.out.println(e.getMessage());
						throw new ReActivateExteption(e.getMessage());
					}
				}
				return  new ResponseEntity<String>( "record is creatd successfully",HttpStatus.CREATED);	
			 
//			 ==============================================================================================================================================================
		    }
		 
		 
		 /**** required code end ****/
	 
		 @GetMapping("/downloadTrackingTaskFile/{fileName:.+}")
		 public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
		 {
		        // Load file as Resource
		        Resource resource = trackingTaskService.loadFileAsResource(fileName);

		        // Try to determine file's content type
		        String contentType = null;
		        try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		           ex.printStackTrace();
		        }

		        // Fallback to the default content type if type could not be determined
		        if(contentType == null) {
		            contentType = "application/octet-stream";
		        }
		        String originalfilename   = trackingTaskUploadFileService.getOriginalFileName(resource.getFilename());

		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalfilename + "\"")
		                .body(resource);
		    }
		 

		 @RequestMapping(value="/getTrackingTask/{trackingtask_id}",method=RequestMethod.GET)
		 public ResponseEntity<TrackingTask> getTrackingTask(@PathVariable Integer trackingtask_id )
		 {
		 System.out.println(trackingtask_id);
		 TrackingTask trackingTask=trackingTaskService.getTrackingTaskById(trackingtask_id);
		 System.out.println(trackingTask);
		 return new ResponseEntity<TrackingTask>(trackingTask,HttpStatus.OK);
		 }

		 
		 @RequestMapping(value="/getAllTrackingTask",method=RequestMethod.GET)
		 public ResponseEntity<List<TrackingTask>> getAllTrackingTask( )
		 {
			 List<TrackingTask> trackingtasks2=new  ArrayList<TrackingTask>();
			 List<TrackingTask> trackingtasks=trackingTaskService.getAllTrackingTask();
			 System.out.println("controller end");
			 System.out.println(trackingtasks);
//			 for(TrackingTask  trackingtask :trackingtasks) {
//				 TrackingTask trackingtask2=new TrackingTask();
//				 trackingtask2.setId(trackingtask.getId());
//				 trackingtask2.setScreen(trackingtask.getScreen());
//				 trackingtask2.setTasktype(trackingtask.getTasktype());
//				 trackingtask2.setUser(trackingtask.getUser());
//				 trackingtask2.setTaskName(trackingtask.getTaskName());
//				 trackingtask2.setTaskDesc(trackingtask.getTaskDesc());
//				 trackingtask2.setSummary(trackingtask.getSummary());
//				 trackingtask2.setUploadfiles(trackingtask.getUploadfiles());
//				 Long ln=Long.parseLong( trackingtask.getDateOfRelease().toString());
//				 Date date =new Date(ln);
//				 trackingtask2.setDateOfRelease(date);
//				 trackingtasks2.add(trackingtask2);
//			 }
//			 System.out.println(trackingtasks2);
//			 
			 return new ResponseEntity<List<TrackingTask>>(trackingtasks, HttpStatus.OK);
		 }
		 
		 
		 @RequestMapping(value="/deleteTrackingTask/{trackingtask_id}",method=RequestMethod.DELETE)
		 public ResponseEntity<String> deleteTrackingTask(@PathVariable Integer trackingtask_id ) 
		 {
			 String username=SecurityContextHolder.getContext().getAuthentication().getName();
			 System.out.println(username);
			 Integer userid=com.lara.TractingTasksApplication.map.get(username);
		     trackingTaskService.deleteTrackingTask(trackingtask_id,userid);
			 return new ResponseEntity<String>( "record is deleted successfully",HttpStatus.OK);
		 }
		 
		 
		 @RequestMapping(value="/updateTrackingTask/{trackingtask_id}",method=RequestMethod.PUT)
		 public ResponseEntity<TrackingTask>  updateTrackingTask(@PathVariable Integer trackingtask_id ,@RequestParam("files") MultipartFile[] files,HttpServletRequest req) throws ParseException
		 {
			     System.out.println("##############updatescreen#############");
				// case-1
				 Map<String, Integer> map=new HashMap<String, Integer>();
				 TrackingTask trackingTask=trackingTaskService.getTrackingTaskById(trackingtask_id);
		
				 System.out.println(map);
				 System.out.println(trackingTask.getUploadfiles());
				// @SuppressWarnings("unchecked")
				 Set<TrackingTaskUploadFile> listfiles=trackingTask.getUploadfiles();
				 System.out.println(listfiles);
				 for(TrackingTaskUploadFile file1 :listfiles)
				 {
					 map.put(file1.getOriginalfileName(),file1.getId());
				 }
				 System.out.println("map");
				 
				 System.out.println(map);
				 
				 
				 List<MultipartFile> newfiles=new ArrayList<MultipartFile>();
				 
				 
				// case-2
				 System.out.println("files");
//				 System.out.println(files.length);
//				 System.out.println(map.size());
//				 
				 
				 for(MultipartFile file: files)
				 {
					 System.out.println("file uploaded");
					 System.out.println(file.getOriginalFilename());
					 if(!map.containsKey(file.getOriginalFilename()))
					 {
		                 System.out.println("newly added file");
						 System.out.println(file.getOriginalFilename());
						 newfiles.add(file);
					 }else if(map.containsKey(file.getOriginalFilename())) {
						 map.remove(file.getOriginalFilename());
					 }
				 }
				 
				 String username=SecurityContextHolder.getContext().getAuthentication().getName();
				 System.out.println(username);
				 Integer userid=com.lara.TractingTasksApplication.map.get(username);
				 System.out.println("newly uploaded file");
				 System.out.println(newfiles);
				 System.out.println("map with values");
				 System.out.println(map);
				 if(!map.isEmpty()) {
					 map.keySet().forEach(value->{
						 trackingTaskUploadFileService.deleteFileName(value,userid);
				 });
					 
				 }
				 System.out.println("controller screen");
				 //System.out.println(files.toString());
//				 Integer projectId=Integer.parseInt(req.getParameter("project"));
//				 Integer modulename=Integer.parseInt(req.getParameter("module"));
				 Integer screenid= Integer.parseInt(req.getParameter("screen"));
				 Integer user_id= Integer.parseInt(req.getParameter("user"));
				 Integer tasktypeid= Integer.parseInt(req.getParameter("tasktype"));
				
				 String taskname=req.getParameter("taskname");
				 String taskdesc=req.getParameter("taskdesc");
				 String dateofrelease=req.getParameter("dateofrelease");
				 System.out.println("dateofrelease");
				 System.out.println(dateofrelease);
				 Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateofrelease);
				 System.out.println(date1);
				 String summary=req.getParameter("summary");
				 
				 
			
//				 Project project= projectService.getProjectById(projectId);
//				 Module  module= moduleService.getModuleById(modulename);
				 Screen screen=screenService.getScreenById(screenid);
				 TaskType tasktype=taskTypeService.getTaskTypeById(tasktypeid);
				 Users user=userService.getUserById(user_id);
				 
//				 System.out.println(project);
//				 System.out.println(module);
				// System.out.println(files);
				  
				 TrackingTask trackingtask= new TrackingTask();
//				 trackingtask.setProject(project);
//				 trackingtask.setModule(module);
				 trackingtask.setScreen(screen);
				 trackingtask.setTasktype(tasktype);
				 trackingtask.setUser(user);
				 trackingtask.setTaskName(taskname);
				 trackingtask.setSummary(summary);
				 trackingtask.setTaskDesc(taskdesc);
				 trackingtask.setDateOfRelease(date1);
				 trackingtask.setModifiedBy(userid.toString());
				 
				 System.out.println(newfiles.size());
				 if(!newfiles.isEmpty()) {
					 MultipartFile[] arr = new MultipartFile[newfiles.size()]; 
					 for (int i =0; i < newfiles.size(); i++) 
					 {
						 arr[i] = newfiles.get(i);
					 }
				            
					 Set<TrackingTaskUploadFile> setoffiles= Arrays.asList(arr).stream().map(file -> uploadFile((MultipartFile) file)).collect(Collectors.toSet());
					 System.out.println("setoffiles"); 
				     System.out.println(setoffiles); 
				     for(TrackingTaskUploadFile file:setoffiles ) {
				    	 file.setCreatedBy(userid.toString());
				    	 file.setCreatedDate(new Date());
				     }
				     trackingtask.setUploadfiles(setoffiles);  
				 }
			     TrackingTask trackingTask2= trackingTaskService.updateTrackingTask(trackingtask_id,trackingtask);
			     return new  ResponseEntity<TrackingTask>(trackingTask2, HttpStatus.CREATED);
		    }
		 
		 @RequestMapping(value="/getTrackingTaskByTaskName/{screenName}",method=RequestMethod.GET)
		 public ResponseEntity<List<TrackingTask>> getTrackingTaskName(@PathVariable String screenName ) 
		 {
			 System.out.println(screenName); 
			 List<TrackingTask> trackingtasks=trackingTaskService.getTrackingTaskScreenName(screenName);
			 return new ResponseEntity<List<TrackingTask>>(trackingtasks,HttpStatus.OK);
		 }
}
