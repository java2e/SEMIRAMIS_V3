package semiramis.kasa.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pelops.controller.AktifBean;
import pelops.model.Hesap;
import semiramis.kasa.model.Reddiyat;
import semiramis.kasa.model.Tahsilat;
import semiramis.operasyon.model.ChronologyIdentifier;

public class TahsilatBean 
{
	
	private Tahsilat tahsilat;
	
	public TahsilatBean() {
		// TODO Auto-generated constructor stub
	
	tahsilat=new Tahsilat();
	
	}
	
	

	public Tahsilat getTahsilat() {
		return tahsilat;
	}

	public void setTahsilat(Tahsilat tahsilat) {
		this.tahsilat = tahsilat;
	}
	
	
	

}
