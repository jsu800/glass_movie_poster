## Glass Movie Poster

This Android service runs on Google Appspot Engine which runs and computes image recognition tasks using the beta-face service. This appspot service captures movie poster image, computes image result, and sends notifications to client via the Glass Mirror API:

##Server: Google AppEngine Service (requiring Java 1.7+)
* connects to betaface image recognition service for image processing
* computes dynamic image recognition - level of confidence 0% - 100% match
* listens for client photo share activities (async notifications via REST GET) 
* communicates with client (sending client notificaitons via Glass Mirror API)

This project uses two publicly available APIs:

* Google Glass API 16-18
* Open Source Beta Face API for image recognition

##Client: Google Glass
Minimal client installation steps involved other than going through the standard OAuthing dance via:

https://poster-vision.appspot.com/

to ensure that client is equipped with the poster app that communicates with the server above

* snaps and captures image photo
* shares image photo by sending it to Server (see below)
* receives async server notifications about image results 

![](https://github.com/jsu800/glass_movie_poster/blob/master/image/glass%20movie%20poster%20app.png)

##License
/*
 * MIT License
 * Copyright (c) 2014 Joseph Su (http://www.github.com/jsu800)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and 
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of 
 * the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO 
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
