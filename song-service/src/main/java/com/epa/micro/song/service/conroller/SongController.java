package com.epa.micro.song.service.conroller;

import com.epa.micro.song.service.model.dto.SongDto;
import com.epa.micro.song.service.service.SongExistsException;
import com.epa.micro.song.service.service.SongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
        var songDto = songService.getSongById(id);
        return ResponseEntity.ok(songDto);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Long>> createSong(@Valid @RequestBody SongDto songDto) {
        var savedSongId = songService.save(songDto);
        return ResponseEntity.ok(Map.of("id", savedSongId));
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Map<String, List<Long>>> deleteSong(
            @RequestParam("id")
            @Size(min = 1, max = 200, message = "Wrong chars number for ids - must me [1,200]")
            @Pattern(regexp = "^[0-9,]+$", message = "Incorrect format ids - Only numbers separated with coma allowed") String ids) {

        var idList = songService.deleteSongs(ids);
        return ResponseEntity.ok(Map.of("ids", idList));
    }
}