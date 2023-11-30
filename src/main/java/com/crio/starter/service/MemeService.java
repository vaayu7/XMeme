package com.crio.starter.service;

import java.util.List;
import java.util.Optional;
import com.crio.starter.data.Meme;
import com.crio.starter.repository.MemeRepository;
import com.crio.starter.repository.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemeService implements IMemeService {


    @Autowired
    private MemeRepository memeRepository;
    
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;



    public MemeService() {

    }


    @Override
    public Long createMeme(Meme xmeme) {
        // TODO Auto-generated method stub

        memeRepository.save(xmeme);
        return xmeme.getId();
    }


    @Override
    public List<Meme> getMeme() {
        // TODO Auto-generated method stub
        Page<Meme> memes =
                memeRepository.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "id")));
        return memes.getContent();

    }

    @Override
    public Optional<Meme> getMemeById(Long id) {
        // TODO Auto-generated method stub

        return memeRepository.findById(id);
    }


    @Override
    public void updateMeme(Optional<Meme> meme) {
        // TODO Auto-generated method stub
        memeRepository.save(meme.get());
    }


    @Override
    public List<Meme> memeExist(Meme meme) {
        // TODO Auto-generated method stub
        Meme meme2 = new Meme();
        meme2.setName(meme.getName());
        meme2.setUrl(meme.getUrl());
        meme2.setCaption(meme.getCaption());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id");
        Example<Meme> memeExample = Example.of(meme2, exampleMatcher);
        return memeRepository.findAll(memeExample);

    }



    public boolean deleteMemeById(Long id) {

        Optional<Meme> meme = memeRepository.findById(id);
        Meme me=meme.get();
        memeRepository.delete(me);

        return true;
    }
}