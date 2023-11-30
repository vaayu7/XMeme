package com.crio.starter.service;

import java.util.List;
import java.util.Optional;
import com.crio.starter.data.Meme;

public interface IMemeService {
    Long createMeme(Meme xmeme);

    List<Meme> getMeme();

    Optional<Meme> getMemeById(Long id);

    void updateMeme(Optional<Meme> meme);

    List<Meme> memeExist(Meme meme);
}
