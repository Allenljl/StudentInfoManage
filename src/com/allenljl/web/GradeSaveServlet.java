package com.allenljl.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.allenljl.dao.GradeDao;
import com.allenljl.model.Grade;
import com.allenljl.model.PageBean;
import com.allenljl.util.DbUtil;
import com.allenljl.util.JsonUtil;
import com.allenljl.util.ResponseUtil;
import com.allenljl.util.StringUtil;

public class GradeSaveServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String gradeName=request.getParameter("gradeName");
		String gradeDesc=request.getParameter("gradeDesc");
		String id=request.getParameter("id");
		Grade grade=new Grade(gradeName,gradeDesc);
		if(StringUtil.isNotEmpty(id)){
			grade.setId(Integer.parseInt(id));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums=gradeDao.gradeModify(con, grade);
			}else{
				saveNums=gradeDao.gradeAdd(con, grade);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
}
