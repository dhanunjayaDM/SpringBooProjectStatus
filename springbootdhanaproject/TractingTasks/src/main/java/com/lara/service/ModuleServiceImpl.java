package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.ModuleDao;
import com.lara.model.Module;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService 
{
	@Autowired
	private ModuleDao moduleDao;

	@Override
	public void saveModule(Module module)
	{
		moduleDao.saveModule(module);
	}

	@Override
	public List<Module> getAllModules() 
	{
		return moduleDao.getAllModules();
	}

	@Override
	public Module getModuleById(Integer module_id)
	{
		return moduleDao.getModuleById(module_id) ;
	}

	@Override
	public Module updateModule(Integer module_id, Module module) 
	{
		return moduleDao.updateModule(module_id,module) ;
	}

	@Override
	public void deleteModule(Integer module_id,Integer userid) 
	{
		moduleDao.deleteModule(module_id, userid);
	}

	

	@Override
	public boolean duplicateChecking(Module module, HttpServletRequest req) 
	{
		return moduleDao.duplicateChecking(module,req);
	}

	@Override
	public void reactivateRecord(Integer module_id,Integer userid ) 
	{
		moduleDao.reactivateRecord(module_id,userid);
	}

	@Override
	public List<Module> getAllModuleByProjectId(Integer project_id) 
	{
		return moduleDao.getAllModuleByProjectId(project_id);
	}

}
