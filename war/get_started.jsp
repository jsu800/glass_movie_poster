<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.api.services.mirror.model.Contact" %>
<%@ page import="com.nbcu.medialabs.MirrorClient" %>
<%@ page import="com.nbcu.medialabs.WebUtil" %>
<%@ page import="com.nbcu.medialabs.MainServlet" %>
<%@ page import="com.nbcu.medialabs.AuthUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.api.services.mirror.model.TimelineItem" %>
<%@ page import="com.google.api.services.mirror.model.Subscription" %>
<%@ page import="com.google.api.services.mirror.model.Attachment" %>
<%@ page import="com.nbcu.medialabs.model.cards.WelcomeCard" %>

<%
	String userId = com.nbcu.medialabs.AuthUtil.getUserId(request);
 	WelcomeCard.insert(request, userId);  
%>

<!doctype html>

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link href="static/css/style.css" rel="stylesheet" media="screen" />	
    <title>Google Glass PosterVision</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  </head>

  <body>

		<div class="header">
			<div style="text-align:center;"><img alt="Tap Google Glass" height="180"
					src="static/images/universal_logo.jpg" />	
		    <h1>Google Glass PosterVision<span style="vertical-align: 25%;">&copy;</span> for Universal</h1>
			<div>Thank you for using  NBC Universal PosterVision on Glass.</div>
			<div></div>
		  <div>Also, you may use "Sign Out" to unsubscribe and stop receiving PosterVision messages.</div>
		  <div></div>
			<div>Glass is a trademark of Google Inc.		</div>
            
            		<% String flash = WebUtil.getClearFlash(request);
				if (flash != null) { %>
					<%= flash %><br />
			<% } %>		
		   	<form class="navbar-form pull-right">
			    <input type="hidden" name="operation" value="triggerPosterVision">
		      <br>
			</form>
		   	<form class="navbar-form pull-right" action="/signout" method="post">
		      		<input type="submit" value="Sign Out" />
			</form>	
		</div>  
		</div>
		<div class="footer">
		  <p><br>
			<br>	
		  </p>
		  <p><br>
			<br>
			<span>&copy;2014</span> Media Labs - NBCUniversal. All rights reserved.
		  </p>
		</div>
  </body>
</html>