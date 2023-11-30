package com.crio.starter.repository;

import java.util.List;
import java.util.Optional;
import com.crio.starter.data.Meme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends MongoRepository<Meme,Long>{
    List<Meme> findAllByOrderByIdDesc();
   
   public Optional<Meme> findById(Long id);
  
    
}
