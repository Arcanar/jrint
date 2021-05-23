package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.exceptions.PlayerNotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PlayerService {
    Player getById(Long id) throws PlayerNotFoundException;
    List<Player> getAllPlayers(Specification<Player> specification);
    Player updatePlayer(Long id,Player player);
    void deletePlayer(Long id);
    Player createPlayer(Player player);
    List<Player> getSortedPlayers(PlayerOrder...params);

}
