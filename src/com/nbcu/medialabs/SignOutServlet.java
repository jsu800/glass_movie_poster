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

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.nbcu.medialabs.model.cards.DirectionCard;
import com.nbcu.medialabs.model.cards.MovieInfoCard;
import com.nbcu.medialabs.model.cards.TicketPurchaseCard;

public class SignOutServlet extends HttpServlet {
	
  private static final Logger LOG = Logger.getLogger(SignOutServlet.class.getSimpleName());
	
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

	  
	// TODO: DELETE THIS IN PRODUCTION 
	// This is a hack - this is to prevent that in case the notificaitons never arrive that we can still
	// send the cards to the user
	// The dance is done. Do our bootstrapping stuff for this user
	Credential credential = AuthUtil.getCredential(req);
	TicketPurchaseCard.insert(req, credential);
	DirectionCard.insert(req, credential);
	MovieInfoCard.insert(req, credential);	  
	  
	  
	// It'd be nice to do some XSRF validation here
    AuthUtil.clearUserId(req);
    
    LOG.info("User has been signed out");    
    WebUtil.setFlash(req, "You have been signed out.");
    
    resp.sendRedirect(WebUtil.buildUrl(req, "/"));
    
    
    // If you are logged into other services, clear their sessions here :)
  }
}
