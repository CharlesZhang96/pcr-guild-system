package com.charleszhang.pcrguildsystem.service;

import com.charleszhang.pcrguildsystem.bean.Member;
import com.charleszhang.pcrguildsystem.dao.MemberDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Charles Zhang
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

    private final MemberDao memberDao;

    private final static String CONDITION_GAME_ID = "gameId";
    private final static String CONDITION_USERNAME = "username";
    private final static String CONDITION_PHONE = "phone";
    private final static String CONDITION_QQ = "qq";
    private final static String CONDITION_WE_CHAT = "weChat";
    private final static String CONDITION_IS_DELETED = "deleted";

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * Save member
     *
     * @param member member
     */
    public void saveMember(Member member) {
        memberDao.save(member);
    }

    /**
     * 'Delete' member by memberId
     *
     * @param memberId memberId
     */
    public void deleteMemberByMemberId(String memberId) {
        memberDao.deleteByMemberId(memberId);
    }

    /**
     * Find member by memberId
     *
     * @param memberId memberId
     * @return member
     */
    public Member findMemberByMemberId(String memberId) {
        return memberDao.findById(memberId).orElse(null);
    }

    public List<Member> findMember(Map<String, String> queryMap) {
        Specification<Member> specification = createSpecification(queryMap);

        return memberDao.findAll(specification);
    }

    public Page<Member> findMember(Map<String, String> queryMap, int page, int size) {
        Specification<Member> specification = createSpecification(queryMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        return memberDao.findAll(specification, pageRequest);
    }

    /**
     * Create query conditions
     *
     * @param queryMap Query map
     * @return query conditions
     */
    private Specification<Member> createSpecification(Map<String, String> queryMap) {
        return (Specification<Member>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            // is_deleted
            predicateList.add(criteriaBuilder.isFalse(root.get(CONDITION_IS_DELETED).as(Boolean.class)));

            List<Predicate> orList = new ArrayList<>();
            if (!StringUtils.isEmpty(queryMap.get(CONDITION_GAME_ID))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_GAME_ID).as(String.class), "%" + queryMap.get(CONDITION_GAME_ID) + "%"));
            }

            if (!StringUtils.isEmpty(queryMap.get(CONDITION_USERNAME))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_USERNAME).as(String.class), "%" + queryMap.get(CONDITION_USERNAME) + "%"));
            }

            if (!StringUtils.isEmpty(queryMap.get(CONDITION_PHONE))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_PHONE).as(String.class), "%" + queryMap.get(CONDITION_PHONE) + "%"));
            }

            if (!StringUtils.isEmpty(queryMap.get(CONDITION_QQ))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_QQ).as(String.class), "%" + queryMap.get(CONDITION_QQ) + "%"));
            }

            if (!StringUtils.isEmpty(queryMap.get(CONDITION_WE_CHAT))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_WE_CHAT).as(String.class), "%" + queryMap.get(CONDITION_WE_CHAT) + "%"));
            }

            if (orList.size() > 0) {
                predicateList.add(criteriaBuilder.or(orList.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
