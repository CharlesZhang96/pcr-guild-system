package com.charleszhang.pcrguildsystem.dao;

import com.charleszhang.pcrguildsystem.bean.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Charles Zhang
 */
@Repository
public interface CharacterDao extends JpaRepository<Character, String>, JpaSpecificationExecutor<Character> {

    /**
     * 'Delete' member by characterId (set isDeleted true)
     *
     * @param characterId characterId
     */
    @Modifying
    @Query("UPDATE Character c SET c.deleted = true, c.gmtModified = CURRENT_TIMESTAMP WHERE c.characterId = :characterId")
    void deleteByCharacterId(@Param("characterId") String characterId);
}
