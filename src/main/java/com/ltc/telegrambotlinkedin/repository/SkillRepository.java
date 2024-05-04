package com.ltc.telegrambotlinkedin.repository;

import com.ltc.telegrambotlinkedin.entity.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s WHERE s.skillName = :skillName")
    Skill findSkill(@Param(value = "skillName") String skillName);
}
