package com.sg.webservice.interfaces;

import com.sg.commandmodel.player.createplayer.CreatePlayerCommandModel;
import com.sg.commandmodel.player.editplayer.EditPlayerCommandModel;
import com.sg.dto.Player;
import com.sg.viewmodel.player.createplayer.CreatePlayerViewModel;
import com.sg.viewmodel.player.editplayer.EditPlayerViewModel;
import com.sg.viewmodel.player.playerlist.PlayerListViewModel;
import com.sg.viewmodel.player.playerprofile.PlayerProfileViewModel;

public interface PlayerWebService {

    public PlayerListViewModel getPlayerListViewModel(int limit, int offset, int pageNumbers);
    public PlayerProfileViewModel getPlayerProfileViewModel(Long id);

    public CreatePlayerViewModel getCreatePlayerViewModel();
    public EditPlayerViewModel getEditPlayerViewModel(Long id);

    public Player saveCreatePlayerCommandModel(CreatePlayerCommandModel createPlayerCommandModel);
    public Player saveEditPlayerCommandModel(EditPlayerCommandModel editPlayerCommandModel);


}
