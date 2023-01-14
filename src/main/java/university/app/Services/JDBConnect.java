package university.app.Services;

import lombok.Getter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class JDBConnect {
    @Getter
    final NamedParameterJdbcOperations jdbc;

    public JDBConnect(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
}