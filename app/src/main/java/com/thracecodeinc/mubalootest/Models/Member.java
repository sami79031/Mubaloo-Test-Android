package com.thracecodeinc.mubalootest.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Samurai on 6/29/16.
 */
public class Member implements Serializable {
    private String ID;
    private String firstName;
    private String lastName;
    private String role;
    private String profileImgURL;
    private boolean teamLead;

    public Member() {
        this.ID = "";
        this.firstName = "";
        this.lastName = "";
        this.role = "";
        this.profileImgURL = "";
        this.teamLead = false;
    }

    public Member(String ID, String firstName, String lastName, String role, String profileImgURL, boolean teamLead) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.profileImgURL = profileImgURL;
        this.teamLead = teamLead;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    public boolean isTeamLead() {
        return teamLead;
    }

    public void setTeamLead(boolean teamLead) {
        this.teamLead = teamLead;
    }

    @Override
    public String toString() {
        return "Member{" +
                "ID='" + ID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", profileImgURL='" + profileImgURL + '\'' +
                ", teamLead=" + teamLead +
                '}';
    }
}
