package com.epa.micro.song.service.model.dto;

import com.epa.micro.song.service.model.Song;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SongDto {

    @Positive(message = "Resource Id invalid")
    private Long id;

    @NotBlank(message = "Song name cannot be blank")
    @Size(max = 100, message = "Song name too long, max length 100 chars")
    private String name;

    @NotBlank(message = "Artist cannot be blank")
    @Size(max = 100, message = "Artist too long, max length 100 chars")
    private String artist;

    @NotBlank(message = "Album cannot be blank")
    @Size(max = 100, message = "Album too long, max length 100 chars")
    private String album;

    @Pattern(regexp = "[0-9]{1,2}:[0-9]{2}", message = "Invalid Duration format, required MM:SS")
    private String duration;

    @Pattern(regexp = "19[0-9]{2}|20[0-9]{2}", message = "Invalid Year, required YYYY format and year between 1900-2099")
    private String year;


    public Song toSong() {
        return Song.builder()
                .album(album)
                .artist(artist)
                .duration(duration)
                .name(name)
                .id(id)
                .year(year)
                .build();
    }
}
