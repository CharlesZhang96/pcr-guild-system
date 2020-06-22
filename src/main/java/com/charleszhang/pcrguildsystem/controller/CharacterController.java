package com.charleszhang.pcrguildsystem.controller;

import com.charleszhang.pcrguildsystem.bean.Character;
import com.charleszhang.pcrguildsystem.bean.common.PageResult;
import com.charleszhang.pcrguildsystem.bean.common.Result;
import com.charleszhang.pcrguildsystem.service.CharacterService;
import com.charleszhang.pcrguildsystem.util.Constants;
import com.charleszhang.pcrguildsystem.util.IdWorker;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Charles Zhang
 */
@Controller
@ResponseBody
@RequestMapping(value = "/character")
public class CharacterController {

    private final CharacterService characterService;
    private final IdWorker idWorker = new IdWorker(1, 1);

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addCharacter(@RequestBody Character character) {
        character.setCharacterId(String.valueOf(idWorker.nextId()));
        character.setDeleted(false);
        character.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        character.setGmtModified(new Timestamp(System.currentTimeMillis()));

        characterService.saveCharacter(character);

        return new Result(true, Constants.SUCCESS, "Add success!");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result updateCharacter(@RequestBody Character character) {
        if (StringUtils.isEmpty(character.getCharacterId())) {
            return new Result(false, Constants.ERROR, "Character Id does not exist, update failed.");
        }

        Character oldCharacter = characterService.findCharacterByCharacterId(character.getCharacterId());
        oldCharacter.setCharName(character.getCharName());
        oldCharacter.setNickname(character.getNickname());
        oldCharacter.setAvatarUrl(character.getAvatarUrl());
        oldCharacter.setGmtModified(new Timestamp(System.currentTimeMillis()));

        characterService.saveCharacter(oldCharacter);

        return new Result(true, Constants.SUCCESS, "Update success!");
    }

    @RequestMapping(value = "/{characterId}", method = RequestMethod.DELETE)
    public Result deleteCharacter(@PathVariable String characterId) {
        characterService.deleteCharacterByCharacterId(characterId);

        return new Result(true, Constants.SUCCESS, "Delete success!");
    }

    @RequestMapping(value = "/{characterId}", method = RequestMethod.GET)
    public Result findByCharacterId(@PathVariable String characterId) {
        return new Result(true, Constants.SUCCESS, "Query success!", characterService.findCharacterByCharacterId(characterId));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findCharacter(@RequestBody Map<String, String> queryMap) {
        return new Result(true, Constants.SUCCESS, "Query success!", characterService.findCharacter(queryMap));
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findCharacter(@RequestBody Map<String, String> queryMap, @PathVariable int page, @PathVariable int size) {
        Page<Character> characterPage = characterService.findCharacter(queryMap, page, size);

        return new Result(true, Constants.SUCCESS, "Query success!", new PageResult<>(characterPage.getTotalElements(), characterPage.getContent()));
    }
}
