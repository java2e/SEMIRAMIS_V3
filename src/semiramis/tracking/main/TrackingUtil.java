package semiramis.tracking.main;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import semiramis.tracking.classes.AftershipAPIException;
import semiramis.tracking.classes.ConnectionAPI;
import semiramis.tracking.classes.Tracking;

public class TrackingUtil {

	static ConnectionAPI connection = new ConnectionAPI("510bc78d-ae42-461d-863e-5b415b186cf8");

	public static Tracking getTrackingWithBarcode(String barcode) {

		Tracking tracking1 = new Tracking(barcode);
		tracking1.setSlug("ptt-posta");
		Tracking trackingToGet = null;
		try {
			connection.postTracking(tracking1);
			trackingToGet = connection.getTrackingByNumber(tracking1);

		} catch (AftershipAPIException e) {
			// TODO Auto-generated catch block
			try {
				JSONObject object = new JSONObject(e.getMessage());
				if(Integer.valueOf(object.getJSONObject("meta").get("code").toString())==4003){
					try {
						trackingToGet = connection.getTrackingByNumber(tracking1);
					} catch (AftershipAPIException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trackingToGet;
	}

}
