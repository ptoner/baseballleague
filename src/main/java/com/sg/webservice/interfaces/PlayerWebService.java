package com.sg.webservice.interfaces;

import com.sg.viewmodel.player.playerlist.PlayerListViewModel;

public interface PlayerWebService {

    public PlayerListViewModel getPlayerListViewModel(int limit, int offset, int pageNumbers);

}
