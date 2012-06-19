package com.tmobile.reallyme.core.api.remote;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.tmobile.reallyme.utils.Log;
import com.tmobile.reallyme.utils.Profiler;

/**
 * Handles interaction between HttpRequest and PullParser and defines
 * a simple request. The concrete class must implement onPullEvent.
 * location is the URL for the query
 * 
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 15:45:05
 */
public abstract class AbstractRequest{
    protected Log log = new Log(this.getClass());
    private Profiler p_get = new Profiler(this.getClass().getName() + " GET ");
    private Profiler p_post = new Profiler(this.getClass().getName() + " POST ");
    protected Profiler p_parse = new Profiler(this.getClass().getName() + " PARSE ");

    protected Log logError = new Log("AbstractRequest - SERVERERROR");
    protected Boolean active = false;

    /**
     * A function that gets called back when u finish parsing
     * from the PullParser
     */
    public abstract void onEndDocument();

    /**
     * Target location of the HTTP GET and POST request is a URL
     */
    public abstract String getUrl();

    /** Parser for the HTTP response input stream.*/
    protected void get() {
        p_get.start();
        log.info("Send HTTP GET request to the: " + getUrl());
        /** Refer to the javadoc for javafx.io.HttpRequest */
        // Prepare a request object
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(getUrl());
        try {
            active = true;
            HttpResponse response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() != 200) {
                log.info("HTTP response " + response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase());
            }

            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            p_parse.start();
            parsinResult(instream);
            instream.close();
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        active = false;
    }

    protected void post(List<NameValuePair> nameValuePairs) {
        log.info("Post:\n " + nameValuePairs);
        try {
            this.post(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.toString());
        }
    }

    protected void post(String content) {
        log.info("Post:\n " + content);
        try {
            this.post(new StringEntity(content));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.toString());
        }
    }

     private void post(HttpEntity httpEntity) {
        p_post.start();
        log.info("Send HTTP POST request to the: " + getUrl());

        /** Refer to the javadoc for javafx.io.HttpRequest */
        HttpClient httpclient = new DefaultHttpClient();
        //Your URL
        HttpPost httppost = new HttpPost(getUrl());
        try {

            httppost.setEntity(httpEntity);

            active = true;
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() != 200) {
                log.info("HTTP response " + response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase());
            }
            //
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            p_parse.start();
            parsinResult(instream);
            instream.close();
        } catch (ClientProtocolException e) {
           log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        active = false;
    }

    protected abstract void parsinResult(InputStream instream);
}


