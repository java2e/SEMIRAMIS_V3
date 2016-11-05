package semimis.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "genelAramaBean")
@ViewScoped
public class GenelAramaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<GenelArama> liste;
	
	private LazyDataModel<GenelArama> lazyModel;

	
	private List<GenelArama> filteredListe;
	
	public GenelAramaDAO dao;

	private GenelArama genelArama;
	
	private GenelArama selectedGenelArama;

	public GenelAramaBean() {
		// TODO Auto-generated constructor stub
		
		
		dao = new GenelAramaDAO();

		genelArama=new GenelArama();

		liste = new ArrayList<GenelArama>();
		
		lazyModel =new LazyGenelArama(dao.getListe(genelArama));

		filteredListe=new ArrayList<GenelArama>();

	}

	public void getList() 
	{
		lazyModel =new LazyGenelArama(dao.getListe(genelArama));
		
		filteredListe=liste;
	}
	
	public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected "+((GenelArama) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        RequestContext.getCurrentInstance().closeDialog(((GenelArama) event.getObject()));
    }

	public void selectGenelAramaFromDialog(GenelArama genelArama) {
		
	}

	public List<GenelArama> getListe() {
		return liste;
	}

	public void setListe(List<GenelArama> liste) {
		this.liste = liste;
	}

	public GenelArama getGenelArama() {
		return genelArama;
	}

	public void setGenelArama(GenelArama genelArama) {
		this.genelArama = genelArama;
	}

	public List<GenelArama> getFilteredListe() {
		return filteredListe;
	}

	public void setFilteredListe(List<GenelArama> filteredListe) {
		this.filteredListe = filteredListe;
	}

	public GenelArama getSelectedGenelArama() {
		return selectedGenelArama;
	}

	public void setSelectedGenelArama(GenelArama selectedGenelArama) {
		this.selectedGenelArama = selectedGenelArama;
	}

	public LazyDataModel<GenelArama> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<GenelArama> lazyModel) {
		this.lazyModel = lazyModel;
	}
	
	
	
	

}
