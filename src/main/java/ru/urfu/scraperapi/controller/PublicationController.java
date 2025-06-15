package ru.urfu.scraperapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.urfu.scraperapi.dto.IsFakeResponse;
import ru.urfu.scraperapi.dto.PublicationResponse;
import ru.urfu.scraperapi.service.PublicationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publication")
@RequiredArgsConstructor
@Tag(name = "Сервис публикаций", description = "Получение публикаций по новостям, собраных скрапером")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping
    @Operation(summary = "Получить все публикации")
    public List<PublicationResponse> findAll(@RequestParam(value = "limit", required = false, defaultValue = "500")
                                                 Integer limit,
                                             @RequestParam(value = "offset", required = false, defaultValue = "0")
                                             Integer offset) {
        return publicationService.findAll(limit, offset);
    }

    @DeleteMapping("/{publication_id}")
    @Operation(summary = "Удаление публикации по id")
    public void delete(@PathVariable("publication_id") UUID publicationId) {
        publicationService.deleteById(publicationId);
    }

    @PostMapping("/is-fake/{publication_id}")
    @Operation(summary = "Перепроверить публикацию на фейк")
    public IsFakeResponse isFakePublication(@PathVariable("publication_id") UUID publicationId) {
        return publicationService.isFake(publicationId);
    }
}
