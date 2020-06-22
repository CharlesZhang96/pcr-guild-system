package com.charleszhang.pcrguildsystem.dao;

import com.charleszhang.pcrguildsystem.bean.Member;
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
public interface MemberDao extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {

    /**
     * 'Delete' member by memberId (set isDeleted true)
     *
     * @param memberId memberId
     */
    @Modifying
    @Query("UPDATE Member m SET m.deleted = true, m.gmtModified = CURRENT_TIMESTAMP WHERE m.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") String memberId);
}
