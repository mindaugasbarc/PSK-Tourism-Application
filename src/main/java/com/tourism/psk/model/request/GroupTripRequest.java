package com.tourism.psk.model.request;

import java.time.LocalDate;

public class GroupTripRequest {

    private String name;
    private String description;
    private String officeFromName;
    private String officeToName;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public GroupTripRequest(String name, String description, String officeFromName, String officeToName, LocalDate dateFrom, LocalDate dateTo) {
        this.name = name;
        this.description = description;
        this.officeFromName = officeFromName;
        this.officeToName = officeToName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
