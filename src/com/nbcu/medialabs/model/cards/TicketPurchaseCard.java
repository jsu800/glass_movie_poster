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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.nbcu.medialabs.MirrorClient;
import com.nbcu.medialabs.WebUtil;

public class TicketPurchaseCard {

	private static final Logger LOG = Logger.getLogger(TicketPurchaseCard.class
			.getSimpleName());

	public static boolean insert(HttpServletRequest request, Credential credential) {
		LOG.info("insert");

		TimelineItem timelineItem = new TimelineItem();
		timelineItem.setNotification(new NotificationConfig()
				.setLevel("DEFAULT"));

		// TODO support all same actions as welcome card to make easier for users?
		List<MenuItem> menuItems = new LinkedList<MenuItem>();
		menuItems.add(new MenuItem().setAction("DELETE"));
		timelineItem.setMenuItems(menuItems);

		// Find current date and time
		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		int month = cal.get(Calendar.MONTH);
		int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
		
		DateFormat df = new SimpleDateFormat("EEEE, MMMM, dd");	
		cal.set(Calendar.DAY_OF_WEEK, day_of_week);
		cal.set(Calendar.DAY_OF_MONTH, day_of_month);
		cal.set(Calendar.MONTH, month);
		
		String htmlContent = 
           "<article class='author'>"
        		   +  "<div class='overlay-full'>"
        		   +  "<header>"
        		   +  "<img src='http://zeeboxwidgets.com/glass/images/fandango.png'>"
        		   +  "<h1 class='text-small'>Ticket selection</h1>"
       		       +  "<h2 style='font-size:20px!important'><img src='http://zeeboxwidgets.com/glass/images/rate_pg13.png'> None-Stop 1 hr 47 min</h2>"
        		   +  "</header>"
        		   +  "<section>"
        		   +  "<hr>"
        		   +  "<table class='text-x-small align-justify'>"
        		   +  "<tbody>"
        		   +  "<tr>"
        		   +  "<td>"
        		   +  df.format(cal.getTime())
        		   +  "</td>"
        		   +  "<td>3:00 pm</td>"
        		   +  "</tr>"
        		   +  "<tr>"
        		   +  "<td>1 Adult</td>"
        		   +  "<td>$13.50</td>"
        		   +  "</tr>"
        		   +  "</tbody>"
        		   +  "</table>"
        		   + "</section>"
        		   + "<footer><p contenteditable='true' class='green'>Tap to complete purchase</p></footer>"
        		   +  "</div></article>";
		
		timelineItem.setHtml(htmlContent);
		
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
