package com.sg.commandmodel.player.editplayer;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class EditPlayerCommandModel {

    private Long id;

    @NotEmpty(message = "HELLO USER YOU DIDNT FILL THIS IN AND ID REALLY LIKE IT IF YOU FILLED IT IN")
    @Length(min = 4, message = "Your name is too short")
    private String first;


    private String last;


    private String hometown;
    private Long teamId;
    private Long[] positionIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long[] getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(Long[] positionIds) {
        this.positionIds = positionIds;
    }
}
