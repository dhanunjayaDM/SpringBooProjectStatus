package com.lara.dao;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.lara.model.Screen;

@Repository
public interface ScreenDao {

    void saveScreen(Screen screen);

	boolean duplicateChecking(String screenname, Integer projectId, Integer moduleId, HttpServletRequest req);

	Screen getScreenById(Integer screen_id);

	List<Screen> getAllScreen();

	void deleteScreen(Integer screen_id, Integer userid);

	List<Screen> getAllScreenByModuleId(Integer module_id);

	Screen updateScreen(Integer screen_id, Screen screen);

//	String getOriginalFileName(String filename);



}
