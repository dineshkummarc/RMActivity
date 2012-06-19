package com.tmobile.reallyme.core.api.remote.profile;

import org.json.JSONException;
import org.json.JSONObject;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.AbstractJSONRequest;
import com.tmobile.reallyme.utils.Constants;
import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 26.07.2009
 * Time: 19:40:00
 */
public class ProfileManager<T extends ProfileMetaData> extends AbstractJSONRequest {
    private JsonProfileEnum jsonProfileEnum;
    private String iid;
    private Parser<T> parser;

    @Override
    public void onEndDocument() {
        callBack(this.parser.getData());
        parser.clear();
    }

    public void callBack(T data){};

    protected void parse(JSONObject jsonObject)  throws JSONException {
        this.parser.parse(jsonObject);
    }

    @Override
    public String getUrl() {
        return Constants.API_URL + "/" + jsonProfileEnum.getUrl()
                + "sid=" + UserSession.getSessionid()
                + (Utils.isNotBlank(this.iid) ? ("&iid=" + this.iid) : "");
    }

    public void getProfileElement(JsonProfileEnum jsonProfileEnum, String iid) {
        this.jsonProfileEnum = jsonProfileEnum;
        this.parser = jsonProfileEnum.getParser();
        this.iid = Utils.isNotBlank(iid) ? iid : "";
        get();
        //test
//        parsinResult(null);
    }
}
