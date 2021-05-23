package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlayerFilters {


    public Specification<Player> filterByName(String name){
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }

    public Specification<Player> filterByTitle(String title) {
        return (root, query, cb) -> title == null ? null : cb.like(root.get("title"), "%" + title + "%");
    }

    public Specification<Player> filterByRace(Race race) {
        return (root, query, cb) -> race == null ? null : cb.equal(root.get("race"), race);
    }

    public Specification<Player> filterByProfession(Profession profession) {
        return (root, query, cb) -> profession == null ? null : cb.equal(root.get("profession"), profession);
    }

    public Specification<Player> filterByBirthday(Long after, Long before) {
        return (root, query, cb) -> {
            if (after == null && before == null)
                return null;
            if (after == null) {
                Date before1 = new Date(before);
                return cb.lessThanOrEqualTo(root.get("birthday"), before1);
            }
            if (before == null) {
                Date after1 = new Date(after);
                return cb.greaterThanOrEqualTo(root.get("birthday"), after1);
            }
            Date before1 = new Date(before);
            Date after1 = new Date(after);
            return cb.between(root.get("birthday"), after1, before1);
        };
    }

    public Specification<Player> filterByBan(Boolean isBanned) {
        return (root, query, cb) -> {
            if (isBanned == null)
                return null;
            if (isBanned)
                return cb.isTrue(root.get("banned"));
            else return cb.isFalse(root.get("banned"));
        };
    }

    public Specification<Player> filterByPlayerExp(Integer minExperience, Integer maxExperience) {
        return (root, query, cb) -> {
            if (minExperience == null && maxExperience == null)
                return null;
            if (minExperience == null)
                return cb.lessThanOrEqualTo(root.get("experience"), maxExperience);
            if (maxExperience == null)
                return cb.greaterThanOrEqualTo(root.get("experience"), minExperience);

            return cb.between(root.get("experience"), minExperience, maxExperience);
        };
    }

    public Specification<Player> filterByPlayerLvl(Integer minLevel, Integer maxLevel) {
        return (root, query, cb) -> {
            if (minLevel == null && maxLevel == null)
                return null;
            if (minLevel == null)
                return cb.lessThanOrEqualTo(root.get("level"), maxLevel);
            if (maxLevel == null)
                return cb.greaterThanOrEqualTo(root.get("level"), minLevel);

            return cb.between(root.get("level"), minLevel, maxLevel);
        };
    }
}
