package semiramis.operasyon.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import pelops.controller.AktifBean;
import semiramis.operasyon.dao.TebligatDAO;
import semiramis.operasyon.model.Tebligat;

@ManagedBean(name="tebligatBean",eager=true)
@RequestScoped
public class TebligatBean
{
	
	
	private Tebligat tebligat;
	
	public TebligatDAO dao;
	
	public int kaydet=1;
	
	
	public TebligatBean() {
		// TODO Auto-generated constructor stub
	
	tebligat=new Tebligat();
	

	 dao=new TebligatDAO();
	
	tebligat=dao.getT(AktifBean.icraDosyaID);
	
	if(tebligat!=null)
		kaydet=2;
	else
		tebligat=new Tebligat();
	
	
	tebligat.setIcraDosyaId(AktifBean.icraDosyaID);
	tebligat.setIcraDosyaNo(AktifBean.icraDosyaNo);
	tebligat.setBorcluId(AktifBean.borcluId);
	
	
	
	}

	
	public void kaydet()
	{
		
		if(kaydet==2)
			dao.guncelleme(tebligat);
		else
			dao.kaydet(tebligat);
			
		
	}

	public Tebligat getTebligat() {
		return tebligat;
	}


	public void setTebligat(Tebligat tebligat) {
		this.tebligat = tebligat;
	}
	
	
	

}
