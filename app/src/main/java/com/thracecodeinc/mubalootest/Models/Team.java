package com.thracecodeinc.mubalootest.Models;

import java.io.Serializable;

/**
 * Created by Samurai on 6/28/16.
 */
public class Team extends Member {
    private String teamName;

    public Team(String ID, String firstName, String lastName, String role, String profileImgURL, boolean teamLead, String teamName) {
        super(ID, firstName, lastName, role, profileImgURL, teamLead);
        this.teamName = teamName;
    }

    public Team(){

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


}
