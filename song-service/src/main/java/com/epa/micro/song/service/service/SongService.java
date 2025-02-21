package com.epa.micro.song.service.service;

import com.epa.micro.song.service.model.dto.SongDto;
import com.epa.micro.song.service.repository.SongRepository;
import com.epa.micro.song.service.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public Long save(SongDto songDto) {
        if(songRepository.existsById(songDto.getId())) throw new SongExistsException(String.format("Song with ID=%d already exists", songDto.getId()));
        return songRepository.save(songDto.toSong()).getId();
    }

    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Song with ID=%d not found", id)));
        return song.toSongDto();
    }

    public List<Long> deleteSongs(String idsToDelete) {
        List<Long> existingIds = Arrays.stream(idsToDelete.split(","))
                .map(Long::valueOf)
                .filter(id -> songRepository.existsById(id))
                .toList();
        songRepository.deleteAllById(existingIds);
        return existingIds;
    }
}
