package com.crio.starter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.crio.starter.data.Meme;
import com.crio.starter.data.MemeId;
import com.crio.starter.repository.MemeRepository;
import com.crio.starter.repository.SequenceGeneratorService;
import com.crio.starter.service.MemeService;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin("*")
public class MemeController {

    @Autowired
    private MemeService xmemeService;

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }


    /**
     * This is POST Request for Xmeme Application
     *
     * @param xmeme xmeme Object contains Payload for saving memes
     * @return list of All Memes
     */
    


    @PostMapping("/memes")
    public ResponseEntity<Object> createMeme(@Validated @RequestBody Meme meme) {
        if (!xmemeService.memeExist(meme).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (meme.getName() == null || meme.getUrl() == null || meme.getCaption() == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        meme.setId(sequenceGeneratorService.generateSequence(Meme.SEQUENCE_NAME));
               
        Long id = xmemeService.createMeme(meme);

        MemeId memeId = new MemeId();

        memeId.setId(id);
        
        return new ResponseEntity<>(memeId, HttpStatus.CREATED);
    }

    /**
     * This is GET Request for Xmeme Application
     *
     * @param
     * @return list of All Memes
     */
    @GetMapping("/memes")
    public ResponseEntity<Object> getAllMemes() {
        List<Meme> xmemeList;
        xmemeList = xmemeService.getMeme();
        return new ResponseEntity<>(xmemeList, HttpStatus.OK);
    }

    /**
     * This is GET Request for Xmeme Application
     *
     * @param id Parameter to fetch meme by meme Id
     * @return list of All Memes
     */
    @GetMapping("/memes/{id}")
    public ResponseEntity<Object> getMemeById(@PathVariable Long id) {

     
        Optional<Meme> memeEntity = memeRepository.findById(id);
        if (memeEntity.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Meme memeEntitys = memeEntity.get();
        List<Meme> memes = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        memes.add(modelMapper.map(memeEntitys, Meme.class));
        return new ResponseEntity<>(memes.get(0), HttpStatus.OK);

    }


    /**
     * This is PATCH Request for Xmeme Application
     *
     * @param id,xmeme ID for fetching meme by Id and xmeme Object Contains Updated Contents
     * @return HTTP Response
     */
    @PatchMapping("memes/{id}")
    public ResponseEntity<Object> patchMemeById(@PathVariable Long id, @RequestBody Meme xmeme) {
        Optional<Meme> meme = xmemeService.getMemeById(id);
        if (meme.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!xmemeService.memeExist(xmeme).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        meme.get().setUrl(xmeme.getUrl());
        meme.get().setCaption(xmeme.getCaption());
        xmemeService.updateMeme(meme);
        return new ResponseEntity<>(meme, HttpStatus.OK);

    }

    @DeleteMapping(value = "/memes/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        Optional<Meme> memeOptional = memeRepository.findById(id);
        if (!memeOptional.isPresent()) {
            return ResponseEntity.notFound().build();

        }
        xmemeService.deleteMemeById(id);

        return ResponseEntity.noContent().build();

    }
}
