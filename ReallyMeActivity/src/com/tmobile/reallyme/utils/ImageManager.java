package com.tmobile.reallyme.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.tmobile.reallyme.R;

/**
 * dku
 */
public class ImageManager {
    //Log log = new Log(this.getClass());
    private final Map<String, Drawable> drawableMap;
    private Context context;
    public ImageManager(Context context) {
        drawableMap = new HashMap<String, Drawable>();
        this.context = context;
    }


    public void fetchDrawableLazy(final String urlString, final ImageView imageView) {
        if (drawableMap.containsKey(urlString)) {
            imageView.setImageDrawable(drawableMap.get(urlString));
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageView.setImageDrawable((Drawable) message.obj);
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                Drawable drawable = fetchDrawable(urlString);
                Message message = handler.obtainMessage(1, drawable);
                handler.sendMessage(message);
            }
        };
        thread.start();
    }

    public Drawable fetchDrawable(String urlString) {
        if (drawableMap.containsKey(urlString)) {
            return drawableMap.get(urlString);
        }
        Log.i("ImageManager", "image url:" + urlString);
        Drawable drawable = null;
        try {
            if(urlString.contains("/images/nobody.png") || "".equals(urlString)){
                drawable = context.getResources().getDrawable(R.drawable.nobody);

            }else{
                InputStream is = fetch(urlString);
                drawable = Drawable.createFromStream(is, "src");                
            }
            drawableMap.put(urlString, drawable);
            return drawable;
        } catch (MalformedURLException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        }
    }

    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }
}
