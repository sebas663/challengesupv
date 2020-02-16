package com.supv.challenge.domain;

import java.sql.Types;

import org.hibernate.dialect.H2Dialect;

public class FixedH2Dialect extends H2Dialect {

    /**
     * <p>Constructor for FixedH2Dialect.</p>
     */
    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }

}
