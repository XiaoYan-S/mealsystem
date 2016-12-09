package com.diancan.service.usermanage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.diancan.dao.DaoSupport;
import com.diancan.entity.Page;
import com.diancan.service.usermanage.UserManager;
import com.diancan.util.PageData;

@Service(value="usermanagerService")
public class UserManagerService implements UserManager{

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public void save(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.save("UserManageMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.delete("UserManageMapper.delete", pd);
	}

	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("UserManageMapper.edit", pd);
	}

	@Override
	public List<PageData> list(PageData page) throws Exception {
		return (List<PageData>) dao.findForList("UserManageMapper.listAll",page);
	}

	@Override
	public PageData findById(PageData page) throws Exception {
		return (PageData) dao.findForObject("UserManageMapper.findById", page);
	}

	

	
}
