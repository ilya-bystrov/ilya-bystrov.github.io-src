package io.github.ilyabystrov.json.jackson.usingmultilinestring;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class Config {
    
    private String name;
    private String sql;

    private Config() {
    }

    public String getName() {
        return name;
    }

    public String getSql() {
        return sql;
    }
}
