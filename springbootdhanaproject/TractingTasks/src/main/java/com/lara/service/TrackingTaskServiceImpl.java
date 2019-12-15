package com.lara.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.lara.dao.TrackingTaskDao;
import com.lara.exception.MyFileNotFoundException;
import com.lara.model.Constants;
import com.lara.model.TrackingTask;

@Service
@Transactional
public class TrackingTaskServiceImpl implements TrackingTaskService 
{
	private Path uploadLocation;
		
	@PostConstruct
	public void init() {
		this.uploadLocation = Paths.get(Constants .UPLOAD_TRACKINGTASK_LOCATION);
		try {
			Files.createDirectories(uploadLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}
	
	@Autowired
	private TrackingTaskDao trackingTaskDao;

	@Override
	public void saveTrackingTask(TrackingTask trackingTask) {
		
		trackingTaskDao.saveTrackingTask(trackingTask);
	}

	@Override
	public void deleteTrackingTask(Integer trackingtask_id, Integer userid) {
		trackingTaskDao.deleteTrackingTask(trackingtask_id, userid);
	}

	@Override
	public List<TrackingTask> getAllTrackingTask() {
		return trackingTaskDao.getAllTrackingTask();
	}

	@Override
	public TrackingTask getTrackingTaskById(Integer trackingtask_id) {
		return trackingTaskDao.getTrackingTaskById(trackingtask_id);
	}

	@Override
	public String store(MultipartFile file) {
		
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
	public TrackingTask updateTrackingTask(Integer trackingtask_id, TrackingTask trackingtask) {
		// TODO Auto-generated method stub
		return trackingTaskDao.updateTrackingTask(trackingtask_id,trackingtask);
	}

//	@Override
//	public boolean duplicateChecking(String taskname,Integer projectId, Integer moduleid, Integer screenid, HttpServletRequest req) {
//		// TODO Auto-generated method stub
//		return trackingTaskDao.duplicateChecking(taskname,projectId, moduleid,screenid, req) ;
//	}

	@Override
	public  List<TrackingTask> getTrackingTaskName(String taskname) {
		// TODO Auto-generated method stub
		System.out.println(taskname);
		return trackingTaskDao.getTrackingTaskName(taskname);
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

	@Override
	public List<TrackingTask> getTrackingTaskScreenName(String screenName) {
		// TODO Auto-generated method stub
		return trackingTaskDao.getTrackingTaskScreenName(screenName);
	}

	@Override
	public boolean duplicateChecking(String taskname, Integer screenid, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return trackingTaskDao.duplicateChecking(taskname,screenid, req) ;
	}

}
