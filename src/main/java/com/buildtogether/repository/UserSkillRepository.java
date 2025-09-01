package com.buildtogether.repository;

import com.buildtogether.entity.UserSkill;
import com.buildtogether.entity.UserSkill.ProficiencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUserId(Long userId);
    List<UserSkill> findBySkillId(Long skillId);
    List<UserSkill> findByProficiencyLevel(ProficiencyLevel proficiencyLevel);
    boolean existsByUserIdAndSkillId(Long userId, Long skillId);
}
