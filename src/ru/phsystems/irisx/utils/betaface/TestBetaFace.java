package ru.phsystems.irisx.utils.betaface;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * IRIS-X Project
 * Author: Nikolay A. Viguro
 * WWW: smart.ph-systems.ru
 * E-Mail: nv@ph-systems.ru
 * Date: 28.09.12
 * Time: 14:03
 *
 * BetaFace.com API implements.
 */

public class TestBetaFace {

    private static Logger log = Logger.getLogger(TestBetaFace.class.getName());

    public static void main(String[] args) throws IOException, JDOMException, InterruptedException {

        log.info("Start BetaFace API test!");

        //URL url1 = new URL("http://afd28827a9dc6f044f59-b21abc630f0dd591b64353526a1109ce.r94.cf1.rackcdn.com/Valentina_head.jpg");
        URL url1 = new URL("http://collider.com/wp-content/uploads/non-stop-liam-neeson-600x398.jpg");
        
        InputStream is1 = url1.openStream(); 

        //URL url2 = new URL("http://afd28827a9dc6f044f59-b21abc630f0dd591b64353526a1109ce.r94.cf1.rackcdn.com/Shelly_head.jpg");
        URL url2 = new URL("http://cinemavine.com/wp-content/uploads/2013/01/liam_neeson_non_stop_movie_images-e1357776812468.png");
        
        InputStream is2 = url2.openStream(); 

        // Upload images of a couple different people. 
        Image img1 = new Image(is1);        
        Image img2 = new Image(is2);

        // Get detected face for each.
        // Currently only support one face per picture.
        ArrayList<Face> faces1 = img1.getFaces();
        ArrayList<Face> faces2 = img2.getFaces();

        log.info("UID1: "+faces1.get(0).getUID()+
        		"\nUID2: "+faces2.get(0).getUID()
        		);

        Person originalPoster = new Person();
        originalPoster.setName("Non-Stop Poster");
        originalPoster.addUID(faces1.get(0).getUID());

        Person newPoster = new Person();
        newPoster.setName("Lance");
        newPoster.addUID(faces2.get(0).getUID());

        ArrayList<String> originalPosterFaces = new ArrayList<String>();
        originalPosterFaces.add(faces1.get(0).getUID());

        ArrayList<String> newPosterFaces = new ArrayList<String>();
        newPosterFaces.add(faces2.get(0).getUID());
        
        // compare
        
        // Does return true.
        log.info("======Is originalPoster present on originalPoster faces? " + originalPoster.compareWithUIDsForConfidence(originalPosterFaces));
        
        // Does return false.
        log.info("======Is originalPoster present on newPoster faces? " + originalPoster.compareWithUIDsForConfidence(newPosterFaces));
        
        // ~6x% confidence for correct first two, ~3x% for incorrect second two
        log.info("======Is newPoster present on newPoster faces? " + newPoster.compareWithUIDsForConfidence(newPosterFaces));
        
        log.info("======Is newPoster present on originalPoster faces? " + newPoster.compareWithUIDsForConfidence(originalPosterFaces));
    }

}

