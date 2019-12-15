package com.lara.dao;




import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lara.exception.ReActivateExteption;
import com.lara.model.Users;

@Repository
public class UserDaoImpl implements UserDao
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Users getUserByName(String username) {
		System.out.println("dao");
		System.out.println(username);
		Session session=sessionFactory.getCurrentSession();
		SQLQuery qry=session.createSQLQuery("select * from users where user_name=:username").addEntity(Users.class);
		qry.setParameter("username", username);
		Users user=(Users) qry.uniqueResult();
		System.out.println(user);
		return user;
	}

	@Override
	public Users getUserById(Integer id) {
		System.out.println("dao");
		System.out.println(id);
		Session session=sessionFactory.getCurrentSession();
		SQLQuery qry=session.createSQLQuery("select * from users where id=:id").addEntity(Users.class);
		qry.setParameter("id", id);
		Users user=(Users) qry.uniqueResult();
		System.out.println(user);
		return user;
	}

	@Override
	public List<Users> getAllUsers() {
		System.out.println("user list dao");
		Session session=sessionFactory.getCurrentSession();
		Criteria crit=session.createCriteria(Users.class);
		Criterion cn=Restrictions.eq("isActive", true);
		crit.add(cn);
		@SuppressWarnings("unchecked")
		List<Users> users=crit.list();
		System.out.println(users);
		return users;
		
		
	}

	@Override
	public void saveUser(Users user) {
		Session session=sessionFactory.getCurrentSession();
		Users user2=new Users();
		
		System.out.println(user.getRoles());
		
//		user2.setId(user.getId());
		user2.setIsActive(true);
		user2.setCreatedDate(new Date());
		user2.setCreatedBy(user.getCreatedBy());
		user2.setUsername(user.getUsername());
		user2.setPassword(user.getPassword());
		user2.setRoles(user.getRoles());
		user2.setPermissions(user.getPermissions());
		session.save(user2);
	}

	@Override
	public boolean duplicateChecking(Users user, HttpServletRequest req) {
		boolean isDulicateRecord=false;
		//HttpServletRequest request = null;
		//HttpSession session12=null;
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Users u where u.username=:username");
			query.setString("username", user.getUsername());
			//query.setString("description", project.getDescription());
			Users user1=(Users) query.uniqueResult();
			if(user1!=null && user1.getIsActive()){
				isDulicateRecord=true;
			}
			else if(user1!=null && !user1.getIsActive()) {
//				   pro.getId();
				req.getSession().setAttribute("reactivateId",user1.getId());
				  // session12.setAttribute("activateId",pro.getId());
					throw new ReActivateExteption(""+user1.getId());	
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
	public Users getUserByUsername(String username) {
		System.out.println("dao");
		System.out.println(username);
		Session session=sessionFactory.getCurrentSession();
		SQLQuery qry=session.createSQLQuery("select * from users where user_name=:username").addEntity(Users.class);
		qry.setParameter("username", username);
		Users user=(Users) qry.uniqueResult();
		System.out.println("users roles");
		System.out.println(user.getRoles());
		return user;
	}

	@Override
	public void deleteUser(Integer user_id, Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Users user2=session.get(Users.class, user_id);
		user2.setIsActive(false);
		user2.setModifiedBy(userid.toString());
		user2.setModifiedDate(new Date());
		session.update(user2);
	}

	@Override
	public void reactivateRecord(Integer user_id, Integer userid) {
		Session session=sessionFactory.getCurrentSession();
		Users user2=session.get(Users.class, user_id);
		user2.setIsActive(true);
		user2.setModifiedBy(userid.toString());
		user2.setModifiedDate(new Date());
		session.update(user2);	
		
	}
		
	


}
