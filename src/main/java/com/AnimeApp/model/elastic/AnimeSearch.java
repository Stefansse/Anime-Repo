package com.AnimeApp.model.elastic;

import jakarta.annotation.security.DenyAll;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "anime")
@Data
public class AnimeSearch {

    @Id
    private String id;

    @Field(type = FieldType.Text)  // analyzed for full-text search
    private String name;

    @Field(type = FieldType.Keyword)  // exact match
    private String genre;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Integer)
    private Integer rating;

    @Field(type = FieldType.Long)
    private Long studioId;

    // getters and setters
}
