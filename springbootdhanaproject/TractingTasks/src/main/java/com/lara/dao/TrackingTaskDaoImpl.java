package com.lara.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lara.exception.ReActivateExteption;
import com.lara.model.TrackingTask;
import com.lara.model.TrackingTaskUploadFile;



@Repository
public class TrackingTaskDaoImpl implements TrackingTaskDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	

	@Override
	public void saveTrackingTask(TrackingTask trackingTask) 
	{
		System.out.println("dao trackingtask dao");
		System.out.println(trackingTask.getUploadfiles());
		Session session=sessionFactory.getCurrentSession();
		TrackingTask trackingTask2=new TrackingTask();
		trackingTask2.setCreatedDate(new Date());
		trackingTask2.setIsActive(true);
		trackingTask2.setCreatedBy(trackingTask.getCreatedBy());
//		trackingTask2.setProject(trackingTask.getProject());
//		trackingTask2.setModule(trackingTask.getModule());
		trackingTask2.setScreen(trackingTask.getScreen());
		trackingTask2.setTasktype(trackingTask.getTasktype());
		trackingTask2.setUser(trackingTask.getUser());
		trackingTask2.setTaskName(trackingTask.getTaskName());
		trackingTask2.setTaskDesc(trackingTask.getTaskDesc());
		trackingTask2.setSummary(trackingTask.getSummary());
		trackingTask2.setDateOfRelease(trackingTask.getDateOfRelease());
		Set<TrackingTaskUploadFile> uploadfiles2=new HashSet<TrackingTaskUploadFile>();
		System.out.println(uploadfiles2);
		Set<TrackingTaskUploadFile> uploadfiles=trackingTask.getUploadfiles();
		System.out.println(uploadfiles);
		for(TrackingTaskUploadFile upldfile :uploadfiles)
		{
			TrackingTaskUploadFile uploadfile=new TrackingTaskUploadFile();
			uploadfile.setCreatedBy(upldfile.getCreatedBy());
			uploadfile.setCreatedDate(new Date());
			uploadfile.setOriginalfileName(upldfile.getOriginalfileName());
			uploadfile.setFileName(upldfile.getFileName());
			uploadfile.setFiledownloadUrl(upldfile.getFiledownloadUrl());
			uploadfile.setIsActive(true);
			uploadfiles2.add(uploadfile);
		}
		trackingTask2.setUploadfiles(uploadfiles2);
		System.out.println(" dao end ");
		System.out.println(trackingTask2);
		session.save(trackingTask2);
		
	}

	@Override
	public void deleteTrackingTask(Integer trackingtask_id,Integer userid ) 
	{
		Session session=sessionFactory.getCurrentSession();
		TrackingTask trackingTask=session.get(TrackingTask.class, trackingtask_id);
		trackingTask.setIsActive(false);
		trackingTask.setModifiedBy(userid.toString());
		trackingTask.setModifiedDate(new Date());
		session.update(trackingTask);
		
		
	}

	@Override
	public List<TrackingTask> getAllTrackingTask() 
	{
		System.out.println(" getAllTrackingTask ");
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(TrackingTask.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.addOrder(Order.desc("dateOfRelease"));
		@SuppressWarnings("unchecked")
		List<TrackingTask> trackingTasks=crit.list();
		System.out.println(trackingTasks);
//		for(TrackingTask trackingTask:trackingTasks) {
//		Hibernate.initialize(trackingTask.getUploadfiles());
//		}
		return trackingTasks; 
	}

	@Override
	public TrackingTask getTrackingTaskById(Integer trackingtask_id) 
	{
		System.out.println("dao "+trackingtask_id);
		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("from TrackingTask t where t.id=:id and t.isActive=1");
		query.setInteger("id", trackingtask_id);
//		Query query=session.createQuery("from Screen s where s.project.id = :projectId and s.module.id = :moduleId and s.screenName=:screenname and s.isActive=1");
	
		TrackingTask trackingTask=(TrackingTask) query.uniqueResult();
		
//		Criteria crit=session.createCriteria(TrackingTask.class);
//		Criterion cn=Restrictions.eq("id", trackingtask_id);
//	    Criterion cn2=Restrictions.eq("isActive", true);
//		crit.add(cn);
//		crit.add(cn2);
//		TrackingTask trackingTask= (TrackingTask) crit.uniqueResult();
		System.out.println(trackingTask);
//		Hibernate.initialize(trackingTask.getUploadfiles());
		return trackingTask;
	}

	@Override
	public TrackingTask updateTrackingTask(Integer trackingtask_id, TrackingTask trackingtask) {
		System.out.println("trackingtask dao");
		System.out.println(trackingtask);
		Session session=sessionFactory.getCurrentSession();
		TrackingTask trackingtask2=session.get(TrackingTask.class, trackingtask_id);
		trackingtask2.setId(trackingtask_id);
		trackingtask2.setModifiedBy(trackingtask.getModifiedBy());
		trackingtask2.setModifiedDate(new Date());
//		trackingtask2.setProject(trackingtask.getProject());
//		trackingtask2.setModule(trackingtask.getModule());
		trackingtask2.setScreen(trackingtask.getScreen());
		trackingtask2.setTasktype(trackingtask.getTasktype());
		trackingtask2.setUser(trackingtask.getUser());
		trackingtask2.setTaskName(trackingtask.getTaskName());
		trackingtask2.setTaskDesc(trackingtask.getTaskDesc());
		trackingtask2.setSummary(trackingtask.getSummary());
		trackingtask2.setDateOfRelease(trackingtask.getDateOfRelease());
		Set<TrackingTaskUploadFile> uploadfiles2=new HashSet<TrackingTaskUploadFile>();
		System.out.println("uploadfiles2");
		System.out.println(uploadfiles2);
		Set<TrackingTaskUploadFile> uploadfiles=trackingtask.getUploadfiles();
//		System.out.println(uploadfiles.isEmpty());
//		System.out.println("uploadfiles");
//		System.out.println(uploadfiles);
//		System.out.println(uploadfiles.isEmpty());
		if(uploadfiles != null) {
			for(TrackingTaskUploadFile upldfile :uploadfiles)
			{
				TrackingTaskUploadFile uploadfile=new TrackingTaskUploadFile();
				uploadfile.setCreatedBy(upldfile.getCreatedBy());
				uploadfile.setCreatedDate(new Date());
				uploadfile.setOriginalfileName(upldfile.getOriginalfileName());
				uploadfile.setFileName(upldfile.getFileName());
				uploadfile.setFiledownloadUrl(upldfile.getFiledownloadUrl());
				uploadfile.setIsActive(true);
				uploadfiles2.add(uploadfile);
			}
			
		}
		
		
		Set<TrackingTaskUploadFile> uploadfiles3=trackingtask2.getUploadfiles();
		System.out.println("uploadfiles3");
		System.out.println(uploadfiles3);
		for(TrackingTaskUploadFile upldfile :uploadfiles3)
		{
			TrackingTaskUploadFile uploadfile=new TrackingTaskUploadFile();
			uploadfile.setModifiedBy(upldfile.getCreatedBy());
			uploadfile.setModifiedDate(new Date());
			uploadfile.setOriginalfileName(upldfile.getOriginalfileName());
			uploadfile.setFileName(upldfile.getFileName());
			uploadfile.setFiledownloadUrl(upldfile.getFiledownloadUrl());
			uploadfile.setIsActive(true);
			uploadfiles2.add(uploadfile);
		}
		
		trackingtask2.setUploadfiles(uploadfiles2);
		System.out.println(" dao end ");
		System.out.println(trackingtask2);
		session.update(trackingtask2);
		return trackingtask2;
	}

	@Override
	public boolean duplicateChecking(String taskname,Integer screenid, HttpServletRequest req) {
		boolean isDulicateRecord=false;
		try {
			Session session=sessionFactory.getCurrentSession();	
			Query query=session.createQuery("from TrackingTask t where t.screen.id=:screenId and t.taskName=:taskName and t.isActive=1");
			query.setString("screenId", screenid.toString());
			query.setString("taskName", taskname);
			TrackingTask trackingtask2=(TrackingTask) query.uniqueResult();
			if(trackingtask2!=null && trackingtask2.getIsActive()){
				isDulicateRecord=true;
			}
			else if(trackingtask2!=null && !trackingtask2.getIsActive()) {
				req.getSession().setAttribute("reactivateId",trackingtask2.getId());
					throw new ReActivateExteption(""+trackingtask2.getId());	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingTask> getTrackingTaskName(String taskname) {
		System.out.println(taskname);
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from TrackingTask t where t.taskName like :projectname");
		query.setString("projectname", taskname + "%");
		List<TrackingTask> trackingtasks=(List<TrackingTask>) query.list();
		System.out.println(trackingtasks);
		return trackingtasks;
	}

	@Override
	public List<TrackingTask> getTrackingTaskScreenName(String screenName) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from TrackingTask t where t.screen.screenName like :screenName");
		query.setString("screenName", screenName + "%");
		List<TrackingTask> trackingtasks=(List<TrackingTask>) query.list();
		System.out.println(trackingtasks);
		return trackingtasks;
	}

}
