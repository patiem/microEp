package com.epa.micro.song.service.repository;

import com.epa.micro.song.service.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SongRepository extends JpaRepository<Song, Long> {
}