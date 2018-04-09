package com.sg.webservice;

import com.sg.dto.Player;
import com.sg.dto.Team;
import com.sg.service.PlayerService;
import com.sg.service.TeamService;
import com.sg.viewmodel.player.playerlist.PlayerListViewModel;
import com.sg.viewmodel.player.playerlist.PlayerViewModel;
import com.sg.webservice.interfaces.PlayerWebService;
import com.sg.webservice.util.PagingUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlayerWebServiceImpl implements PlayerWebService {

    PlayerService playerService;
    TeamService teamService;

    @Inject
    public PlayerWebServiceImpl(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }


    @Override
    public PlayerListViewModel getPlayerListViewModel(int limit, int offset, int pageNumbers) {

        //Instantiate
        PlayerListViewModel playerListViewModel = new PlayerListViewModel();



        //Look stuff up
        List<Player> players = playerService.list(limit, offset);



        Integer currentPage = PagingUtils.calculatePageNumber(limit, offset);
        List<Integer> pages = PagingUtils.getPageNumbers(currentPage, pageNumbers);



        //Put stuff in
        playerListViewModel.setPageNumber(currentPage);
        playerListViewModel.setPageNumbers(pages);
        playerListViewModel.setPlayers(translate(players));


        return playerListViewModel;
    }

    private List<PlayerViewModel> translate(List<Player> players) {
        List<PlayerViewModel> playerViewModels = new ArrayList<>();

        for (Player player : players) {
            playerViewModels.add(translate(player));
        }

        return playerViewModels;
    }

    private PlayerViewModel translate(Player player) {

        PlayerViewModel playerViewModel = new PlayerViewModel();

        //Note that we don't care if the names match. Designers can be free to name things weird and wear fedoras.
        playerViewModel.setFirst(player.getFirstName());
        playerViewModel.setLast(player.getLastName());
        playerViewModel.setId(player.getId());


        //Since we've separated the UI from the business services, this is the only place
        //we need to care about lazy loading vs eager fetching.
        if (player.getTeam() != null) {
            Team team = teamService.read(player.getTeam().getId());

            playerViewModel.setTeamId(team.getId());
            playerViewModel.setTeamName(team.getNickname());

        }



        return playerViewModel;
    }

}
