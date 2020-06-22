package com.charleszhang.pcrguildsystem.controller;

import com.charleszhang.pcrguildsystem.bean.Member;
import com.charleszhang.pcrguildsystem.bean.common.PageResult;
import com.charleszhang.pcrguildsystem.bean.common.Result;
import com.charleszhang.pcrguildsystem.service.MemberService;
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
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;
    private final IdWorker idWorker = new IdWorker(1, 1);

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addMember(@RequestBody Member member) {
        member.setMemberId(String.valueOf(idWorker.nextId()));
        member.setDeleted(false);
        member.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        member.setGmtModified(new Timestamp(System.currentTimeMillis()));

        memberService.saveMember(member);

        return new Result(true, Constants.SUCCESS, "Add success!");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result updateMember(@RequestBody Member member) {
        if (StringUtils.isEmpty(member.getMemberId())) {
            return new Result(false, Constants.ERROR, "Member Id does not exist, update failed.");
        }

        Member oldMember = memberService.findMemberByMemberId(member.getMemberId());
        oldMember.setGameId(member.getGameId());
        oldMember.setUsername(member.getUsername());
        oldMember.setLevel(member.getLevel());
        oldMember.setPhone(member.getPhone());
        oldMember.setQq(member.getQq());
        oldMember.setWeChat(member.getWeChat());
        oldMember.setGmtModified(new Timestamp(System.currentTimeMillis()));

        memberService.saveMember(oldMember);

        return new Result(true, Constants.SUCCESS, "Update success!");
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.DELETE)
    public Result deleteMember(@PathVariable String memberId) {
        memberService.deleteMemberByMemberId(memberId);

        return new Result(true, Constants.SUCCESS, "Delete success!");
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.GET)
    public Result findByMemberId(@PathVariable String memberId) {
        return new Result(true, Constants.SUCCESS, "Query success!", memberService.findMemberByMemberId(memberId));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findMember(@RequestBody Map<String, String> queryMap) {
        return new Result(true, Constants.SUCCESS, "Query success!", memberService.findMember(queryMap));
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findMember(@RequestBody Map<String, String> queryMap, @PathVariable int page, @PathVariable int size) {
        Page<Member> memberPage = memberService.findMember(queryMap, page, size);

        return new Result(true, Constants.SUCCESS, "Query success!", new PageResult<>(memberPage.getTotalElements(), memberPage.getContent()));
    }
}
