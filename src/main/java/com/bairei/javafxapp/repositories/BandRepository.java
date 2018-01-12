package com.bairei.javafxapp.repositories;

import com.bairei.javafxapp.models.Band;
import com.bairei.javafxapp.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandRepository extends JpaRepository <Band, Long> {
    List<Band> findBandsByNameContainingIgnoreCaseAndYearFoundedBetween(String name, Integer from, Integer to);
    List<Band> findBandsByYearFoundedBetween(Integer from, Integer to);
}
