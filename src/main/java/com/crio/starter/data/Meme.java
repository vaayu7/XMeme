package com.crio.starter.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "Meme")

public class Meme {
   
    @Field("id")
    private @Getter @Setter Long id;// Primary Key

    // fields for entity Xmemes

    private @Getter @Setter String name;
    
    private @Getter @Setter String caption;
    
    private @Getter @Setter String url;

    @Transient
    public static final String SEQUENCE_NAME = "posts_sequence";


    public Meme() {}

    public Meme(Long id, String name, String caption, String url) {
        this.id = id;
        this.name = name;
        this.caption = caption;
        this.url = url;
    }

    public void setCreatedAt(long currentTimeMillis) {}

    public void setLastUpdatedAt(long currentTimeMillis) {}
}
