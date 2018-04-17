package com.sg.webservice;

import com.sg.commandmodel.team.createteam.CreateTeamCommandModel;
import com.sg.commandmodel.team.editteam.EditTeamCommandModel;
import com.sg.dto.Team;
import com.sg.service.TeamService;
import com.sg.viewmodel.team.createteam.CreateTeamViewModel;
import com.sg.viewmodel.team.editteam.EditTeamViewModel;
import com.sg.viewmodel.team.teamlist.TeamListViewModel;
import com.sg.viewmodel.team.teamlist.TeamViewModel;
import com.sg.viewmodel.team.teamprofile.TeamProfileViewModel;
import com.sg.webservice.interfaces.TeamWebService;
import com.sg.webservice.util.PagingUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TeamWebServiceImpl implements TeamWebService {

    TeamService teamService;

    @Inject
    public TeamWebServiceImpl(TeamService teamService) {
        this.teamService = teamService;
    }


    @Override
    public TeamListViewModel getTeamListViewModel(Integer limit, Integer offset, Integer pagesToShow) {

        //Set defaults
        if (limit == null) limit = 5;
        if (offset == null) offset = 0;
        if (pagesToShow == null) pagesToShow = 5;

        //Instantiate view model
        TeamListViewModel teamListViewModel = new TeamListViewModel();


        //Figure out stuff to put into it.
        List<Team> teams = teamService.list(limit, offset);

        Integer currentPage = PagingUtils.calculatePageNumber(limit, offset);
        List<Integer> pages = PagingUtils.getPageNumbers(currentPage, pagesToShow);


        //Put the stuff into it.
        teamListViewModel.setTeams(translate(teams));
        teamListViewModel.setPageNumber(currentPage);
        teamListViewModel.setPageNumbers(pages);


        return teamListViewModel;
    }

    //Translate a list
    private List<TeamViewModel> translate(List<Team> teams) {
        List<TeamViewModel> teamViewModels = new ArrayList<>();

        for (Team team : teams) {
            teamViewModels.add(translate(team));
        }

        return teamViewModels;
    }

    //Translate an individual one
    private TeamViewModel translate(Team team) {
        TeamViewModel teamViewModel = new TeamViewModel();

        teamViewModel.setId(team.getId());
        teamViewModel.setCity(team.getCity());
        teamViewModel.setNickname(team.getNickname());


        return teamViewModel;
    }



    @Override
    public TeamProfileViewModel getTeamProfileViewModel(Long id) {

        //Instantiate view model
        TeamProfileViewModel teamProfileViewModel = new TeamProfileViewModel();

        //Look up stuff
        Team team = teamService.read(id);


        //Put stuff
        teamProfileViewModel.setId(team.getId());
        teamProfileViewModel.setCity(team.getCity());
        teamProfileViewModel.setNickname(team.getNickname());


        return teamProfileViewModel;
    }

    @Override
    public CreateTeamViewModel getCreateTeamViewModel() {
        return new CreateTeamViewModel();
    }

    @Override
    public EditTeamViewModel getEditTeamViewModel() {
        return new EditTeamViewModel();
    }

    @Override
    public EditTeamCommandModel getEditTeamCommandModel(Long id) {

        //Instantiate command model
        EditTeamCommandModel editTeamCommandModel = new EditTeamCommandModel();


        //Look up stuff
        Team team = teamService.read(id);

        //Put stuff
        editTeamCommandModel.setId(team.getId());
        editTeamCommandModel.setCity(team.getCity());
        editTeamCommandModel.setNickname(team.getNickname());


        return editTeamCommandModel;
    }

    @Override
    public Team saveCreateTeamCommandModel(CreateTeamCommandModel createTeamCommandModel) {

        //Translate onto domain object
        Team team = new Team();
        team.setCity(createTeamCommandModel.getCity());
        team.setNickname(createTeamCommandModel.getNickname());

        //Save
        Team createdTeam = teamService.create(team);


        return createdTeam;
    }

    @Override
    public void saveEditTeamCommandModel(EditTeamCommandModel editTeamCommandModel) {

        //Look up existing
        Team existingTeam = teamService.read(editTeamCommandModel.getId());

        //Translate
        existingTeam.setCity(editTeamCommandModel.getCity());
        existingTeam.setNickname(editTeamCommandModel.getNickname());

        //Save
        teamService.update(existingTeam);


    }
}
