package com.jevprentice.service;

import com.jevprentice.model.HumanBeing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Date;
import java.util.List;

/**
 * Service to manage CRUD operations on a simple Human Being using {@link JdbcTemplate}
 */
@Service
public class HumanBeingService {

    private final JdbcTemplate template;

    private final String findByIdSql = "select id, created, name, birthday from human_being where id = ?";

    private final String insertSql = "insert into human_being (name, birthday) values (?, ?) " +
            "on conflict on constraint human_being_pkey do update set name = EXCLUDED.name, birthday = EXCLUDED.birthday";

    private final String upsertSql = "insert into human_being (id, name, birthday) values (?, ?, ?) " +
            "on conflict on constraint human_being_pkey do update set name = EXCLUDED.name, birthday = EXCLUDED.birthday";

    private final RowMapper<HumanBeing> humanBeingRowMapper = (rs, rowNum) -> new HumanBeing(
            rs.getLong("id"),
            rs.getTimestamp("created").toInstant().getEpochSecond(),
            rs.getString("name"),
            rs.getDate("birthday")
    );

    /**
     * @param template The JDBC Template.
     */
    HumanBeingService(final JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Find a human being by ID.
     *
     * @param id The ID.
     * @return The human being.
     */
    public HumanBeing findById(final Long id) {
        return this.template.queryForObject(this.findByIdSql, new Object[]{id}, this.humanBeingRowMapper);
    }

    /**
     * Insert a new human being and generate a unique ID.
     *
     * @param name     The name.
     * @param birthday The birthday.
     * @return The created human being.
     */
    public HumanBeing insert(final String name, final Date birthday) {
        final List<SqlParameter> params = List.of(
                new SqlParameter(Types.VARCHAR, "name"),
                new SqlParameter(Types.DATE, "birthday")
        );
        var factory = new PreparedStatementCreatorFactory(this.insertSql, params) {
            {
                setReturnGeneratedKeys(true);
                setGeneratedKeysColumnNames("id");
            }
        };
        var psc = factory.newPreparedStatementCreator(List.of(name, birthday));
        var kh = new GeneratedKeyHolder();
        this.template.update(psc, kh);

        final Number key = kh.getKey();
        if (key instanceof Long)
            return findById((Long) key);

        throw new IllegalArgumentException("Couldn't create the " + HumanBeing.class.getName());
    }

    /**
     * Insert or update a human being with a specific ID.
     *
     * @param id       The ID.
     * @param name     The name.
     * @param birthday The birthday.
     * @return The created human being.
     */
    public HumanBeing upsert(final Long id, final String name, final Date birthday) {
        final List<SqlParameter> params = List.of(
                new SqlParameter(Types.BIGINT, "id"),
                new SqlParameter(Types.VARCHAR, "name"),
                new SqlParameter(Types.DATE, "birthday")
        );
        var factory = new PreparedStatementCreatorFactory(this.upsertSql, params) {
            {
                setReturnGeneratedKeys(true);
                setGeneratedKeysColumnNames("id");
            }
        };
        var psc = factory.newPreparedStatementCreator(List.of(id, name, birthday));
        var kh = new GeneratedKeyHolder();
        this.template.update(psc, kh);

        final Number key = kh.getKey();
        if (key instanceof Long)
            return findById((Long) key);

        throw new IllegalArgumentException("Couldn't update the " + HumanBeing.class.getName());
    }
}
