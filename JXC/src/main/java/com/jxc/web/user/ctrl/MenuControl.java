package com.jxc.web.user.ctrl;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 主菜单栏控制器，根据登陆人员身份显示左侧菜单栏
 * @author Sun
 *
 */
@Controller
public class MenuControl {
	/**
	 * 0000为admin,0001为普通员工，0002为管理员
	 * @param gradetype 身份类型
	 * @param id 用户ID
	 * @return
	 */
	@RequestMapping("/menu.jhtml")
	public String menu(String gradetype,String id){
		if("0000".equals(gradetype)){
			//admin
			return "menu/menu_admin";
		}else if("0001".equals(gradetype)){
			//普通员工
			return "menu/menu_normal";
		}else if("0002".equals(gradetype)){
			return "menu/menu_normal";
		}
		return "login";
	}
	
	@RequestMapping(value="firstofall",method=RequestMethod.POST)
	public @ResponseBody String checkAvaliable(HttpServletRequest request){
		ServletContext application = request.getServletContext();

		HttpSession session = request.getSession();
		Map<String,Object>  map = (Map) application.getAttribute("userMap");
		
		System.out.println(session.isNew());
		
		if (null == session.getAttribute("username")) {
				return "0";
		}
		return "1";
	}
}
