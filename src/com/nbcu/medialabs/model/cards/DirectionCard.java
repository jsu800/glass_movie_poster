/*
 * Copyright (c) 2014 Joseph Su
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nbcu.medialabs.model.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.nbcu.medialabs.MirrorClient;
import com.nbcu.medialabs.WebUtil;
import com.google.api.services.mirror.model.Location;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class DirectionCard {

	private static final Logger LOG = Logger.getLogger(DirectionCard.class
			.getSimpleName());
	
	final static String key = "AIzaSyArYvzdUOIvjUpbUPGi6WTS3WKO7eOeXQE";
	static String theaterStreet = null;
	static String theaterLat = null;
	static String theaterLng = null;
	static String theaterName = null;
	static Double userLat = null;
	static Double userLng = null;
	
	public static String findLocation(Credential credential) {
		
		Mirror service = MirrorClient.getMirror(credential);		
		String retVal = null;
		
		try {			
			Location location = service.locations().get("latest").execute();
						
			if (location != null) {
				userLat = location.getLatitude();
				userLng = location.getLongitude();
				retVal = userLat + "," + userLng; 
			}			
		} catch (IOException e) {
			LOG.info("An error occurred: " + e);
		} finally {
			return retVal;
		}
	}
	
	private static void findTheaters(String data) {
		
		String placesSearchUrl = "https://maps.googleapis.com/maps/api/place/textsearch/xml?query=movie+theater&sensor=true" + "&location=" + data + "&radius=1" + "&key=" + key;		
		LOG.info("url = " + placesSearchUrl);
		String output = null;
		
		try {
			URL url = new URL(placesSearchUrl);
			InputStream is = url.openStream();
			
			SAXBuilder builder = new SAXBuilder();			
			Document document = (Document)builder.build(is);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("result");

			// just get the first entry assuming it is the closest one to user
			for (int i=0; i<1; i++) {
				
				Element node = (Element) list.get(i);
				
				String[] addressSegments = node.getChildText("formatted_address").split(",");
				theaterStreet = addressSegments[0];				
				theaterName = node.getChildText("name");
				
				List geoList = node.getChildren("geometry");				
				Element locationNode = (Element)geoList.get(0);
				
				List dataList = locationNode.getChildren();				
				Element dataNode = (Element)dataList.get(0);
				theaterLat = dataNode.getChildText("lat");
				theaterLng = dataNode.getChildText("lng");
		
				System.out.println("lat = " + theaterLat + " : lng = " + theaterLng);
				
			}			
			
		} catch (Exception e) {
			LOG.info("An error occurred reading: " + e);
		}
		
	}
		
	
	public static boolean insert(HttpServletRequest request, Credential credential) {
		
		LOG.info("insert");

		// Fetch user location
		String locationData = findLocation(credential);		
		
		// Fetch theater data based on user location
		findTheaters(locationData);
				
		TimelineItem timelineItem = new TimelineItem();
		timelineItem.setNotification(new NotificationConfig()
				.setLevel("DEFAULT"));
		List<MenuItem> menuItems = new LinkedList<MenuItem>();
		menuItems.add(new MenuItem().setAction("DELETE"));
		menuItems.add(new MenuItem().setAction("NAVIGATE"));		
		timelineItem.setMenuItems(menuItems);

		String glassMapUrl = "glass://map?w=240&h=360&marker=0;" + theaterLat + "," + theaterLng + "&marker=1;" + userLat + "," + userLng;
				
		String htmlContent = 
			"<article>"
					+ "<figure>"
					+ "<img src='"
					+ glassMapUrl 
					+ "' height='360' width='240'>"
					+ "</figure>"
					+ "<section>"
				    + "<h1 class='text-large yellow'>Nearest Movie Theater</h1>"
				    + "<p class='text-x-small'>"
				    + theaterStreet
				    + "</p>"
				    + "<p class='text-normal'>"
				    + "<img class='icon-small' src='http://zeeboxwidgets.com/glass/images/ic_location_50.png'>1 mile away"
				    + "</p>"
				    + "</section>"
        		   	+ "</article>";
		
		timelineItem.setHtml(htmlContent);
		
		// Setting theater location
		Location location = new Location();
		location.setLatitude(Double.parseDouble(theaterLat));
		location.setLongitude(Double.parseDouble(theaterLat));
		timelineItem.setLocation(location);
				
		try {
			
			MirrorClient.insertTimelineItem(credential,  timelineItem);
			
		} catch (MalformedURLException e1) {
			LOG.log(Level.SEVERE, "Failed to add no matches card to timeline.", e1);
			return false;
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Failed to add no matches card to timeline.", e);
			return false;
		}
		
		return true;
	}

}
