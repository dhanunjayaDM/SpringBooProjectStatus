package com.lara.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.UploadFileDao;

@Service
@Transactional
public class UploadFileServiceImpl implements UploadFileService {
	
	@Autowired
	private UploadFileDao uploadFileDao;

	@Override
	public void deleteFileName(String value,Integer userid) {
		uploadFileDao.deleteFileName( value, userid);
		
	}

	@Override
	public String getOriginalFileName(String filename) {
		// TODO Auto-generated method stub
		return uploadFileDao.getOriginalFileName(filename);
	}

}
