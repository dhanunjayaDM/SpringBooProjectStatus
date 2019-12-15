package com.lara.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.lara.model.TrackingTask;

@Repository
public interface TrackingTaskDao {

	void saveTrackingTask(TrackingTask trackingTask);

	void deleteTrackingTask(Integer trackingtask_id, Integer userid);

	List<TrackingTask> getAllTrackingTask();

	TrackingTask getTrackingTaskById(Integer trackingtask_id);

	TrackingTask updateTrackingTask(Integer trackingtask_id, TrackingTask trackingtask);

	boolean duplicateChecking(String taskname, Integer screentId, HttpServletRequest req);

	 List<TrackingTask> getTrackingTaskName(String taskname);

	List<TrackingTask> getTrackingTaskScreenName(String screenName);

}
