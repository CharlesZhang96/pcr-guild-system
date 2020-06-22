package com.charleszhang.pcrguildsystem.controller;

import com.charleszhang.pcrguildsystem.service.CharacterService;
import com.charleszhang.pcrguildsystem.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * @author Charles Zhang
 */
@Controller
public class PageViewController {

    private final MemberService memberService;
    private final CharacterService characterService;

    public PageViewController(MemberService memberService, CharacterService characterService) {
        this.memberService = memberService;
        this.characterService = characterService;
    }

    @RequestMapping("/home")
    public String jumpHome() {
        return "home";
    }

    @RequestMapping("/member/member-list")
    public String jumpMemberList(Model model) {
        model.addAttribute("memberList", memberService.findMember(new HashMap<>(1)));

        return "member/member-list";
    }

    @RequestMapping("/member/member-box-list")
    public String jumpMemberBoxList() {
        return "member/member-box-list";
    }

    @RequestMapping("/character/character-list")
    public String jumpCharacterList(Model model) {
        model.addAttribute("characterList", characterService.findCharacter(new HashMap<>(1)));

        return "character/character-list";
    }

    @RequestMapping("/battle/team-list")
    public String jumpTeamList() {
        return "battle/team-list";
    }

    @RequestMapping("/battle/battle-list")
    public String jumpBattleList() {
        return "battle/battle-list";
    }

    @RequestMapping("/battle/boss-list")
    public String jumpBossList() {
        return "battle/boss-list";
    }

    @RequestMapping("/battle/battle-statistic")
    public String jumpBattleStatistic() {
        return "battle/battle-statistic";
    }
}
