package eu.cynarski.emailsender.rest;

import java.util.Map;

public class Recipient {
    private Map<String, String> customProperties;
    private String emailAddress;

    public Map<String, String> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Map<String, String> customProperties) {
        this.customProperties = customProperties;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
