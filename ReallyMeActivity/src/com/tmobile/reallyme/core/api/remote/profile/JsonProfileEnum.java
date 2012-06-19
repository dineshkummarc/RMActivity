package com.tmobile.reallyme.core.api.remote.profile;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:21:26
 */
public enum JsonProfileEnum {
    ACTIVITY("jpa", "activity", "profileElement?name=activity&type=0&format=JSON&", new ActivityParser()),
    CALLER_TUNE_COLLECTION("jpctc", "ctcollection", "ctCollection?", new CallerTuneCollectionParser()),
    CALLER_TUNE_SELECTION("jpcts", "ctselection", "ctSellection?", new CallerTuneSelectionParser()),
    MUTUAL_FAVES("jpmf", "mutualFaves", "profileElement?name=mutualFaves&format=JSON&", new MutualFavesParser()),
    FAVES_FAVES("jpff", "favesFaves", "profileElement?name=favesFaves&format=JSON&", new FavesFavesParser()),
    PICTURES("jpp", "pictures", "profileElement?name=pictures&format=JSON&", new PicturesParser());

    private String uid;
    private String name;
    private String url;
    private Parser parser;

    JsonProfileEnum(String uid, String name, String url, Parser parser) {
        this.uid = uid;
        this.name = name;
        this.url = url;
        this.parser = parser;
    }

    private String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Parser getParser() {
        return parser;
    }

    public String getUrl() {
        return url;
    }

    public Boolean equals(JsonProfileEnum _enum) {
        return this.uid.equals(_enum.getUid());
    }
}
