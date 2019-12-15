package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lara.model.TaskType;

@Service
public interface TaskTypeService {

	void saveTaskType(TaskType taskType);

	List<TaskType> getAllTaskType();

	TaskType getTaskTypeById(Integer tasktype_id);

	TaskType updateTaskType(Integer tasktype_id, TaskType taskType);

	void deleteTaskType(Integer tasktype_id, Integer userid);

	boolean duplicateChecking(TaskType taskType, HttpServletRequest req);

	void reactivateRecord(Integer tasktype_id, Integer userid);

}
