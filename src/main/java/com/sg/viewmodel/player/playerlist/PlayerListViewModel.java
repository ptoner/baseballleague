package com.sg.viewmodel.player.playerlist;

import java.util.List;

public class PlayerListViewModel {

    private List<PlayerViewModel> players;
    private List<Integer> pageNumbers;
    private Integer pageNumber;


    public List<PlayerViewModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerViewModel> players) {
        this.players = players;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
