package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lara.model.Module;


@Service
public interface ModuleService 
{

	void saveModule(Module module);

	List<Module> getAllModules();

	Module getModuleById(Integer module_id);

	Module updateModule(Integer module_id, Module module);

	void deleteModule(Integer module_id, Integer userid);

	boolean duplicateChecking(Module module, HttpServletRequest req);

	void reactivateRecord(Integer module_id, Integer userid);

	List<Module> getAllModuleByProjectId(Integer project_id);
	

}
