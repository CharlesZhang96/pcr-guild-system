package com.charleszhang.pcrguildsystem.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Charles Zhang
 */
@Entity
@Table(name = "pcr_member")
public class Member {
    private String memberId;
    private String gameId;
    private String username;
    private int level;
    private String phone;
    private String qq;
    private String weChat;
    private Boolean isDeleted;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Id
    @Column(name = "member_id")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "game_id")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }


    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "we_chat")
    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    @Basic
    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Member member = (Member) o;

        return memberId.equals(member.memberId) &&
                Objects.equals(gameId, member.gameId) &&
                Objects.equals(username, member.username) &&
                Objects.equals(phone, member.phone) &&
                Objects.equals(qq, member.qq) &&
                Objects.equals(weChat, member.weChat) &&
                Objects.equals(isDeleted, member.isDeleted) &&
                Objects.equals(gmtCreate, member.gmtCreate) &&
                Objects.equals(gmtModified, member.gmtModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, gameId, username, phone, qq, weChat, isDeleted, gmtCreate, gmtModified);
    }
}
