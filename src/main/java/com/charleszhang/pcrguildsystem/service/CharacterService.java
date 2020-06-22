package com.charleszhang.pcrguildsystem.service;

import com.charleszhang.pcrguildsystem.bean.Character;
import com.charleszhang.pcrguildsystem.dao.CharacterDao;
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
public class CharacterService {

    private final CharacterDao characterDao;

    private final static String CONDITION_CHARACTER_ID = "characterId";
    private final static String CONDITION_CHAR_NAME = "charName";
    private final static String CONDITION_NICK_NAME = "nickname";
    private final static String CONDITION_IS_DELETED = "deleted";

    public CharacterService(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }

    /**
     * Save character
     *
     * @param character character
     */
    public void saveCharacter(Character character) {
        characterDao.save(character);
    }

    /**
     * 'Delete' character by characterId
     *
     * @param characterId characterId
     */
    public void deleteCharacterByCharacterId(String characterId) {
        characterDao.deleteByCharacterId(characterId);
    }

    /**
     * Find character by characterId
     *
     * @param characterId characterId
     * @return character
     */
    public Character findCharacterByCharacterId(String characterId) {
        return characterDao.findById(characterId).orElse(null);
    }

    public List<Character> findCharacter(Map<String, String> queryMap) {
        Specification<Character> specification = createSpecification(queryMap);

        return characterDao.findAll(specification);
    }

    public Page<Character> findCharacter(Map<String, String> queryMap, int page, int size) {
        Specification<Character> specification = createSpecification(queryMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        return characterDao.findAll(specification, pageRequest);
    }

    /**
     * Create query conditions
     *
     * @param queryMap Query map
     * @return query conditions
     */
    private Specification<Character> createSpecification(Map<String, String> queryMap) {
        return (Specification<Character>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            // is_deleted
            predicateList.add(criteriaBuilder.isFalse(root.get(CONDITION_IS_DELETED).as(Boolean.class)));

            List<Predicate> orList = new ArrayList<>();
            if (!StringUtils.isEmpty(queryMap.get(CONDITION_CHAR_NAME))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_CHAR_NAME).as(String.class), "%" + queryMap.get(CONDITION_CHAR_NAME) + "%"));
            }

            if (!StringUtils.isEmpty(queryMap.get(CONDITION_NICK_NAME))) {
                orList.add(criteriaBuilder.like(root.get(CONDITION_NICK_NAME).as(String.class), "%" + queryMap.get(CONDITION_NICK_NAME) + "%"));
            }

            if (orList.size() > 0) {
                predicateList.add(criteriaBuilder.or(orList.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
