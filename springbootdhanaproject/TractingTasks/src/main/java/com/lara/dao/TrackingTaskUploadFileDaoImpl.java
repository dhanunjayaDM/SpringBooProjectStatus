package com.lara.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lara.model.TrackingTaskUploadFile;

@Repository
public class TrackingTaskUploadFileDaoImpl implements TrackingTaskUploadFileDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteFileName(String value, Integer userid) {
		System.out.println(value);

		Session session=sessionFactory.getCurrentSession();	 
		Criteria crit=session.createCriteria(TrackingTaskUploadFile.class);
		Criterion cn=Restrictions.eq("fileName", value);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		TrackingTaskUploadFile uploadfiles= (TrackingTaskUploadFile) crit.uniqueResult();
		uploadfiles.setModifiedBy(userid.toString());
		uploadfiles.setModifiedDate(new Date());
		uploadfiles.setIsActive(false);
		session.update(uploadfiles);
		
	}

	@Override
	public String getOriginalFileName(String filename) {
		Session session=sessionFactory.getCurrentSession();	 
		Query query=session.createQuery("select u.originalfileName from TrackingTaskUploadFile u where u.fileName=:fileName");
		query.setString("fileName", filename);
		List<?> l =query.list();
		System.out.println("Total Number Of Records : "+l.size());
		Iterator<?> it = l.iterator();
		String fileName=null;
		while(it.hasNext())
		{
			String fname = (String)it.next();
			fileName=fname;	
		}	
		return fileName;
	}



}
