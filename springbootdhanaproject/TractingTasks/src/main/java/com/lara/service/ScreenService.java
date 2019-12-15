package com.lara.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lara.model.Screen;

@Service
public interface ScreenService 
{

	String store(MultipartFile file);
	List<Path> listSourceFiles(Path dir) ;
	Path getUploadLocation();
	void saveScreen(Screen screen);
	boolean duplicateChecking(String screenName, Integer projectId, Integer modulename, HttpServletRequest req);

	List<Screen> getAllScreen();
	Resource loadFileAsResource(String fileName);
	Screen getScreenById(Integer screen_id);
	void deleteScreen(Integer screen_id, Integer userid);
	List<Screen> getAllScreenByModuleId(Integer module_id);
	Screen updateScreen(Integer screen_id, Screen screen);
	String store(InputStream inputStream, long size, String contentType, String originalFilename);
//	String getOriginalFileName(String filename);
	
}
