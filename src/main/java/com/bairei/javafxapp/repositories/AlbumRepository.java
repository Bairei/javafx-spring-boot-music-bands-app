package com.bairei.javafxapp.repositories;

import com.bairei.javafxapp.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository <Album, Long> {
    List<Album> findAlbumsByTitleContainingIgnoreCase(String title);
    List<Album> findAlbumsByYearOfReleaseBetween(Integer from, Integer to);
    List<Album> findAlbumsByTitleContainingIgnoreCaseAndYearOfReleaseBetween(String title, Integer from, Integer to);
}
