package com.lara.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
import com.lara.service.ScreenService;
import com.lara.service.UploadFileService;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lara.model.Screen;
import com.lara.model.UploadFile;
import com.lara.service.ModuleService;
import com.lara.service.ProjectService;
import com.lara.exception.DuplicateException;
import com.lara.exception.ReActivateExteption;
import com.lara.model.Module;
import com.lara.model.Project;
@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/screen")
public class ScreenController 
{
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private UploadFileService uploadFileService;

	
	 public UploadFile uploadFile(@RequestParam("file") MultipartFile file) 
	 {
		    System.out.println(file);
		    System.out.println("file to be saving");
		    System.out.println(file.getOriginalFilename());
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
			fileName2 = screenService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		    
		    
	// end	        
		   // System.out.println(file.getName());
	   //  String fileName = screenService.store(file);
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/screen/downloadFile/")
	                .path(fileName2)
	                .toUriString();
	        return new UploadFile(fileName2, fileDownloadUri,newfilename );
	  }
	 
	 
	 @RequestMapping(value="/saveScreen",method=RequestMethod.POST)
	 public ResponseEntity<String>  saveScreen(@RequestParam("files") MultipartFile[] files,HttpServletRequest req)
	  {
		 System.out.println("controller screen");
		 String screenname=req.getParameter("screenName");
		 Integer projectId=Integer.parseInt(req.getParameter("project"));
		 Integer modulename=Integer.parseInt(req.getParameter("module"));
		 try {
				if(screenService.duplicateChecking(screenname, projectId,modulename, req))
				{
					throw new DuplicateException();
				}
				else {
					 System.out.println("controller screen");
					 System.out.println(files.toString());
//					 Integer projectId=Integer.parseInt(req.getParameter("project"));
//					 Integer modulename=Integer.parseInt(req.getParameter("module"));
					 String description=req.getParameter("description");
					 Project project= projectService.getProjectById(projectId);
					 Module  module= moduleService.getModuleById(modulename);
					 System.out.println(project);
					 System.out.println(module);
					 System.out.println(files);
					 Screen screen1= new Screen();
				   //  screen1.setProject(project);
					 screen1.setModule(module);
					 screen1.setScreenName(screenname);
					 screen1.setDescription(description);
					 String username=SecurityContextHolder.getContext().getAuthentication().getName();
					 System.out.println(username);
					 Integer userid=com.lara.TractingTasksApplication.map.get(username);
					 screen1.setCreatedBy(userid.toString());
					

				     Set<UploadFile> setoffiles=Arrays.asList(files)
				                .stream()
				                .map(file -> uploadFile(file))
				                .collect(Collectors.toSet());
				     System.out.println(setoffiles); 
				     for(UploadFile file:setoffiles ) {
//				    	 file.setOriginalfileName(file.getOriginalfileName());
				    	 file.setCreatedBy(userid.toString());
				    	 file.setCreatedDate(new Date());
				     }
				     screen1.setUploadfiles(setoffiles);   
				     screenService.saveScreen(screen1);
				}
			}catch(ReActivateExteption  e) {
				if(e instanceof ReActivateExteption) {
					System.out.println(e.getMessage());
					throw new ReActivateExteption(e.getMessage());
				}
			}
	     return new  ResponseEntity<String>("record is creatd successfully", HttpStatus.CREATED);
	    }
	 
	 
	 @GetMapping("/downloadFile/{fileName:.+}")
	 public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
	 {
	        // Load file as Resource
	        Resource resource = screenService.loadFileAsResource(fileName);

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
	        String originalfilename   = uploadFileService.getOriginalFileName(resource.getFilename());
	        		
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalfilename + "\"")
	                .body(resource);
	 }
	 
	 
	 @RequestMapping(value="/getScreen/{screen_id}",method=RequestMethod.GET)
	 public ResponseEntity<Screen> getScreen(@PathVariable Integer screen_id )
	 {
	 System.out.println(screen_id);
	 System.out.println("screen_id");
	 Screen screen=screenService.getScreenById(screen_id);
	 System.out.println(screen);
	 return new ResponseEntity<Screen>(screen,HttpStatus.OK);
	 }

	 
	 @RequestMapping(value="/getAllScreen",method=RequestMethod.GET)
	 public ResponseEntity<List<Screen>> getAllScreen( )
	 {
	 List<Screen> screens=screenService.getAllScreen() ;
	 return new ResponseEntity<List<Screen>>(screens,HttpStatus.OK);
	 }
	  
	 
	 @RequestMapping(value="/deleteScreen/{screen_id}",method=RequestMethod.DELETE)
	 public ResponseEntity<String> deleteScreen(@PathVariable Integer screen_id ) throws IOException 
	 {
		 String username=SecurityContextHolder.getContext().getAuthentication().getName();
		 System.out.println(username);
		 Integer userid=com.lara.TractingTasksApplication.map.get(username);
		 
		 
//		   Path fileToDeletePath = Paths.get("src/test/resources/fileToDelete_jdk7.txt");
//		   Files.delete(fileToDeletePath);
		 
	     screenService.deleteScreen(screen_id, userid );
		 return new ResponseEntity<String>( "record is deleted successfully",HttpStatus.OK);
	 }
	
	 
	 
	 @RequestMapping(value="/getAllScreenByModuleId/{module_id}",method=RequestMethod.GET)
	 public ResponseEntity<List<Screen>> getAllScreenByModuleId(@PathVariable Integer module_id )
	 {
	 System.out.println(module_id);
	 List<Screen> screens=screenService.getAllScreenByModuleId(module_id);
	 System.out.println(screens);
	 return new ResponseEntity<List<Screen>>(screens,HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="/updateScreen/{screen_id}",method=RequestMethod.PUT)
	 public ResponseEntity<Screen> editScreen(@PathVariable Integer screen_id, @RequestParam("files") MultipartFile[] files,HttpServletRequest req) throws InvocationTargetException 
	 {
		 System.out.println("##############updatescreen#############");
		// case-1
		 Map<String, Integer> map=new HashMap<String, Integer>();
		 Screen screen=screenService.getScreenById(screen_id);
		 System.out.println(map);
		 System.out.println(screen.getUploadfiles());
		// @SuppressWarnings("unchecked")
		 Set<UploadFile> listfiles=screen.getUploadfiles();
		 System.out.println(listfiles);
		 for(UploadFile file1 :listfiles)
		 {
			 System.out.println(file1.getOriginalfileName());
			 map.put(file1.getOriginalfileName(),file1.getId());
		 }
		 System.out.println("map");
		 
		 System.out.println(map);
		 
		 
		 List<MultipartFile> newfiles=new ArrayList<MultipartFile>();
		 
		 
		// case-2
		 System.out.println("files");
	 
		 
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
			 
			 uploadFileService.deleteFileName(value,userid);

		 });
			 
		 }

		 Screen screen2 =new Screen();
		 System.out.println("controller screen");
		 String screenname=req.getParameter("screenName");
		 System.out.println("controller screen");
		// Integer projectId=Integer.parseInt(req.getParameter("project"));
		 Integer modulename=Integer.parseInt(req.getParameter("module"));
		 String description=req.getParameter("description");
		// Project project= projectService.getProjectById(projectId);
		 Module  module= moduleService.getModuleById(modulename);
		// System.out.println(project);
		 System.out.println(module);
		 Screen screen1= new Screen();
	    // screen1.setProject(project);
		 screen1.setModule(module);
		 screen1.setScreenName(screenname);
		 screen1.setDescription(description);
		 
		 screen1.setCreatedBy(userid.toString());
		 System.out.println(newfiles.size());
		 MultipartFile[] arr = new MultipartFile[newfiles.size()]; 
		 for (int i =0; i < newfiles.size(); i++) 
		 {
			 arr[i] = newfiles.get(i);
		 }
	            
		 Set<UploadFile> setoffiles= Arrays.asList(arr).stream().map(file -> {
			
				return uploadFile((MultipartFile) file);
			
		}).collect(Collectors.toSet());
		 
		
		 
	     System.out.println(setoffiles); 
	     for(UploadFile file:setoffiles ) {
	    	 file.setCreatedBy(userid.toString());
	    	 file.setCreatedDate(new Date());
	     }
	     screen1.setUploadfiles(setoffiles);   
	     screen2=screenService.updateScreen(screen_id,screen1);
				    
			return  new ResponseEntity<Screen>(screen2,HttpStatus.OK);
	}
	 
	 
	 
	 
	 

	 

}
