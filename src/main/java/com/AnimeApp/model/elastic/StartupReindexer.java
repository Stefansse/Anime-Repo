package com.AnimeApp.model.elastic;

import com.AnimeApp.service.mainE.AnimeService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupReindexer {

    private final AnimeService animeService;

    public StartupReindexer(AnimeService animeService) {
        this.animeService = animeService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void reindexOnStartup() {
        System.out.println("Reindexing anime on app startup...");
        animeService.reindexAllAnimeToElastic();
        System.out.println("Reindexing completed.");
    }
}
