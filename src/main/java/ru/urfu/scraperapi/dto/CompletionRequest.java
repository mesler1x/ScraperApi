package ru.urfu.scraperapi.dto;

import ru.urfu.scraperapi.model.Message;

import java.util.List;

public record CompletionRequest (
        String model,
        List<Message> messages,
        double temperature
) {
}


