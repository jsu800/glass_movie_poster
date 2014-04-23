<%@ page import="com.google.api.client.auth.oauth2.Credential" %>
<%@ page import="com.google.api.services.mirror.model.Contact" %>
<%@ page import="com.nbcu.medialabs.MirrorClient" %>
<%@ page import="com.nbcu.medialabs.WebUtil" %>
<%@ page import="com.nbcu.medialabs.MainServlet" %>
<%@ page import="com.nbcu.medialabs.AuthUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.api.services.mirror.model.TimelineItem" %>
<%@ page import="com.google.api.services.mirror.model.Subscription" %>
<%@ page import="com.google.api.services.mirror.model.Attachment" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<%
  String userId = AuthUtil.getUserId(request);
  String appBaseUrl = WebUtil.buildUrl(request, "/");

  Credential credential = AuthUtil.getCredential(userId);

%>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="static/css/style.css" rel="stylesheet" media="screen" />	
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <title>Google Glass PosterVision</title>
  </head>

  <body>
		<div class="header">
			<div style="text-align:center;"><img alt="Tap Google Glass" height="180"
					src="static/images/universal_logo.jpg" />	
		    <h1>Google Glass PosterVision<span style="vertical-align: 25%;">&copy;</span> for Universal</h1></div>
            <div class="infoGraphic">
            		<img alt="Tap Google Glass" height="300" width="550" src="static/images/info_graphic.png" />	
            </div>
		  <div style="width:550px"><div>We invite you to try our new service specifically for users of Glass.</div>
			<div></div>
		  <div>Please click "Get it on GLASS" below to start using,  movie poster service that will deliver movie infomation directly toGlass™ device, with option to purchase tickets.</div>
			<div></div>
			<div>The sign in process will involve authenticating via your Google Account at which time Google will be asking you to:
			  <ul>
			    <li>Allow us to add items to your Glass timeline</li>
			    <li>Allow us to retrieve the email address tied to your Glass device.</li>
		      </ul>
		  </div>
		  <div>Also, you may use "Connect My Glass" to unsubscribe and stop receiving these messages.</div>
		  <div></div>
			<div>Glass is a trademark of Google Inc.</div></div>
			<br/><br/>
			<form action="get_started.jsp" class="mainCta">
				    <input type="image" src="static/images/getitonglass_172x60_action_button.png">
				</form>
				<br>
				<br>
				<% String flash = WebUtil.getClearFlash(request);
					if (flash != null) { %>
						<%= flash %> <br /><br />
				<% } %>		
				<br>
				<br>
            </p>
			</p>
		</div>  

		<div class="footer">								
				<span>&copy;2014</span> Media Labs - NBCUniversal. All rights reserved.
		</div>

		
  </body>
</html>
