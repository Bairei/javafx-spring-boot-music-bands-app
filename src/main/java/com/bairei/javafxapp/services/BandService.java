package com.bairei.javafxapp.services;

import com.bairei.javafxapp.models.Band;

import java.util.List;

public interface BandService {
    List<Band> findAll();

    void delete(Band band);

    Band save(Band band);

    List<Band> findBandsByNameContainingIgnoreCaseAndYearFoundedBetween(String phrase, Integer from, Integer to);

    List<Band> findBandsByYearFoundedBetween(Integer from, Integer to);
}
