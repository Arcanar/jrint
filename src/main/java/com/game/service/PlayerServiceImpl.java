package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.PlayerNotFoundException;
import com.game.exceptions.WrongParamsException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player getById(Long id){
        //Player player = playerRepository.findById(id).get();
        if(!playerRepository.existsById(id)){
            throw new PlayerNotFoundException("Player not found");
        }
        return playerRepository.findById(id).get();
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(Long id,Player player) {
        if(!playerRepository.existsById(id)){
            throw new PlayerNotFoundException("Player not found");
        }
        checkPlayerParams(player);
        Player updatedPlayer = playerRepository.findById(id).get();
        if(player.getName()!=null) updatedPlayer.setName(player.getName());
        if(player.getTitle()!=null) updatedPlayer.setTitle(player.getTitle());
        if(player.getRace()!=null) updatedPlayer.setRace(player.getRace());
        if(player.getProfession()!=null) updatedPlayer.setProfession(player.getProfession());
        if(player.getBirthday()!=null) updatedPlayer.setBirthday(player.getBirthday());
        if(player.getBanned()!=null) updatedPlayer.setBanned(player.getBanned());
        if(player.getExperience()!=null) updatedPlayer.setExperience(player.getExperience());
        calcLvl(updatedPlayer);
        return playerRepository.save(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        if(!playerRepository.existsById(id)){
            throw new PlayerNotFoundException("Player not found");
        }
        playerRepository.deleteById(id);
    }

    @Override
    public Player createPlayer(Player player) {
        if(player.getName() == null
                ||player.getTitle()==null
                ||player.getRace()==null
                ||player.getProfession()==null
                ||player.getBirthday()==null
                ||player.getExperience()==null
                ||player.getBirthday().getTime()<0){
            throw new WrongParamsException("One of the parameters cant be null");
        }
        if(player.getBanned()==null){
            player.setBanned(false);
        }
        checkPlayerParams(player);
        calcLvl(player);
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getSortedPlayers(PlayerOrder... params) {
        return null;
    }

    public Long checkId(String id){
        long correctId = 0;
        if(id==null||id.equals("")||id.equals("0")){
            throw new WrongParamsException("Wrong Id");
        }
        try {
            correctId = Long.parseLong(id);
            return correctId;
        } catch (NumberFormatException e){
            throw new WrongParamsException("Wrong id format");
        }
    }

    public void calcLvl(Player player){
        int lvl =(int) (Math.sqrt(2500+200*player.getExperience())-50)/100;
        int untilNextLvl = 50*(lvl+1)*(lvl+2)-player.getExperience();
        player.setLevel(lvl);
        player.setUntilNextLevel(untilNextLvl);
    }

    public void checkPlayerParams(Player player){
        if(player.getName()!=null) {
            if (player.getName().length() < 1 || player.getName().length() > 12) {
                throw new WrongParamsException("Invalid Player`s name");
            }
        }
        if(player.getTitle()!=null) {
            if (player.getTitle().length() < 1 || player.getTitle().length() > 30) {
                throw new WrongParamsException("Invalid Player`s title");
            }
        }
        if(player.getExperience()!=null) {
            if (player.getExperience() < 0 || player.getExperience() > 10000000) {
                throw new WrongParamsException("Invalid Player`s experience");
            }
        }
        Date playersDate = player.getBirthday();
        if(playersDate!=null) {
            if (playersDate.getYear() < 2000 || playersDate.getYear() > 3000) {
                throw new WrongParamsException("Invalid Player`s birthday");
            }
        }
    }
}
