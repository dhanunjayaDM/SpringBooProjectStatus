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
import com.lara.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveRole(Role role) {
		System.out.println("r dao");
		System.out.println(role);
		Session session=sessionFactory.getCurrentSession();
		Role role2=new Role();
		role2.setCreatedDate(new Date());
		role2.setIsActive(true);
		role2.setUserRole(role.getUserRole());
		role2.setIsAdmin(role.getIsAdmin());
		session.save(role2);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Role> getAllRole() {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Role.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		@SuppressWarnings("unchecked")
		List<Role> roles=crit.list();
		return roles;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Role getRoleById(Integer role_id) {
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Role.class);
		Criterion cn=Restrictions.eq("id", role_id);
		Criterion cn2=Restrictions.eq("isActive", true);
		crit.add(cn);
		crit.add(cn2);
		Role role= (Role) crit.uniqueResult();
		return role;
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean duplicateChecking(Role role, HttpServletRequest req){
		boolean isDulicateRecord=false;
		//HttpServletRequest request = null;
		//HttpSession session12=null;
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Role r where p.userRole=:userrole");
			query.setString("userrole", role.getUserRole());
			//query.setString("description", project.getDescription());
			Role pro=(Role) query.uniqueResult();
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

}
