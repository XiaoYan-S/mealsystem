package com.diancan.controller.menumanage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.diancan.base.BaseController;
import com.diancan.service.menumanage.MenuMManager;
import com.diancan.util.PageData;

@Controller
@RequestMapping(value = "/menumanage")
// 窄化，方便模块开发
public class MenuController extends BaseController{
	
	@Resource(name = "menumanagerService")
	private MenuMManager menumanagerService;

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
		List<PageData>	varList = menumanagerService.list(pd);	
		mv.setViewName("menumanage/menu_list");
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
		PageData var=menumanagerService.findById(pd);
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
		map.put("m_id", (Integer) var.get("m_id"));
		map.put("m_image",var.getString("m_image"));
		return map;
	}
	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveEdit")
	public ModelAndView saveEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		menumanagerService.edit(pd);
		List<PageData>	varList = menumanagerService.list(pd);	
		mv.setViewName("menumanage/menu_list");
		mv.addObject("varList", varList);      //值
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**新增
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	public ModelAndView add(String m_name0,String m_piece0,String m_describe0,String m_sale0,String m_data0,String m_classify0,MultipartFile pictureFile,String m_add_user0) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(pictureFile!=null)
		{
			String pic_path="D:\\temp\\";
			//原始文件名称
		String pictureFile_name =  pictureFile.getOriginalFilename();
		//新文件名称
		String newFileName = UUID.randomUUID().toString()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
		
		//上传图片
		File uploadPic = new File(pic_path+newFileName);
		//将内存中的数据写入磁盘
		pictureFile.transferTo(uploadPic);
		
		pd.put("m_image", newFileName);
}
		pd.put("m_name", m_name0);
		pd.put("m_piece", m_piece0);
		pd.put("m_describe", m_describe0);
		pd.put("m_sale", m_sale0);
		pd.put("m_data", m_data0);
		pd.put("m_classify", m_classify0);
		pd.put("m_add_user", m_add_user0);
		
		//获得系统时间
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		pd.put("m_add_time", time);
		//获取添加人
		menumanagerService.save(pd);
		List<PageData>	varList = menumanagerService.list(pd);	
		mv.setViewName("menumanage/menu_list");
		mv.addObject("varList", varList);      //值
		mv.addObject("pd", pd);
		return mv;
	}


	/**判断输入的是否数字
     * @return
     */
    @RequestMapping(value="/isNumber")
    @ResponseBody
    public Object isNumber(){
        Map<String,String> map = new HashMap<String,String>();
        String errInfo = "isExist";
        PageData pd = new PageData();
        pd = this.getPageData();
        String number=pd.getString("number");                         //全宗号
        try{
            //判断是否数字
            Integer.parseInt(number);
        } catch(Exception e){
           // logger.error(e.toString(), e);
            errInfo="fail";
        }
        map.put("result", errInfo);				                      //返回结果
        map.put("resultValue","");
        return map;
    }

    /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/delAll")
	public Object delAll() throws Exception{
		//ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取所有要删除的id
		String ids[]=pd.getString("DATA_IDS").split(",");
		for(int i=0;i<ids.length;i++){
			pd.put("id", ids[i]);
			menumanagerService.delete(pd);
		}
		//List<PageData>	varList = menumanagerService.list(pd);	
		//mv.setViewName("menumanage/menu_list");
		//mv.addObject("varList", varList);      //值
		//mv.addObject("pd", pd);
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

}
