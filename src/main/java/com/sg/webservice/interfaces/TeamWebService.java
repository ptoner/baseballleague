package com.sg.webservice.interfaces;

import com.sg.commandmodel.createteam.CreateTeamCommandModel;
import com.sg.commandmodel.editteam.EditTeamCommandModel;
import com.sg.dto.Team;
import com.sg.viewmodel.team.createteam.CreateTeamViewModel;
import com.sg.viewmodel.team.editteam.EditTeamViewModel;
import com.sg.viewmodel.team.teamlist.TeamListViewModel;
import com.sg.viewmodel.team.teamprofile.TeamProfileViewModel;

public interface TeamWebService {

    public TeamListViewModel getTeamListViewModel(int limit, int offset, int pageNumbers);
    public TeamProfileViewModel getTeamProfileViewModel(Long id);

    public CreateTeamViewModel getCreateTeamViewModel();
    public EditTeamViewModel getEditTeamViewModel();

    public EditTeamCommandModel getEditTeamCommandModel(Long id);


    //Save command models
    public Team saveCreateTeamCommandModel(CreateTeamCommandModel createTeamCommandModel);
    public void saveEditTeamCommandModel(EditTeamCommandModel editTeamCommandModel);


}
