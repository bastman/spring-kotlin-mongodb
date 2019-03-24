package com.example.api.api.common.rest.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class ValidityDto(
        @JsonProperty("isActive")
        val isActive: Boolean,
        val deletedAt: Instant?
)
