/*
 * Copyright (C) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.nbcu.medialabs.model.cards;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.MenuValue;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.nbcu.medialabs.AuthUtil;
import com.nbcu.medialabs.MirrorClient;
import com.nbcu.medialabs.WebUtil;

public class WelcomeCard {

	public static final String SOURCE_ID = "poster_vision_welcome";

	private static final Logger LOG = Logger.getLogger(WelcomeCard.class
			.getSimpleName());

	public static void insert(HttpServletRequest req, String userId)
			throws IOException {
		try {
			Credential credential = AuthUtil.newAuthorizationCodeFlow()
					.loadCredential(userId);

			TimelineItem timelineItem = new TimelineItem();
			timelineItem.setSourceItemId(SOURCE_ID);
			timelineItem.setSpeakableText("Google Glass Poster Vision for Universal");
			timelineItem.setNotification(new NotificationConfig()
					.setLevel("DEFAULT"));

			List<MenuItem> menuItems = new LinkedList<MenuItem>();
			menuItems.add(new MenuItem().setAction("TOGGLE_PINNED"));
			menuItems.add(new MenuItem().setAction("DELETE"));			
			
			timelineItem.setMenuItems(menuItems);

			final String contentType = "image/png";
			final String appBaseUrl = WebUtil.buildUrl(req, "/");
			final String imageUrl = appBaseUrl
					+ "static/images/welcomeCard.jpg";
			final URL url = new URL(imageUrl);

			MirrorClient.insertTimelineItem(credential, timelineItem,
					contentType, url.openStream());

			LOG.info("Bootstrapper inserted poster vision welcome message for user " + userId);
		} catch (Exception e) {
			LOG.log(Level.INFO, "Error inserting welcome card.", e);
		}
	}


}
