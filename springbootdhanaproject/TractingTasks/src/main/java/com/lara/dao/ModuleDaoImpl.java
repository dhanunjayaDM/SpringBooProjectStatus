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
import com.lara.model.Module;



@Repository
public class ModuleDaoImpl implements ModuleDao
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveModule(Module module) {
		Session session=sessionFactory.getCurrentSession();
		Module module2=new Module();
		module2.setCreatedDate(new Date());
		module2.setCreatedBy(module.getCreatedBy());
		module2.setIsActive(true);
		module2.setModuleName(module.getModuleName());
		module2.setProject(module.getProject());
		session.save(module2);
	}

	
	@Override
	public List<Module> getAllModules() {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Module.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		@SuppressWarnings("unchecked")
		List<Module> modules=crit.list();
		return modules;
	}

	@Override
	public Module getModuleById(Integer module_id) {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Module.class);
		Criterion cn=Restrictions.eq("id", module_id);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		Module module=(Module) crit.uniqueResult();
		return module;
	}

	@Override
	public Module updateModule(Integer module_id, Module module) {
		Session session=sessionFactory.getCurrentSession();
		Module module2=session.get(Module.class, module_id);
		module2.setId(module.getId());
		module2.setIsActive(true);
		module2.setModifiedDate(new Date());
		module2.setModifiedBy(module.getModifiedBy());
		module2.setProject(module.getProject());
		module2.setModuleName(module.getModuleName());
		session.update(module2);
		return module2;
	}

	@Override
	public void deleteModule(Integer module_id,Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Module module=session.get(Module.class, module_id);
		module.setModifiedBy(userid.toString());
		module.setModifiedDate(new Date());
		module.setIsActive(false);
		session.update(module);
	}


	@Override
	public boolean duplicateChecking(Module module, HttpServletRequest req) {
		boolean isDulicateRecord=false;
		//HttpServletRequest request = null;
		//HttpSession session12=null;
		try {
			Session session=sessionFactory.getCurrentSession();
			
			Query query=session.createQuery("from Module m where m.project.id = :projectId and m.moduleName=:moduleName and m.isActive=1");
			query.setString("projectId",module.getProject().getId().toString());
			query.setString("moduleName", module.getModuleName());
			Module module2=(Module) query.uniqueResult();
			
			
			if(module2!=null && module2.getIsActive()){
				isDulicateRecord=true;
			}
			else if(module2!=null && !module2.getIsActive()) {
//				   pro.getId();
				req.getSession().setAttribute("reactivateId",module2.getId());
				  // session12.setAttribute("activateId",pro.getId());
					throw new ReActivateExteption(""+module2.getId());	
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
		//return false;
	}


	@Override
	public void reactivateRecord(Integer module_id,Integer userid ) {
		Session session=sessionFactory.getCurrentSession();
		Module module=session.get(Module.class, module_id);
		module.setIsActive(true);
		module.setModifiedBy(userid.toString());
		module.setModifiedDate(new Date());
		session.update(module);	
	}


	@Override
	public List<Module> getAllModuleByProjectId(Integer project_id) {
		System.out.println(project_id);
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Module.class);
		Criterion cn=Restrictions.eq("project.id", project_id);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		@SuppressWarnings("unchecked")
		List<Module> modules=crit.list();
		return modules;
	}

}
