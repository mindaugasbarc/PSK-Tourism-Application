package com.tourism.psk.model.request;

import java.time.LocalDate;

public class GroupTripRequest {

    private String name;
    private String description;
    private String officeFromName;
    private String officeToName;
    private String dateFrom;
    private String dateTo;
    private long userId;

    public GroupTripRequest(String name, String description, String officeFromName, String officeToName, String dateFrom, String dateTo, long userId) {
        this.name = name;
        this.description = description;
        this.officeFromName = officeFromName;
        this.officeToName = officeToName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.userId = userId;
    }

    public GroupTripRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfficeFromName() {
        return officeFromName;
    }

    public void setOfficeFromName(String officeFromName) {
        this.officeFromName = officeFromName;
    }

    public String getOfficeToName() {
        return officeToName;
    }

    public void setOfficeToName(String officeToName) {
        this.officeToName = officeToName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
