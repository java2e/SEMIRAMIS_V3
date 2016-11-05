package semiramis.tracking.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import semiramis.tracking.classes.AftershipAPIException;
import semiramis.tracking.classes.ConnectionAPI;
import semiramis.tracking.classes.Courier;
import semiramis.tracking.classes.ParametersTracking;
import semiramis.tracking.classes.Tracking;

public class Test {

	public static void main(String[] args) throws AftershipAPIException, IOException, ParseException, JSONException {

		ConnectionAPI connection = new ConnectionAPI("510bc78d-ae42-461d-863e-5b415b186cf8");
		Tracking tracking1 = new Tracking("4270031279928");
		List<Courier> couriers = connection.getCouriers();
//System.out.println(couriers.get(0).getSlug().toString());
		//Then we can add information;
//		tracking1.setSlug(couriers.get(0).getSlug());
//		Tracking trackingPosted = connection.postTracking(tracking1);
//		
		
		Tracking trackingToGet = new Tracking("4270031279928");
		trackingToGet.setSlug("ptt-posta");
		Tracking tracking3 =TrackingUtil.getTrackingWithBarcode("4270031279928");

		//Tracking tracking = connection.getTrackingByNumber(trackingToGet);
		System.out.println(tracking3.getCheckpoints().toString());

	}

}
