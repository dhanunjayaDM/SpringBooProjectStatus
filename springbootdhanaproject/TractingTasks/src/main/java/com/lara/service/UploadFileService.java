package com.lara.service;

import org.springframework.stereotype.Service;

@Service
public interface UploadFileService {



	 // void deleteFileName(String value);

	void deleteFileName(String value, Integer userid);

	String getOriginalFileName(String filename);

}
