package com.lara.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lara.exception.ReActivateExteption;

import com.lara.model.TaskType;

@Repository
public class TaskTypeDaoImpl implements TaskTypeDao 
{
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void saveTaskType(TaskType taskType) {
		Session session=sessionFactory.getCurrentSession();
		TaskType taskType2=new TaskType();
		taskType2.setCreatedDate(new Date());
		taskType2.setCreatedBy(taskType.getCreatedBy());
		taskType2.setIsActive(true);
		taskType2.setTaskType(taskType.getTaskType());
		session.save(taskType2);
	}

	@Override
	public List<TaskType> getAllTaskType() {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(TaskType.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		@SuppressWarnings("unchecked")
		List<TaskType> taskTypes=crit.list();
		return taskTypes;
	}

	@Override
	public TaskType getTaskTypeById(Integer tasktype_id) {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(TaskType.class);
		Criterion cn=Restrictions.eq("id", tasktype_id);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		TaskType tasktype= (TaskType) crit.uniqueResult();
		return tasktype;
	}

	@Override
	public TaskType updateTaskType(Integer tasktype_id, TaskType taskType) {
		Session session=sessionFactory.getCurrentSession();
		TaskType taskType2=session.get(TaskType.class, tasktype_id);
		taskType2.setId(taskType.getId());
		taskType2.setIsActive(true);
		taskType2.setModifiedDate(new Date());
		taskType2.setModifiedBy(taskType.getModifiedBy());
		taskType2.setTaskType(taskType.getTaskType());
		session.update(taskType2);
		return taskType2;
	}

	@Override
	public void deleteTaskType(Integer tasktype_id, Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		TaskType taskType2=session.get(TaskType.class, tasktype_id);
		taskType2.setIsActive(false);
		taskType2.setModifiedBy(userid.toString());
		taskType2.setModifiedDate(new Date());
		session.update(taskType2);

	}

	@Override
	public boolean duplicateChecking(TaskType taskType, HttpServletRequest req) {
		boolean isDulicateRecord=false;
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from TaskType t where t.taskType=:taskType");
			query.setString("taskType", taskType.getTaskType());
			TaskType taskType2=(TaskType) query.uniqueResult();
			if(taskType2!=null && taskType2.getIsActive()){
				isDulicateRecord=true;
			}
			else if(taskType2!=null && !taskType2.getIsActive()) {

				req.getSession().setAttribute("reactivateId",taskType2.getId());
				
					throw new ReActivateExteption(""+taskType2.getId());	
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
	public void reactivateRecord(Integer tasktype_id, Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		TaskType taskType=session.get(TaskType.class, tasktype_id);
		taskType.setIsActive(true);
		taskType.setModifiedBy(userid.toString());
		taskType.setModifiedDate(new Date());
		session.update(taskType);	
		
	}

}
