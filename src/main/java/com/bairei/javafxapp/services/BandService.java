package com.bairei.javafxapp.services;

import com.bairei.javafxapp.models.Band;

import java.util.List;

public interface BandService {
    List<Band> findAll();

    void delete(Band band);

    Band save(Band band);
}
