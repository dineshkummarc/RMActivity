package com.tmobile.reallyme.core.persistence;

/**
 * User: Kolesnik Aleksey
 * Date: 08.07.2009
 * Time: 22:09:16
 */
public enum IdentityTypeEnum {
    MY("0"),
    FRIEND("1"),
    MUTUAL_FRIEND("2");

    private String uid;

    IdentityTypeEnum(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return this.uid;
    }

    public String toString() {
        return String.valueOf(this.uid);
    }

    public Boolean equals(IdentityTypeEnum type) {
        return this.uid.equals(type.getId());
    }

}
