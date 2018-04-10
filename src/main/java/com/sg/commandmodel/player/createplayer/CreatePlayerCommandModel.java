package com.sg.commandmodel.player.createplayer;

public class CreatePlayerCommandModel {

    private String first;
    private String last;
    private String hometown;
    private Long teamId;
    private Long[] positionIds;

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
