package semimis.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyGenelArama extends LazyDataModel<GenelArama>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GenelArama> datasource;
    
    public LazyGenelArama(List<GenelArama> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public GenelArama getRowData(String rowKey) {
        
    	int rowK=Integer.valueOf(rowKey);
    	
    	for(GenelArama genelArama : datasource) 
        {
            if(genelArama.getId()==rowK)
                return genelArama;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(GenelArama genelArama) {
        return genelArama.getId();
    }
 
    @Override
    public List<GenelArama> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<GenelArama> data = new ArrayList<GenelArama>();
 
        //filter
        for(GenelArama genelArama : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(genelArama.getClass().getField(filterProperty).get(genelArama));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(genelArama);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}