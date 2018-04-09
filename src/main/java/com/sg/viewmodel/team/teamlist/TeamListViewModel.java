package com.sg.viewmodel.team.teamlist;

import java.util.List;

public class TeamListViewModel {

    private List<TeamViewModel> teams;
    private List<Integer> pageNumbers;
    private Integer pageNumber;


    public List<TeamViewModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamViewModel> teams) {
        this.teams = teams;
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
