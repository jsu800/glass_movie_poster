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
package com.nbcu.medialabs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.phsystems.irisx.utils.betaface.Face;
import ru.phsystems.irisx.utils.betaface.Image;
import ru.phsystems.irisx.utils.betaface.Person;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.model.Attachment;
import com.google.api.services.mirror.model.Contact;
import com.google.api.services.mirror.model.Notification;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.api.services.mirror.model.UserAction;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.nbcu.medialabs.model.cards.DirectionCard;
import com.nbcu.medialabs.model.cards.ErrorCard;
import com.nbcu.medialabs.model.cards.MovieInfoCard;
import com.nbcu.medialabs.model.cards.TicketPurchaseCard;
import com.nbcu.medialabs.model.Subject;



public class NotifyServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(NotifyServlet.class
			.getSimpleName());

	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		
		LOG.info("doPost");

		ack(response);

		final Notification notification = getNotification(request);
		final String userId = notification.getUserToken();
		final Credential credential = AuthUtil.getCredential(userId);
		final Mirror glass = MirrorClient.getMirror(credential);

		LOG.info("userId = " + userId);
		
		// Get Timeline item notification is about.
		final TimelineItem replyItem = glass.timeline()
				.get(notification.getItemId()).execute();
		LOG.info("Shared timeline item: " + replyItem.toPrettyString());
		LOG.info("getOperation() = " + notification.getOperation());
		LOG.info("getUserActions() = " + notification.getUserActions().toString());
		LOG.info("getUserActions().size = " + notification.getUserActions().size());
		
		
		// Shares to the inserted contacts.
		if ("INSERT".equals(notification.getOperation())
				&& notification.getUserActions().contains(
						new UserAction().setType("SHARE"))) {

			handleImageShare(request, notification, credential, replyItem);
			return;
		}

		LOG.warning("I don't know what to do with this notification, so I'm ignoring it.");
	}

	private Notification getNotification(HttpServletRequest request)
			throws IOException {

		// Get the notification object from the request body (into a string so
		// we can log it)
		BufferedReader notificationReader = new BufferedReader(
				new InputStreamReader(request.getInputStream()));
		
		String notificationString = "";

		// Count the lines as a very basic way to prevent Denial of Service
		// attacks
		int lines = 0;
		while (notificationReader.ready()) {
			notificationString += notificationReader.readLine();
			lines++;

			// No notification would ever be this long. Something is very wrong.
			if (lines > 1000) {
				throw new IOException(
						"Attempted to parse notification payload that was unexpectedly long.");
			}
		}

		LOG.info("got raw notification " + notificationString);

		JsonFactory jsonFactory = new JacksonFactory();

		// If logging the payload is not as important, use
		// jacksonFactory.fromInputStream instead.
		Notification notification = jsonFactory.fromString(notificationString,
				Notification.class);
		return notification;
	}

	private void ack(HttpServletResponse response) throws IOException {
		LOG.info("ack");
		// Respond with OK and status 200 in a timely fashion to prevent
		// redelivery
		response.setContentType("text/html");
		Writer writer = response.getWriter();
		writer.append("OK");
		writer.close();
	}

	private static boolean containsContact(final String aId,
			final List<Contact> contacts) {
		for (Contact contact : contacts) {
			if (contact.getId().equals(aId)) {
				return true;
			}
		}
		return false;
	}
	
	// This is to MOCK up the handling of recognition by returning a stock movie poster info card
	private void handleImageShare(HttpServletRequest request,
			Notification notification, Credential credential,
			TimelineItem replyItem) {
		
		//doImageRecognition(request, notification, credential, replyItem);		
    	TicketPurchaseCard.insert(request, credential);
    	DirectionCard.insert(request, credential);
    	MovieInfoCard.insert(request, credential);
	}
	
	private void doImageRecognition(HttpServletRequest request,
			Notification notification, Credential credential,
			TimelineItem replyItem) {
		
		LOG.info("doImageRecognition.");

		try {
			// Get image shared with app.
			final Mirror glass = MirrorClient.getMirror(credential);
			List<Attachment> attachments = replyItem.getAttachments();
			if (null == attachments || attachments.isEmpty()) {
				LOG.info("No attachment found.");
				return;
			}
			InputStream is = downloadAttachment(glass, attachments.get(0),
					credential);
			Image trainingImage = new Image(is);

			// Extract face.
			final ArrayList<Face> faces = trainingImage.getFaces();
			// TODO support multiple faces
			final Face face = null != faces && faces.size() > 0 ? faces.get(0)
					: null;

			if (containsContact("poster_vision", replyItem.getRecipients())) {
				LOG.info("performing face search");
				
				if (null == face) {
					ErrorCard.insert(request, credential);
					return;
				}

				// Lookup subject by facial recognition
				Person probe = new Person();
				probe.addUID(face.getUID());
				
				
				// TODO: need to persist baseline data via DAO next
		        //URL baselineUrl = new URL("http://collider.com/wp-content/uploads/non-stop-liam-neeson-600x398.jpg");
		        // Get it from persistent file storage
				String filePath = "/static/images/non-stop-liam-neeson-600x398.jpg";
				//Image baselineImg = new Image(baselineUrl.openStream());        
				Image baselineImg = new Image(filePath);;
				ArrayList<Face> detectedFaces = baselineImg.getFaces();
		        
		        // If there's nothing returning that means the facial reg operation was a failure. Returning an error card to user immediately
		        if (detectedFaces.size() == 0) {
		        	ErrorCard.insert(request, credential);
		        	LOG.info("RECOGNITION: failed");
		        	return;
		        }
		        
		        ArrayList<String> baselineFaces = new ArrayList<String>();
		        baselineFaces.add(detectedFaces.get(0).getUID());

		        LOG.info("Comparison " + probe.compareWithUIDsForConfidence(baselineFaces));
				
			}

		} catch (Throwable e) {
			LOG.log(Level.SEVERE, "Error handling search request.", e);
		}
	}
	


	/**
	 * Download a timeline items's attachment.
	 * 
	 * @param service
	 *            Authorized Mirror service.
	 * @param itemId
	 *            ID of the timeline item to download the attachment for.
	 * @param attachment
	 *            Attachment to download content for.
	 * @return The attachment content on success, {@code null} otherwise.
	 */
	public static InputStream downloadAttachment(Mirror service,
			Attachment attachment, Credential credential) {
		return SendServerRequest.streamGet(attachment.getContentUrl(),
				credential.getAccessToken());
	}

}
