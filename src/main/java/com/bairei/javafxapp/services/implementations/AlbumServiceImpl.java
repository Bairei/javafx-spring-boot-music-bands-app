package com.bairei.javafxapp.services.implementations;

import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.models.Band;
import com.bairei.javafxapp.repositories.AlbumRepository;
import com.bairei.javafxapp.repositories.BandRepository;
import com.bairei.javafxapp.services.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final BandRepository bandRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, BandRepository bandRepository){
        this.albumRepository = albumRepository;
        this.bandRepository = bandRepository;
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Album selectedAlbum) {
        Band band = selectedAlbum.getBand();
        List<Album> albumList = Collections.synchronizedList(new ArrayList<>(band.getAlbums()));
        // log.info(albumList.toString());
        for (int i = 0; i < albumList.size(); i++){
            if (albumList.get(i).getId().equals(selectedAlbum.getId())){
                albumList.remove(i);
                break;
            }
        }
        band.setAlbums(new HashSet<>(albumList));
        // log.info(String.valueOf(albumList.size()));
        bandRepository.save(band);
        albumRepository.delete(selectedAlbum);
    }

    @Override
    public Album save(Album selectedAlbum) {
        return albumRepository.save(selectedAlbum);
    }
}
