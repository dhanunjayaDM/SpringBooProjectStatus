package com.lara.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileDao {

	void deleteFileName(String value, Integer userid);

	String getOriginalFileName(String filename);

}
