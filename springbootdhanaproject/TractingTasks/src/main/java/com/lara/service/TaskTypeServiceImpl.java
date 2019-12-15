package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.TaskTypeDao;
import com.lara.model.TaskType;
@Service
@Transactional
public class TaskTypeServiceImpl implements TaskTypeService 
{
	@Autowired
	private TaskTypeDao taskTypeDao;

	@Override
	public void saveTaskType(TaskType taskType) {
		taskTypeDao.saveTaskType(taskType);
	}

	@Override
	public List<TaskType> getAllTaskType() {
		return taskTypeDao.getAllTaskType();
	}

	@Override
	public TaskType getTaskTypeById(Integer tasktype_id) {
		return taskTypeDao.getTaskTypeById(tasktype_id);
	}

	@Override
	public TaskType updateTaskType(Integer tasktype_id, TaskType taskType) {
		return taskTypeDao.updateTaskType(tasktype_id,taskType);
	}

	@Override
	public void deleteTaskType(Integer tasktype_id,Integer userid ) {
		taskTypeDao.deleteTaskType(tasktype_id,userid );
	}

	@Override
	public boolean duplicateChecking(TaskType taskType, HttpServletRequest req) {
		return taskTypeDao.duplicateChecking(taskType,req);
	}

	@Override
	public void reactivateRecord(Integer tasktype_id,Integer userid) {
		taskTypeDao.reactivateRecord(tasktype_id, userid);
		
	}

}
