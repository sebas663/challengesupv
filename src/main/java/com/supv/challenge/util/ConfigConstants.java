package com.supv.challenge.util;

public interface ConfigConstants {
	/** Constant <code>SYSTEM_ACCOUNT="system"</code> */
	String SYSTEM_ACCOUNT = "system";
    /** Constant <code>SPRING_PROFILE_DEVELOPMENT="dev"</code> */
    String SPRING_PROFILE_DEVELOPMENT = "dev";
    /** Constant <code>SPRING_PROFILE_TEST="test"</code> */
    String SPRING_PROFILE_TEST = "test";
    /** Constant <code>SPRING_PROFILE_PRODUCTION="prod"</code> */
    String SPRING_PROFILE_PRODUCTION = "prod";
    /** Spring profile used to enable swagger
        Constant <code>SPRING_PROFILE_SWAGGER="swagger"</code> */
    String SPRING_PROFILE_SWAGGER = "swagger";
    /** Spring profile used to disable running liquibase
        Constant <code>SPRING_PROFILE_NO_LIQUIBASE="no-liquibase"</code> */
    String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
}
