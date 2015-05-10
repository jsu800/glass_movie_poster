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

package com.nbcu.medialabs;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.mirror.model.Command;
import com.google.api.services.mirror.model.Contact;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.MenuValue;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.common.collect.Lists;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles POST requests from index.jsp. Modified version of the servlet from 
 * the original author, Jenny Murphy - http://google.com/+JennyMurphy
 * 
 * @author Joseph Su - http://google.com/+JosephSu
 */
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	  private static final Logger LOG = Logger.getLogger(MainServlet.class.getSimpleName());
	  public static final String CONTACT_NAME = "CNBC News Alerts";
	  public static final String CONTACT_ID = "com.cnbcu.medialabs.cnbc-news-alerts";
	
	

	/**
   * Private class to process batch request results.
   * <p/>
   * For more information, see
   * https://code.google.com/p/google-api-java-client/wiki/Batch.
   */
  private final class BatchCallback extends JsonBatchCallback<TimelineItem> {
    private int success = 0;
    private int failure = 0;

    @Override
    public void onSuccess(TimelineItem item, HttpHeaders headers) throws IOException {
      ++success;
    }

    @Override
    public void onFailure(GoogleJsonError error, HttpHeaders headers) throws IOException {
      ++failure;
      LOG.info("Failed to insert item: " + error.getMessage());
    }
  }

  
  
  /**
   * Do stuff when buttons on index.jsp are clicked
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

    String userId = AuthUtil.getUserId(req);
    Credential credential = AuthUtil.newAuthorizationCodeFlow().loadCredential(userId);
    String message = "";
    
    LOG.info("UserId = " + userId);
    LOG.info("Credential = " + credential);    
    
    if (req.getParameter("operation").equals("triggerPosterVision")) {

    	//NewsGenerator.triggerPosterVision(credential);

    } else if (req.getParameter("operation").equals("insertItem")) {
      LOG.info("Inserting Timeline Item");
      TimelineItem timelineItem = new TimelineItem();

      if (req.getParameter("message") != null) {
        timelineItem.setText(req.getParameter("message"));
      }

      // Triggers an audible tone when the timeline item is received
      timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));

      if (req.getParameter("imageUrl") != null) {
        // Attach an image, if we have one
        URL url = new URL(req.getParameter("imageUrl"));
        String contentType = req.getParameter("contentType");
        MirrorClient.insertTimelineItem(credential, timelineItem, contentType, url.openStream());
      } else {
        MirrorClient.insertTimelineItem(credential, timelineItem);
      }

      message = "A timeline item has been inserted.";

    } else if (req.getParameter("operation").equals("deleteTimelineItem")) {

      // Delete a timeline item
      LOG.fine("Deleting Timeline Item");
      MirrorClient.deleteTimelineItem(credential, req.getParameter("itemId"));

      message = "Timeline Item has been deleted.";

    } else {
      String operation = req.getParameter("operation");
      LOG.warning("Unknown operation specified " + operation);
      message = "I don't know how to do that";
    }
    WebUtil.setFlash(req, message);
    res.sendRedirect(WebUtil.buildUrl(req, "/"));
  }
  
  
  
}
