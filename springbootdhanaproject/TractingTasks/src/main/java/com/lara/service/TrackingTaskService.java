package com.lara.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lara.model.TrackingTask;

@Service
public interface TrackingTaskService {

	void saveTrackingTask(TrackingTask trackingTask);

	void deleteTrackingTask(Integer trackingtask_id, Integer userid);

	List<TrackingTask> getAllTrackingTask();

	TrackingTask getTrackingTaskById(Integer trackingtask_id);

	String store(MultipartFile file);

	Resource loadFileAsResource(String fileName);

	TrackingTask updateTrackingTask(Integer trackingtask_id, TrackingTask trackingtask);

	boolean duplicateChecking(String taskname, Integer screenid, HttpServletRequest req);


	List<TrackingTask> getTrackingTaskName(String taskname);

	String store(InputStream inputStream, long size, String contentType, String originalFilename);

	List<TrackingTask> getTrackingTaskScreenName(String screenName);

}
