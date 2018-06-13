package com.onlinetest.action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;

import com.onlinetest.domain.Course;
import com.onlinetest.domain.PageBean;
import com.onlinetest.service.CourseService;
import com.opensymphony.xwork2.ActionSupport;

public class CourseManageAction extends ActionSupport{

	
	private CourseService courseService;

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	
	private int pageCode;
	private int courseId;
	
	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	private String courseName;
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}



	/**
	 * æ ¹æ®é¡µç æ‰¾åˆ°å¯¹åº”çš„è¯¾ç¨?
	 * @return
	 */
	public String findCourseByPage(){
		
		if(pageCode==0){
			pageCode = 1;
		}
		
		int pageSize = 5;
		
		PageBean<Course> pb = courseService.findCourseByPage(pageCode,pageSize);
		if(pb!=null){
			pb.setUrl("findCourseByPage.action?");
		}
		
		ServletActionContext.getRequest().setAttribute("pb", pb);
		return  "success";
	}
	
	/**
	 * æ·»åŠ è¯¾ç¨‹
	 * @return
	 */
	public String addCourse(){
		Course course = new Course();
		course.setCourseName(courseName);
		Course course2 = courseService.getCourseByName(course);
		int success = 0;
		if(course2!=null){
			success = -1;
		}else{
			boolean b = courseService.addCourse(course);
			if(b){
				success = 1;
			}else{
				success = 0;
			
			}
		}
		try {
			ServletActionContext.getResponse().getWriter().print(success);//å‘æµè§ˆå™¨å“åº”æ˜¯å¦æˆåŠŸçš„çŠ¶æ€ç 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * å¾—åˆ°æŒ‡å®šçš„è¯¾ç¨?
	 * Ajaxè¯·æ±‚è¯¥æ–¹æ³?
	 * å‘æµè§ˆå™¨è¿”å›žè¯¥è¯¾ç¨‹çš„jsonå¯¹è±¡
	 * @return
	 */
	public String getCourse(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		Course course = new Course();
		course.setCourseId(courseId);
		Course newCourse = courseService.getCourseById(course);
		JSONObject jsonObject = JSONObject.fromObject(newCourse);
		try {
			response.getWriter().print(jsonObject);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * ä¿®æ”¹æŒ‡å®šè¯¾ç¨‹
	 * @return
	 */
	public String updateCourse(){
		Course course = new Course();
		course.setCourseId(courseId);
		Course updateCourse = courseService.getCourseById(course);
		updateCourse.setCourseName(courseName);
		int success = 0;
		Course newCourse = courseService.updateCourse(updateCourse);
		if(newCourse!=null){
			success = 1;
			
		}
		try {
			ServletActionContext.getResponse().getWriter().print(success);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
	
	/**
	 * åˆ é™¤è¯¾ç¨‹
	 * @return
	 */
	public String deleteCourse(){
		Course course = new Course();
		course.setCourseId(courseId);
		boolean deleteCourse = courseService.deleteCourse(course);
		int success = 0;
		if(deleteCourse){
			success = 1;
			
		}
		try {
			ServletActionContext.getResponse().getWriter().print(success);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		
		return null;
	}
	
	
	public String queryCourse(){
		
		if(pageCode==0){
			pageCode = 1;
		}
		
		int pageSize = 5;
		PageBean<Course> pb = null;
		if("".equals(courseName.trim())){
			pb = courseService.findCourseByPage(pageCode,pageSize);
		}else{
			Course course = new Course();
			course.setCourseName(courseName);
			pb = courseService.queryCourse(course,pageCode,pageSize);
			
		}
		if(pb!=null){
			pb.setUrl("queryCourse.action?courseName="+courseName+"&");
		}

		ServletActionContext.getRequest().setAttribute("pb", pb);
		return "success";
	}
	
	
	
	public String getAllCourses(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		List<Course> allCourses = courseService.getAllCourses();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
		    public boolean apply(Object obj, String name, Object value) {
			if(obj instanceof Set||name.equals("subjects")){//è¿‡æ»¤æŽ‰é›†å?
				return true;
			}else{
				return false;
			}
		   }
		});
		
		
		String json = JSONArray.fromObject(allCourses,jsonConfig).toString();//List------->JSONArray
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
}
