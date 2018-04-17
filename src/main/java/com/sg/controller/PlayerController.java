package com.sg.controller;


import com.sg.commandmodel.player.createplayer.CreatePlayerCommandModel;
import com.sg.commandmodel.player.editplayer.EditPlayerCommandModel;
import com.sg.dto.Player;
import com.sg.viewmodel.player.createplayer.CreatePlayerViewModel;
import com.sg.viewmodel.player.editplayer.EditPlayerViewModel;
import com.sg.viewmodel.player.playerlist.PlayerListViewModel;
import com.sg.viewmodel.player.playerprofile.PlayerProfileViewModel;
import com.sg.webservice.interfaces.PlayerWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/player")
public class PlayerController {

    @Inject
    PlayerWebService playerWebService;


    @RequestMapping(value = "/list")
    public String list(@RequestParam(required = false) Integer offset, Model model) {

        PlayerListViewModel viewModel = playerWebService.getPlayerListViewModel(5, offset, 15);

        model.addAttribute("viewModel", viewModel);

        return "player/list";
    }


    @RequestMapping(value = "/show")
    public String show(@RequestParam Long id, Model model) {

        PlayerProfileViewModel viewModel = playerWebService.getPlayerProfileViewModel(id);

        model.addAttribute("viewModel", viewModel);

        return "player/show";
    }


    // SHOW FORM
    @RequestMapping(value = "/edit")
    public String edit(@RequestParam Long id, Model model) {

        EditPlayerViewModel viewModel = playerWebService.getEditPlayerViewModel(id);

        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getEditPlayerCommandModel());

        return "player/edit";
    }

    //HANDLE FORM SUBMISSION
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit(@Valid @ModelAttribute("commandModel") EditPlayerCommandModel commandModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            EditPlayerViewModel viewModel = playerWebService.getEditPlayerViewModel(commandModel.getId());

            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", commandModel);

            return "player/edit";
        }

        Player player = playerWebService.saveEditPlayerCommandModel(commandModel);


        return "redirect:/player/show?id=" + player.getId();
    }




    // SHOW FORM
    @RequestMapping(value = "/create")
    public String create(Model model) {

        CreatePlayerViewModel viewModel = playerWebService.getCreatePlayerViewModel();

        model.addAttribute("viewModel", viewModel);
        model.addAttribute("commandModel", viewModel.getCreatePlayerCommandModel());

        return "player/create";
    }

    //HANDLE FORM SUBMISSION
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveCreate(@Valid @ModelAttribute("commandModel") CreatePlayerCommandModel commandModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            CreatePlayerViewModel viewModel = playerWebService.getCreatePlayerViewModel();

            model.addAttribute("viewModel", viewModel);
            model.addAttribute("commandModel", commandModel);

            return "player/create";
        }

        Player player = playerWebService.saveCreatePlayerCommandModel(commandModel);


        return "redirect:/player/show?id=" + player.getId();
    }









}
