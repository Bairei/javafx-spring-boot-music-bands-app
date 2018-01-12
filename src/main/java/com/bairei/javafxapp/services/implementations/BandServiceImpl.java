package com.bairei.javafxapp.services.implementations;

import com.bairei.javafxapp.models.Band;
import com.bairei.javafxapp.repositories.BandRepository;
import com.bairei.javafxapp.services.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    @Autowired
    public BandServiceImpl (BandRepository bandRepository){
        this.bandRepository = bandRepository;
    }

    @Override
    public List<Band> findAll() {
        return bandRepository.findAll();
    }

    @Override
    public void delete(Band band) {
        bandRepository.delete(band);
    }

    @Override
    public Band save(Band band) {
        return bandRepository.save(band);
    }

    @Override
    public List<Band> findBandsByNameContainingIgnoreCaseAndYearFoundedBetween(String phrase, Integer from, Integer to) {
        return bandRepository.findBandsByNameContainingIgnoreCaseAndYearFoundedBetween(phrase, from, to);
    }

    @Override
    public List<Band> findBandsByYearFoundedBetween(Integer from, Integer to) {
        return bandRepository.findBandsByYearFoundedBetween(from, to);
    }
}
