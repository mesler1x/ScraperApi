package ru.urfu.scraperapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public record AccessTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_at")
        Long expiresAt
) {
}
