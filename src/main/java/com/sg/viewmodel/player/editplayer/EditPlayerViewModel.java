package com.sg.viewmodel.player.editplayer;



import com.sg.commandmodel.player.editplayer.EditPlayerCommandModel;

import java.util.List;

public class EditPlayerViewModel {

    private List<EditTeamViewModel> teams;
    private List<EditPositionViewModel> positions;

    //Specifically to handle redisplaying when validation errors happen.
    private EditPlayerCommandModel editPlayerCommandModel;


    public EditPlayerCommandModel getEditPlayerCommandModel() {
        return editPlayerCommandModel;
    }

    public void setEditPlayerCommandModel(EditPlayerCommandModel editPlayerCommandModel) {
        this.editPlayerCommandModel = editPlayerCommandModel;
    }

    public List<EditTeamViewModel> getTeams() {
        return teams;
    }

    public void setTeams(List<EditTeamViewModel> teams) {
        this.teams = teams;
    }

    public List<EditPositionViewModel> getPositions() {
        return positions;
    }

    public void setPositions(List<EditPositionViewModel> positions) {
        this.positions = positions;
    }
}
