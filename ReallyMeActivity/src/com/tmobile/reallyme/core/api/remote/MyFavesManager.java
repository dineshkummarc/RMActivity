package com.tmobile.reallyme.core.api.remote;

import java.util.HashSet;

import org.xml.sax.Attributes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.config.TMobileContextProvider;
import com.tmobile.reallyme.core.persistence.definition.FaveDefinition;
import com.tmobile.reallyme.core.persistence.definition.FaveMemberDefinition;
import com.tmobile.reallyme.utils.Constants;

/**
 * dku
 */
public class MyFavesManager extends AbstarctXmlRequest{
    private static MyFavesManager instance = null;
    private Boolean isFavesOk = false;
    private String faveId = "";
    private String faveName = "";
    private HashSet<String> faveMemberSet = new HashSet<String>();
    private HashSet<String> dbFavesFIDSet = new HashSet<String>();//already stored faves
    private ContentResolver contentResolver = null;

    private MyFavesManager() {
        contentResolver = TMobileContextProvider.contentResolver;
    }

    public static MyFavesManager getInstance() {
        if(instance == null){
            instance = new MyFavesManager();
        }
        return instance;
    }

    public String getUrl() {
        return Constants.API_URL + "/myfaves?sid=" + UserSession.getSessionid();
    }

    public void onEndDocument() {

        //remove all Fave groups which wasn't updated from server
        for(String fid : dbFavesFIDSet) {
            contentResolver.delete(FaveMemberDefinition.CONTENT_URI, FaveMemberDefinition.PARENT_FAVE_ID+" = ? ", new String[]{fid});   //delete members
            contentResolver.delete(FaveDefinition.CONTENT_URI, FaveDefinition.FID+" = ? ", new String[]{fid});  //delete group
        }

    }

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("faves") && Boolean.parseBoolean(atts.getValue("ok"))) {
            isFavesOk = true;
        }
        if(isFavesOk){
            if(localName.equals("fave")){
                faveId = atts.getValue("id");
                faveName = atts.getValue("name");
            }
            if(localName.equals("principal")){
                faveMemberSet.add(atts.getValue("id"));
            }
        }
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        if(localName.equals("fave")){
            //store fave + members
/*            Cursor faveCursor = contentResolver.query(FaveDefinition.CONTENT_URI, null,FaveDefinition.FID+"=?" ,new String[]{faveId}, "");

            int count = faveCursor.getCount();
            faveCursor.close();*/

            ContentValues faveValues = new ContentValues();
            faveValues.put(FaveDefinition.NAME, faveName);
            if(dbFavesFIDSet.contains(faveId)){
                contentResolver.update(FaveDefinition.CONTENT_URI, faveValues, FaveDefinition.FID+"=?", new String[]{faveId});
                dbFavesFIDSet.remove(faveId);
            }else{
                faveValues.put(FaveDefinition.FID, faveId);
                contentResolver.insert(FaveDefinition.CONTENT_URI, faveValues);
            }

            
            Cursor faveMemberCursor = contentResolver.query(FaveMemberDefinition.CONTENT_URI, null,
                            FaveMemberDefinition.PARENT_FAVE_ID+"=? ", new String[]{faveId}, "");
            if(faveMemberCursor.getCount() > 0){
                faveMemberCursor.moveToFirst();
                while (!faveMemberCursor.isAfterLast()){ // go through stored members
                    String fmid = faveMemberCursor.getString(faveMemberCursor.getColumnIndex(FaveMemberDefinition.FMID));
                    if(faveMemberSet.contains(fmid)){ //if true, member already in database
                        faveMemberSet.remove(fmid); //no need to store, just remove from the set
                    }else{
                        //delete member, because it absent in last update
                        contentResolver.delete(FaveMemberDefinition.CONTENT_URI,
                            FaveMemberDefinition.FMID+"=? and "+ FaveMemberDefinition.PARENT_FAVE_ID+"=?", new String[]{fmid, faveId});
                    }
                    faveMemberCursor.moveToNext();
                }
            }
            faveMemberCursor.close();
            for(String faveMemberId : faveMemberSet){ // store new faveMembers
                if(faveMemberCursor.getCount() <= 0){
                    ContentValues faveMemberValues = new ContentValues();
                    faveMemberValues.put(FaveMemberDefinition.FMID, faveMemberId);
                    faveMemberValues.put(FaveMemberDefinition.PARENT_FAVE_ID, faveId);
                    contentResolver.insert(FaveMemberDefinition.CONTENT_URI, faveMemberValues);
                }
            }
        }
    }

    public void fetchMyFaves(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                Cursor favesCursor = contentResolver.query(FaveDefinition.CONTENT_URI, new String[]{FaveDefinition.FID},"" ,null, "");
                if(favesCursor.getCount() > 0){
                    favesCursor.moveToFirst();
                    int index = favesCursor.getColumnIndex(FaveDefinition.FID);
                    while(!favesCursor.isAfterLast()){
                        dbFavesFIDSet.add(favesCursor.getString(index));
                        favesCursor.moveToNext();
                    }
                }
                get();
            }
        };
        thread.start();
    }
}
