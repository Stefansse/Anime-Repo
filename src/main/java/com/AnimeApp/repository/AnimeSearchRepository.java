package com.AnimeApp.repository;

import com.AnimeApp.model.elastic.AnimeSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeSearchRepository extends ElasticsearchRepository<AnimeSearch, String> {
    // Custom queries can be added here if needed
}
