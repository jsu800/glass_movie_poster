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


/*
 * @author Joseph Su
 */
public class MovieInfoCard {

	private static final Logger LOG = Logger.getLogger(MovieInfoCard.class
			.getSimpleName());

	public static boolean insert(HttpServletRequest request, Credential credential) {
		LOG.info("insert");

		String synopsis = "An air marshall springs into action during a transatlantic flight afer receiving a series of text messages that put his fellow passengers at risk unless the airline transfers $150 million into an off-shore account";
		String movieUrl = "http://mobile.fandango.com/moviedetails?fmovieid=moviesearch165419";
		TimelineItem timelineItem = new TimelineItem();
		timelineItem.setSpeakableText(synopsis);
		timelineItem.setNotification(new NotificationConfig()
				.setLevel("DEFAULT"));

		// TODO support all same actions as welcome card to make easier for users?
		 List<MenuItem> menuItems = new LinkedList<MenuItem>();
		menuItems.add(new MenuItem().setAction("READ_ALOUD"));
		menuItems.add(new MenuItem().setAction("OPEN_URI").setPayload(movieUrl));
		menuItems.add(new MenuItem().setAction("DELETE"));
		timelineItem.setMenuItems(menuItems);
		//timelineItem.setBundleId("movie_data");

		String htmlContent = 
           "<article class='author'>"
        		   +  "<div class='overlay-full'>"
        		   +  "<header>"
        		   +  "<img src='http://zeeboxwidgets.com/glass/images/poster_nonstop.jpg'>"
        		   +  "<h1 class='text-small'>Non-Stop (2014)</h1>"
        		   +  "<h2 style='font-size:20px!important'>"
        		   +  "<img src='http://zeeboxwidgets.com/glass/images/rate_pg13.png'> 106 min - Action | Mystery | Thriller</h2>"
        		   +  "<hr>"
        		   +  "</header>"
        		   +  "<section>"
        		   +  "<p class='text-x-small' style='font-size:28px!important'>"
        		   +  synopsis
        		   +  "</p>"
        		   +  "</section>"
        		   +  "<footer>"
        		   +  "<p contenteditable='true'><img class='footer-brand-icon' src='http://zeeboxwidgets.com/glass/images/universal_logo.png'></p>"
        		   +  "</footer>"
        		   +  "<footer id='map-time-footer' class='has-brand-icon'></footer>"
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
