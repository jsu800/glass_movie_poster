glass_movie_poster
==================

This Android service runs on Google Appspot Engine which runs and computes image recognition tasks using the beta-face service. This appspot service captures movie poster image, computes image result, and sends notifications to client via the Glass Mirror API:

Server: Google AppEngine Service (requiring Java 1.7+)
* connects to betaface image recognition service for image processing
* computes dynamic image recognition - level of confidence 0% - 100% match
* listens for client photo share activities (async notifications via REST GET) 
* communicates with client (sending client notificaitons via Glass Mirror API)

This project uses two publicly available APIs:

* Google Glass API 16-18
* Open Source Beta Face API for image recognition

Client: Google Glass
NB: there's minimal client installation involved other than going through the standard OAuthing dance such as 

https://poster-vision.appspot.com/

to ensure that client is equipped with the poster app that communicates with the server above

* snaps and captures image photo
* shares image photo by sending it to Server (see below)
* receives async server notifications about image results 
