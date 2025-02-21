package com.epa.micro.song.service.model;

import com.epa.micro.song.service.model.dto.SongDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class Song {
    @Id
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;

    public SongDto toSongDto() {
        return SongDto.builder()
                .album(album)
                .artist(artist)
                .duration(duration)
                .name(name)
                .id(id)
                .year(year)
                .build();
    }
}
