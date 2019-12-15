package com.lara.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lara.dao.ScreenDao;
import com.lara.exception.MyFileNotFoundException;
import com.lara.model.Constants;
import com.lara.model.Screen;




@Service
@Transactional
public class ScreenServiceImpl implements ScreenService 
{
	@Autowired
	private ScreenDao screenDao;
	
    private Path uploadLocation;
	
	@PostConstruct
	public void init() {
		this.uploadLocation = Paths.get(Constants .UPLOAD_LOCATION);
		try {
			Files.createDirectories(uploadLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}

	@Override
	public String store(MultipartFile file)
	{
		file.getOriginalFilename();
		String filename = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file " + filename);
			}
			
			// This is a security check
			if (filename.contains("..")) {
				throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
			}
			
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.uploadLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
		return filename;

	}

	@Override
	public List<Path> listSourceFiles(Path dir) {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{txt,doc,java}")) {
			for (Path entry : stream) {
				result.add(entry);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Path getUploadLocation() 
	{
		return uploadLocation;
	}

	@Override
	public void saveScreen(Screen screen) 
	{
		screenDao.saveScreen(screen) ;
	}


	@Override
	public boolean duplicateChecking(String screenName,Integer projectId ,Integer moduleId , HttpServletRequest req) 
	{
		return screenDao.duplicateChecking(screenName,projectId,moduleId,req);
	}
	
	@Override
	public Resource loadFileAsResource(String fileName) {
	try {
	        Path filePath = this.uploadLocation.resolve(fileName).normalize();
	        org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
	        if(resource.exists()) {
	            return (Resource) resource;
	        } else {
	            throw new MyFileNotFoundException("File not found " + fileName);
	        }
	    } catch (MalformedURLException ex) {
	        throw new MyFileNotFoundException("File not found " + fileName, ex);
	    }
	}

	@Override
	public Screen getScreenById(Integer screen_id) 
	{
		return screenDao.getScreenById(screen_id);
	}

	public List<Screen> getAllScreen()
	{
		return screenDao.getAllScreen();
	}

	@Override
	public void deleteScreen(Integer screen_id,Integer userid) 
	{
		screenDao.deleteScreen(screen_id, userid);
	}

	@Override
	public List<Screen> getAllScreenByModuleId(Integer module_id) {
		return screenDao. getAllScreenByModuleId(module_id);
	}

	@Override
	public Screen updateScreen(Integer screen_id, Screen screen) {
		
		return screenDao.updateScreen(screen_id,screen);
	}

	@Override
	public String store(InputStream inputStream, long size, String contentType, String originalFilename) {
			
			try (InputStream inputStream2 = inputStream) {
				Files.copy(inputStream2, this.uploadLocation.resolve(originalFilename), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return originalFilename;
		
	}

//	@Override
//	public String getOriginalFileName(String filename) {
//		// TODO Auto-generated method stub
////		return screenDao.getOriginalFileName(filename);
//	}

}
