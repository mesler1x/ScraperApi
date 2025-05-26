package ru.urfu.scraperapi.dto;

import ru.urfu.scraperapi.model.Choice;

import java.util.List;

public record GigaChatResponse(
        List<Choice> choices
) {
}
