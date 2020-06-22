package com.charleszhang.pcrguildsystem.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Charles Zhang
 */
@Entity
@Table(name = "pcr_character")
public class Character {
    private String characterId;
    private String charName;
    private String nickname;
    private String avatarUrl;
    private Boolean isDeleted;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Id
    @Column(name = "character_id")
    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    @Basic
    @Column(name = "char_name")
    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

        Character character = (Character) o;

        return Objects.equals(characterId, character.characterId) &&
                Objects.equals(charName, character.charName) &&
                Objects.equals(nickname, character.nickname) &&
                Objects.equals(avatarUrl, character.avatarUrl) &&
                Objects.equals(gmtCreate, character.gmtCreate) &&
                Objects.equals(gmtModified, character.gmtModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterId, charName, nickname, avatarUrl, gmtCreate, gmtModified);
    }
}
