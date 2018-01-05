package com.bairei.javafxapp.repositories;

import com.bairei.javafxapp.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository <Member, Long> {
    List<Member> findMembersByNameContainingIgnoreCase(String name);
}
