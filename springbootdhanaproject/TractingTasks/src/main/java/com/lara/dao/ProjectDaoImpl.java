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
import com.lara.model.Project;

@Repository
public class ProjectDaoImpl implements ProjectDao 
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveProject(Project project) {
		System.out.println("p dao");
		System.out.println(project);
		Session session=sessionFactory.getCurrentSession();
		Project project2=new Project();
		project2.setCreatedDate(new Date());
		project2.setIsActive(true);
		project2.setProjectName(project.getProjectName());
		project2.setDescription(project.getDescription());
		session.save(project2);
	}

	
	@Override
	public List<Project> getAllProject() {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Project.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		@SuppressWarnings("unchecked")
		List<Project> projects=crit.list();
		return projects;
	}

	@Override
	public Project getProjectById(Integer project_id) {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Project.class);
		Criterion cn=Restrictions.eq("id", project_id);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		Project project= (Project) crit.uniqueResult();
		return project;
	}

	@Override
	public Project updateProject(Integer project_id, Project project) {
		Session session=sessionFactory.getCurrentSession();
		Project project2=session.get(Project.class, project_id);
		project2.setId(project.getId());
		project2.setIsActive(true);
		project2.setModifiedDate(new Date());
		project2.setModifiedBy(project.getModifiedBy());
		project2.setProjectName(project.getProjectName());
		project2.setDescription(project.getDescription());
		session.update(project2);
		return project2;
	}

	@Override
	public void deleteProject(Integer project_id, Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Project project2=session.get(Project.class, project_id);
		project2.setIsActive(false);
		project2.setModifiedBy(userid.toString());
		project2.setModifiedDate(new Date());
		session.update(project2);
		
	}


	
	
	@Override
	public boolean duplicateChecking(Project project, HttpServletRequest req){
		boolean isDulicateRecord=false;
		//HttpServletRequest request = null;
		//HttpSession session12=null;
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Project p where p.projectName=:projectname");
			query.setString("projectname", project.getProjectName());
			//query.setString("description", project.getDescription());
			Project pro=(Project) query.uniqueResult();
			if(pro!=null && pro.getIsActive()){
				isDulicateRecord=true;
			}
			else if(pro!=null && !pro.getIsActive()) {
//				   pro.getId();
				req.getSession().setAttribute("reactivateId",pro.getId());
				  // session12.setAttribute("activateId",pro.getId());
					throw new ReActivateExteption(""+pro.getId());	
			}
			else {
				isDulicateRecord= false;
			}
			//return false;
		} catch (Exception  e) {
			if(e instanceof ReActivateExteption)
				throw new ReActivateExteption(e.getMessage());
			//throw new ReActivateExteption();
			
		}
		return isDulicateRecord;
	}


	@Override
	public void reActivateRecord(Integer project_id,Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Project project2=session.get(Project.class, project_id);
		project2.setIsActive(true);
		project2.setModifiedBy(userid.toString());
		project2.setModifiedDate(new Date());
		session.update(project2);	
	}


	


	


	

}
