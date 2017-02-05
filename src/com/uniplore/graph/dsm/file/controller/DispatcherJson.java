package com.uniplore.graph.dsm.file.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class DispatcherJson {

	@RequestMapping(value="/JsonTypeDataAnalysis",method=RequestMethod.GET)
	public String dispatcherJson(HttpServletRequest request) throws Exception{
		/*request.setCharacterEncoding("UTF-8");*/
		String id = request.getParameter("id");
		String fileName = request.getParameter("fileName");
		System.out.println("接受到的文件的唯一识别id为"+id+"--"+"接受到的文件名为"+fileName);  //判断是否成功的接收到文件参数信息
		return "/dsm/file/json/json";
	}
}
