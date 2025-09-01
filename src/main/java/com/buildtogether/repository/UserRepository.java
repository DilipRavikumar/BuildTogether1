package com.buildtogether.repository;

import com.buildtogether.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByRole(User.UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% OR u.email LIKE %:name%")
    List<User> findByNameContainingOrEmailContaining(@Param("name") String name);
    
    @Query("SELECT u FROM User u JOIN u.userSkills us JOIN us.skill s WHERE s.skillName = :skillName")
    List<User> findBySkillName(@Param("skillName") String skillName);
    
    @Query("SELECT u FROM User u JOIN u.teamMemberships tm JOIN tm.team t WHERE t.id = :teamId")
    List<User> findByTeamId(@Param("teamId") Long teamId);
}
