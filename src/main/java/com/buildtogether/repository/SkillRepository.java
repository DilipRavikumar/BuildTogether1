package com.buildtogether.repository;

import com.buildtogether.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillName(String skillName);
    boolean existsBySkillName(String skillName);
    List<Skill> findBySkillNameContainingIgnoreCase(String skillName);
}
