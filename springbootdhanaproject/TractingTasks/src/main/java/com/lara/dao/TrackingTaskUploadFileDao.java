package com.lara.dao;



public interface TrackingTaskUploadFileDao {

	void deleteFileName(String value, Integer userid);

	String getOriginalFileName(String filename);



}
