package com.lara.service;



import org.springframework.stereotype.Service;

@Service
public interface TrackingTaskUploadFileService {

	void deleteFileName(String value, Integer userid);

	String getOriginalFileName(String filename);

	

}
