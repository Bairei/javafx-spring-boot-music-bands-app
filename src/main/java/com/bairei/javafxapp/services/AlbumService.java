package com.bairei.javafxapp.services;

import com.bairei.javafxapp.models.Album;

import java.util.List;

public interface AlbumService {
    List<Album> findAll();

    void delete(Album selectedAlbum);

    Album save(Album selectedAlbum);

    List<Album> findAlbumsByTitleContainingIgnoreCaseAndYearOfReleaseBetween(String phrase, Integer from, Integer to);

    List<Album> findAlbumsByYearOfReleaseBetween(Integer from, Integer to);
}
