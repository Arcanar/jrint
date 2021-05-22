package com.game.controller;

import com.game.entity.Player;
import com.game.exceptions.PlayerNotFoundException;
import com.game.exceptions.WrongParamsException;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class PlayerController {
    @Autowired
    private PlayerServiceImpl playerService;

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
}
