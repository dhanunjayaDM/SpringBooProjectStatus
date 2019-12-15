package com.lara.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lara.exception.ReActivateExteption;

import com.lara.model.Screen;
import com.lara.model.UploadFile;
@Repository
public  class ScreenDaoImpl implements ScreenDao 
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveScreen(Screen screen) {
		Session session=sessionFactory.getCurrentSession();
		Screen screen2=new Screen();
		screen2.setCreatedDate(new Date());
		screen2.setIsActive(true);
		//screen2.setProject(screen.getProject());
		screen2.setModule(screen.getModule());
		screen2.setScreenName(screen.getScreenName());
		screen2.setDescription(screen.getDescription());
		Set<UploadFile> uploadfiles2=new HashSet<UploadFile>();
		Set<UploadFile> uploadfiles=screen.getUploadfiles();
		for(UploadFile upldfile :uploadfiles)
		{
			UploadFile uploadfile=new UploadFile();
			uploadfile.setCreatedBy(upldfile.getCreatedBy());
			uploadfile.setCreatedDate(new Date());
			uploadfile.setOriginalfileName(upldfile.getOriginalfileName());
			uploadfile.setFileName(upldfile.getFileName());
			uploadfile.setFiledownloadUrl(upldfile.getFiledownloadUrl());
			uploadfile.setIsActive(true);
			uploadfiles2.add(uploadfile);
		}
		screen2.setUploadfiles(uploadfiles2);
		session.save(screen2);

	}
	@Override
	public boolean duplicateChecking(String screenname,Integer projectId, Integer moduleId, HttpServletRequest req) {
		boolean isDulicateRecord=false;
		try {
			Session session=sessionFactory.getCurrentSession();
			
			Query query=session.createQuery("from Screen s where s.project.id = :projectId and s.module.id = :moduleId and s.screenName=:screenname and s.isActive=1");
			query.setString("projectId", projectId.toString());
			query.setString("moduleId", moduleId.toString());
			query.setString("screenname", screenname);
			Screen screen2=(Screen) query.uniqueResult();
			if(screen2!=null && screen2.getIsActive()){
				isDulicateRecord=true;
			}
			else if(screen2!=null && !screen2.getIsActive()) {
				req.getSession().setAttribute("reactivateId",screen2.getId());
					throw new ReActivateExteption(""+screen2.getId());	
			}
			else {
				isDulicateRecord= false;
			}
		
		} catch (Exception  e) {
			if(e instanceof ReActivateExteption)
				throw new ReActivateExteption(e.getMessage());
		}
		return isDulicateRecord;
		
	}

	@Override
	public Screen getScreenById(Integer screen_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Screen s where s.id=:id and s.isActive=1");
		query.setInteger("id", screen_id);
//		Query query=session.createQuery("from Screen s where s.project.id = :projectId and s.module.id = :moduleId and s.screenName=:screenname and s.isActive=1");
	
		Screen screen2=(Screen) query.uniqueResult();
		
//		Criteria crit=session.createCriteria(Screen.class);
//		Criterion cn=Restrictions.eq("id", screen_id);
//	    Criterion cn2=Restrictions.eq("isActive", true);
//		crit.add(cn);
//		crit.add(cn2);
//		Screen screen= (Screen) crit.uniqueResult();
		Hibernate.initialize(screen2.getUploadfiles());
		return screen2;
	}

	@Override
	public List<Screen> getAllScreen() {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Screen.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		@SuppressWarnings("unchecked")
		List<Screen> screens=crit.list();
		for(Screen screen:screens) {
		Hibernate.initialize(screen.getUploadfiles());
		}
		return screens; 
	}

	@Override
	public void deleteScreen(Integer screen_id,Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Screen screen2=session.get(Screen.class, screen_id);
		screen2.setIsActive(false);
		screen2.setModifiedBy(userid.toString());
		screen2.setModifiedDate(new Date());
		session.update(screen2);
	}
	@Override
	public List<Screen> getAllScreenByModuleId(Integer module_id) {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Screen.class);
		Criterion cn=Restrictions.eq("module.id", module_id);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		@SuppressWarnings("unchecked")
		List<Screen> screens=crit.list();
		System.out.println(screens);
		return screens;
	}
	@Override
	public Screen updateScreen(Integer screen_id, Screen screen) {
		System.out.println("update Dao");
		System.out.println(screen);
		Session session=sessionFactory.getCurrentSession();
		Screen screen2=session.get(Screen.class, screen_id);
		screen2.setId(screen_id);
		screen2.setIsActive(true);
		screen2.setModifiedDate(new Date());
		screen2.setModifiedBy(screen.getModifiedBy());
	//	screen2.setProject(screen.getProject());
		screen2.setModule(screen.getModule());
		screen2.setScreenName(screen.getScreenName());
		screen2.setDescription(screen.getDescription());
		Set<UploadFile> uploadfiles2=new HashSet<UploadFile>();
		
		Set<UploadFile> uploadfiles=screen.getUploadfiles();
		for(UploadFile upldfile :uploadfiles)
		{
			UploadFile uploadfile=new UploadFile();
			uploadfile.setCreatedBy(upldfile.getCreatedBy());
			uploadfile.setCreatedDate(new Date());
			uploadfile.setOriginalfileName(upldfile.getOriginalfileName());
			uploadfile.setFileName(upldfile.getFileName());
			uploadfile.setFiledownloadUrl(upldfile.getFiledownloadUrl());
			uploadfile.setIsActive(true);
			uploadfiles2.add(uploadfile);
		}
		
		Set<UploadFile> uploadfiles3=screen2.getUploadfiles();
		for(UploadFile upldfile :uploadfiles3)
		{
			UploadFile uploadfile=new UploadFile();
			uploadfile.setModifiedBy(upldfile.getCreatedBy());
			uploadfile.setModifiedDate(new Date());
			uploadfile.setFileName(upldfile.getFileName());
			uploadfile.setOriginalfileName(upldfile.getOriginalfileName());
			uploadfile.setFiledownloadUrl(upldfile.getFiledownloadUrl());
			uploadfile.setIsActive(true);
			uploadfiles2.add(uploadfile);
		}
		
		
		screen2.setUploadfiles(uploadfiles2);
		session.update(screen2);
		return screen2;
	}
//	@Override
//	public String getOriginalFileName(String filename) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
//	@Override
//	public List<Screen> getAllScreenByProjectId(Integer projectid) {
//		Session session=sessionFactory.getCurrentSession();
//		Criteria crit=session.createCriteria(Screen.class);
//		Criterion cn=Restrictions.eq("project", projectid);
//		Criterion cn2=Restrictions.eq("isActive", true);
//		crit.add(cn);
//		crit.add(cn2);
//		@SuppressWarnings("unchecked")
//		List<Screen> screens=crit.list();
//		for(Screen screen:screens) {
//		Hibernate.initialize(screen.getUploadfiles());
//		}
//		return null; 
//	}

}
