package com.tmobile.reallyme.core.api.remote;

/**
 * User: Kolesnik Aleksey
 * Date: 20.07.2009
 * Time: 0:06:33
 */
public class ProfileMetaData extends BaseMetaData{
    public Boolean verificationRequired;
    ProfileMetaData(Boolean ok, Boolean verificationRequired) {
        super(ok);
        this.verificationRequired = verificationRequired; 
    }
}
