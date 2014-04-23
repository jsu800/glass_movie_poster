glass_movie_poster
==================

This Android app runs on Google Glass and interacts with an Appspot (Google App Engine) server which runs image 
recognition on captured movie poster image and computes dynamic results for the Glass client.

Client: Google Glass
* snaps and captures image photo
* shares image photo by sending it to Server (see below)
* receives async server notifications about image results 

Server: Google AppEngine Service (requiring Java 1.7+)
* connects to betaface image recognition service for image processing
* computes dynamic image recognition - level of confidence 0% - 100% match
* listens for client photo share activities (async notifications via REST GET) 
* communicates with client (sending client notificaitons via Glass Mirror API)
