package com.crio.starter.repository;

import java.util.Objects;
import com.crio.starter.data.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service

public class SequenceGeneratorService {

    @Autowired
    MongoTemplate mongoTemplate;
    
    public Long generateSequence(String seqName) {

        Query query = new Query(Criteria.where("id").is(seqName));
        DatabaseSequence counter =
                mongoTemplate.findAndModify(query, new Update().inc("sequenceNo", 1),
                        FindAndModifyOptions.options().returnNew(true).upsert(true),
                        DatabaseSequence.class, "database_sequences");
        return !Objects.isNull(counter) ? counter.getSequenceNo() : 1;
    }


}
