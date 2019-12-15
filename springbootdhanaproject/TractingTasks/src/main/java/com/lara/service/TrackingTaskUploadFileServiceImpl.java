package com.lara.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.TrackingTaskUploadFileDao;

@Service
@Transactional
public class TrackingTaskUploadFileServiceImpl implements TrackingTaskUploadFileService {
	@Autowired
	private TrackingTaskUploadFileDao trackingTaskUploadFileDao;

	@Override
	public void deleteFileName(String value, Integer userid) {
		trackingTaskUploadFileDao. deleteFileName(value,userid);
	}

	@Override
	public String getOriginalFileName(String filename) {

		return trackingTaskUploadFileDao.getOriginalFileName(filename) ;
	}



}
