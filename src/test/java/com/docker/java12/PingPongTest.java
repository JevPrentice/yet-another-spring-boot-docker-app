/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docker.java12;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jev
 */
public class PingPongTest {

    private PingPong pingPong;

    public PingPongTest() {
    }

    @Before
    public void setUp() {
        Logger.getLogger(PingPongTest.class.getName()).log(Level.INFO, "Setting up ping pong service before test");
        pingPong = new PingPong();
        pingPong.start();
    }

    @After
    public void tearDown() {
        Logger.getLogger(PingPongTest.class.getName()).log(Level.INFO, "Stopping ping pong service after test");
        pingPong.stop();
    }

    @Test
    public void testPingEndpoint() {
        HttpURLConnection urlConnection = null;
        try {
            final URL url = new URL("http://localhost:8082/ping");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);

            final int responseCode = urlConnection.getResponseCode();

            Assert.assertEquals(200, responseCode);

            final String response = readInputStream(urlConnection);

            Assert.assertEquals("pong\n", response.toLowerCase(Locale.ENGLISH));

        } catch (final IOException e) {
            Logger.getLogger(PingPongTest.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (final RuntimeException e) {
                    Logger.getLogger(PingPongTest.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }

    private static String readInputStream(final HttpURLConnection urlConnection) {
        try (final InputStream is = urlConnection.getInputStream();
                final Reader reader = new InputStreamReader(is, "UTF-8")) {
            final StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[2 ^ 13];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, read);
            }
            return sb.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
