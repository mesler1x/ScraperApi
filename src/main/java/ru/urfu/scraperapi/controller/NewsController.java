package ru.urfu.scraperapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.scraperapi.dto.NewsSummaryResponse;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@Tag(name = "news", description = "Получение summary по новостям")
public class NewsController {

    @GetMapping("/top")
    @Operation(summary = "Получение главных новостей дня")
    public List<NewsSummaryResponse> getTopNews() {
        return null;
    }

    @GetMapping()
    @Operation(summary = "Получение новостей по категории")
    public List<NewsSummaryResponse> getNewsByCategory(
        @Parameter(
            description = "Категория новости",
            example = "Кибербезопасность",
            schema = @Schema(implementation = String.class)
        )
        @RequestParam(name = "category") String category
    ) {
        return null;
    }
}
