package com.tmobile.reallyme.core.api.remote.pojo.tmo;

/**
 * dku
 */
public class TMOAuthenticationData {
    private String accountStatus;
    private String accountSubType;
    private String accountType;
    private String ban;
    private String registrationStatus;
    private String serviceLevel;

    private Boolean hasMasterPrivileges;
    private Boolean isAuthenticated;
    private Boolean isPrepaid;
    private Boolean isTakeControlSubscriber;
    private Boolean  mustChangePassword;

    private String msisdn;
    private String imsi;
    private String firstName;
    private String lastName;
    private String username;


    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountSubType() {
        return accountSubType;
    }

    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Boolean isHasMasterPrivileges() {
        return hasMasterPrivileges;
    }

    public void setHasMasterPrivileges(Boolean hasMasterPrivileges) {
        this.hasMasterPrivileges = hasMasterPrivileges;
    }

    public Boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Boolean isPrepaid() {
        return isPrepaid;
    }

    public void setPrepaid(Boolean prepaid) {
        isPrepaid = prepaid;
    }

    public Boolean isTakeControlSubscriber() {
        return isTakeControlSubscriber;
    }

    public void setTakeControlSubscriber(Boolean takeControlSubscriber) {
        isTakeControlSubscriber = takeControlSubscriber;
    }

    public Boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(Boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
