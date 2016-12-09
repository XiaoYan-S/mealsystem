package com.diancan.controller.choicemeal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.diancan.base.BaseController;
import com.diancan.entity.Page;
import com.diancan.service.choicemeal.ChoiceMealManager;
import com.diancan.service.menumanage.MenuMManager;
import com.diancan.util.AppUtil;
import com.diancan.util.PageData;

@Controller
@RequestMapping(value = "/choicemeal")
// 窄化，方便模块开发
public class ChoiceMealController extends BaseController{
	
	@Resource(name = "choicemealService")
	private ChoiceMealManager choicemealService;

	@RequestMapping("/list")
	public ModelAndView  list() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");	
		pd.put("keywords",keywords );
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords);
		}
		List<PageData> classifyList=choicemealService.findclassify(pd);
		List<PageData>	varList = choicemealService.list(pd);	
		mv.setViewName("choicemeal/meal_list");
		mv.addObject("varList", varList);      //值
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	@RequestMapping("/goAdd")
	@ResponseBody
	public Object  goAdd() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取选中的ids
		pd.put("id", pd.getString("DATA_IDS"));
		PageData var=choicemealService.findById(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		
		//SimpleDateFormat  formatter  =  new   SimpleDateFormat( "yyyy-MM-dd ");
		//Date date = (Date) var.get("m_add_time");
		//Date   date   =   formatter.parse(var.getString("m_add_time"));
		map.put("m_data", var.getString("m_data"));
		map.put("m_describe", var.getString("m_describe"));
		map.put("m_sale", (Integer) var.get("m_sale"));
		map.put("m_name", var.getString("m_name"));
		map.put("m_add_user", var.getString("m_add_user"));
		map.put("m_piece", (Double) var.get("m_piece"));
		//map.put("m_add_time",date);
		
		map.put("m_id", (Integer) var.get("m_id"));
		map.put("m_image",var.getString("m_image"));
		return map;
	}
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("o_ispay", 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=df.format(new Date());
		pd.put("o_time", time);
		choicemealService.addmeal(pd);	
		mv.setViewName("choicemeal/meal_list");
		mv.addObject("message","query");
		mv.addObject("pd", pd);
		return mv;
	}
}
