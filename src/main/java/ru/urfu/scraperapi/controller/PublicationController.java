package ru.urfu.scraperapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.urfu.scraperapi.dto.PublicationResponse;
import ru.urfu.scraperapi.service.PublicationService;

import java.util.List;

@RestController
@RequestMapping("/api/publication")
@RequiredArgsConstructor
@Tag(name = "news", description = "Получение summary по новостям")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping
    public List<PublicationResponse> findAll(@RequestParam(value = "limit", required = false, defaultValue = "500")
                                                 Integer limit,
                                             @RequestParam(value = "offset", required = false, defaultValue = "0")
                                             Integer offset) {
        return publicationService.findAll(limit, offset);
    }
}
