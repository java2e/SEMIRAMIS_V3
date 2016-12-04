package semiramis.tanimlar.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import semiramis.operasyon.model.ComboItem;
import semiramis.tanimlar.dao.IcraMudurlukleriDAO;

@ManagedBean(name="icraMudurlukleriBean")
@SessionScoped
public class IcraMudurlukleriBean {
	
	private List<ComboItem> listIcraMudurlukleri;
	
	public IcraMudurlukleriDAO dao;
	
	public IcraMudurlukleriBean() {
		
		
		dao=new IcraMudurlukleriDAO();
		
		listIcraMudurlukleri=dao.getList();
		
		
		// TODO Auto-generated constructor stub
	}

	public List<ComboItem> getListIcraMudurlukleri() {
		return listIcraMudurlukleri;
	}

	public void setListIcraMudurlukleri(List<ComboItem> listIcraMudurlukleri) {
		this.listIcraMudurlukleri = listIcraMudurlukleri;
	}
	
	

}
