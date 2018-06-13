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
	 * 根据页码找到对应的课�?
	 * @return
	 */
	public String findCourseByPage(){
		//获取页面传�?�过来的当前页码�?
		if(pageCode==0){
			pageCode = 1;
		}
		//给pageSize,每页的记录数赋�??
		int pageSize = 5;
		
		PageBean<Course> pb = courseService.findCourseByPage(pageCode,pageSize);
		if(pb!=null){
			pb.setUrl("findCourseByPage.action?");
		}
		//存入request域中
		ServletActionContext.getRequest().setAttribute("pb", pb);
		return  "success";
	}
	
	/**
	 * 添加课程
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
			ServletActionContext.getResponse().getWriter().print(success);//向浏览器响应是否成功的状态码
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 得到指定的课�?
	 * Ajax请求该方�?
	 * 向浏览器返回该课程的json对象
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
	 * 修改指定课程
	 * @return
	 */
	public String updateCourse(){
		Course course = new Course();
		course.setCourseId(courseId);
		Course updateCourse = courseService.getCourseById(course);//查出�?要修改的课程对象
		updateCourse.setCourseName(courseName);
		int success = 0;
		Course newCourse = courseService.updateCourse(updateCourse);//修改该课程对�?
		if(newCourse!=null){
			success = 1;
			//由于是转发并且js页面刷新,�?以无�?重查
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
	 * 删除课程
	 * @return
	 */
	public String deleteCourse(){
		Course course = new Course();
		course.setCourseId(courseId);
		boolean deleteCourse = courseService.deleteCourse(course);
		int success = 0;
		if(deleteCourse){
			success = 1;
			//由于是转发并且js页面刷新,�?以无�?重查
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
		//获取页面传�?�过来的当前页码�?
		if(pageCode==0){
			pageCode = 1;
		}
		//给pageSize,每页的记录数赋�??
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
			if(obj instanceof Set||name.equals("subjects")){//过滤掉集�?
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
