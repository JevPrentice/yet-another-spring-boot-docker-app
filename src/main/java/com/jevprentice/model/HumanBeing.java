package com.jevprentice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Date;

/**
 * Simple human being POJO persisted with JdbcTemplate.
 */
@Getter
@ToString
public class HumanBeing {

    private final long id;
    private final long created;
    private final String name;
    private final Date birthday;

    /**
     * @param id       The human being ID.
     * @param created  The millis the human being is created.
     * @param name     The human being name.
     * @param birthday The human being birthday.
     */
    public HumanBeing(
            @JsonProperty("id") final long id,
            @JsonProperty("created") final Long created,
            @JsonProperty("name") final @NonNull String name,
            @JsonProperty("birthday") final @NonNull Date birthday
    ) {
        this.id = id;
        this.created = created == null ? System.currentTimeMillis() : created;
        this.name = name;
        this.birthday = birthday;
        if (id < 0) throw new IllegalArgumentException("A human being id cannot be negative.");
    }
}
