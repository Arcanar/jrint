package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.PlayerNotFoundException;
import com.game.exceptions.WrongParamsException;
import com.game.service.PlayerFilters;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class PlayerController {
    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private PlayerFilters filters;

    @GetMapping(value = "/players/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Player getPlayerById(@PathVariable(value = "id") String id){
        Long correct = playerService.checkId(id);
        return playerService.getById(correct);
    }

    @PostMapping(value = "/players/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Player updatePlayer(@PathVariable(value = "id")String id,@RequestBody Player player){
        /*if(player == null){
            throw new PlayerNotFoundException("Nothin to update");
        }*/
        Long correct = playerService.checkId(id);
        return playerService.updatePlayer(correct,player);
    }

    @DeleteMapping(value = "/players/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable(value = "id")String id){
        Long correct = playerService.checkId(id);
        playerService.deletePlayer(correct);
    }

    @PostMapping(value = "/players")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Player createPlayer(@RequestBody Player player){
        return playerService.createPlayer(player);
    }

    @GetMapping(value ="/players/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getPlayersCount(@RequestParam(value = "name",required = false)String name,
                                   @RequestParam(value = "title",required = false)String title,
                                   @RequestParam(value = "race",required = false) Race race,
                                   @RequestParam(value = "profession",required = false) Profession profession,
                                   @RequestParam(value = "after",required = false)Long after,
                                   @RequestParam(value = "before",required = false)Long before,
                                   @RequestParam(value = "banned",required = false)Boolean banned,
                                   @RequestParam(value = "minExperience",required = false)Integer minExperience,
                                   @RequestParam(value = "maxExperience",required = false)Integer maxExperience,
                                   @RequestParam(value = "minLevel",required = false)Integer minLevel,
                                   @RequestParam(value = "maxLevel",required = false)Integer maxLevel){
        return playerService.getAllPlayers(Specification.where(filters.filterByName(name).and(filters.filterByTitle(title)))
                .and(Specification.where(filters.filterByRace(race)).
                        and(filters.filterByProfession(profession))
                        .and(filters.filterByBirthday(after,before))
                        .and(filters.filterByBan(banned))
                        .and(filters.filterByPlayerExp(minExperience, maxExperience))
                        .and(filters.filterByPlayerLvl(minLevel, maxLevel)))).size();
    }
    @GetMapping(value = "/players")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAllPlayers(@RequestParam(value = "name",required = false)String name,
                                      @RequestParam(value = "title",required = false)String title,
                                      @RequestParam(value = "race",required = false) Race race,
                                      @RequestParam(value = "profession",required = false) Profession profession,
                                      @RequestParam(value = "after",required = false)Long after,
                                      @RequestParam(value = "before",required = false)Long before,
                                      @RequestParam(value = "banned",required = false)Boolean banned,
                                      @RequestParam(value = "minExperience",required = false)Integer minExperience,
                                      @RequestParam(value = "maxExperience",required = false)Integer maxExperience,
                                      @RequestParam(value = "minLevel",required = false)Integer minLevel,
                                      @RequestParam(value = "maxLevel",required = false)Integer maxLevel,
                                      @RequestParam(value = "order",required = false,defaultValue = "ID")PlayerOrder order,
                                      @RequestParam(value = "pageNumber",required = false,defaultValue = "0")Integer pageNumber,
                                      @RequestParam(value = "pageSize",required = false,defaultValue = "3")Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        return playerService.gelAllShips(Specification.where(filters.filterByName(name).and(filters.filterByTitle(title)))
                .and(Specification.where(filters.filterByRace(race)).
                                and(filters.filterByProfession(profession))
                                        .and(filters.filterByBirthday(after,before))
                                                .and(filters.filterByBan(banned))
                                                        .and(filters.filterByPlayerExp(minExperience, maxExperience))
                                                                .and(filters.filterByPlayerLvl(minLevel, maxLevel))),pageable).getContent();
    }
}
