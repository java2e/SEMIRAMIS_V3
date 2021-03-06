package pelops.services;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pelops.dao.DuyuruDAO;
import pelops.model.Duyuru;

 
@ManagedBean(name = "duyuruService")
@SessionScoped
public class DuyuruService {

	
	private DuyuruDAO duyuruDAO;
	
	
     
    public DuyuruDAO getDuyuruDAO() {
		return duyuruDAO;
	}


	public void setDuyuruDAO(DuyuruDAO duyuruDAO) {
		this.duyuruDAO = duyuruDAO;
	}

	
	public DuyuruService(){
		duyuruDAO=new DuyuruDAO();
	}

	public List<Duyuru> createDuyurular(int size) {
        List<Duyuru> list = new ArrayList<Duyuru>();
        for(int i = 0 ; i < size ; i++) {
        	Duyuru duyuru =new Duyuru();
        	duyuru.setId(getRandomId());
        	duyuru.setAciklama("Duyuru("+(i+1)+") aciklaması yüklenecektir");
            list.add(duyuru);
        }
         
        return list;
    }
    
    
    public List<Duyuru> getDuyuruList() {
       return duyuruDAO.select();
    }
     

     
    private int getRandomId() {
        return (int) (Math.random() * 100);
    }
     
 
}
