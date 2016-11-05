package semiramis.yonetici.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import semimis.utils.GenelArama;
import semiramis.yonetici.dao.IcraDosyaAtamaDAO;


@ManagedBean(name="icraDosyasiAtamaBean",eager=true)
@SessionScoped
public class IcraDosyasiAtamaBean
{

	
	private int userId;
	
	List<GenelArama> selectedGenelAramalar;
	
	public IcraDosyasiAtamaBean() {
		// TODO Auto-generated constructor stub
		
		selectedGenelAramalar=new ArrayList<GenelArama>();
	}
	
	
	public void dosyaAta()
	{
		IcraDosyaAtamaDAO dao=new IcraDosyaAtamaDAO();
		
		for (int i = 0; i < selectedGenelAramalar.size(); i++) {
			
			dao.guncelleme(selectedGenelAramalar.get(i), userId);
			
		}
		
		
	}
	
	

	public List<GenelArama> getSelectedGenelAramalar() {
		return selectedGenelAramalar;
	}

	public void setSelectedGenelAramalar(List<GenelArama> selectedGenelAramalar) {
		this.selectedGenelAramalar = selectedGenelAramalar;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
	
	
	
	
}
