package com.sg.viewmodel.player.createplayer;



import com.sg.commandmodel.player.createplayer.CreatePlayerCommandModel;

import java.util.List;

public class CreatePlayerViewModel {

    private List<CreateTeamViewModel> teams;
    private List<CreatePositionViewModel> positions;

    //Specifically to handle redisplaying when validation errors happen.
    private CreatePlayerCommandModel createPlayerCommandModel;


    public CreatePlayerCommandModel getCreatePlayerCommandModel() {
        return createPlayerCommandModel;
    }

    public void setCreatePlayerCommandModel(CreatePlayerCommandModel createPlayerCommandModel) {
        this.createPlayerCommandModel = createPlayerCommandModel;
    }


    public List<CreateTeamViewModel> getTeams() {
        return teams;
    }

    public void setTeams(List<CreateTeamViewModel> teams) {
        this.teams = teams;
    }

    public List<CreatePositionViewModel> getPositions() {
        return positions;
    }

    public void setPositions(List<CreatePositionViewModel> positions) {
        this.positions = positions;
    }
}
