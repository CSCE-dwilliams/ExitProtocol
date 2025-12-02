package com.model;

/**
 * Provides constant values fo the file paths and json
 * keys used throughout the application.
 * 
 * This abstract class is intended to centralize all data-related constants
 * such as file names, json keys for user data,a dn game sessions.
 * 
 * @author Clankers
 */
public abstract class DataConstants {
    /** Path to jon file that stores the users information. */
    protected static final String USER_FILE_NAME = "exitprotocol/src/main/resources/json/users.json";
    /** Json key for users first name */
    protected static final String USER_FIRST_NAME = "firstname";
    /** Json key for users last name */
    protected static final String USER_LAST_NAME = "lastname";
    /** Json key for users unique id (UUID) */
    protected static final String USER_ID = "id";
    /** Json key for users email address */
    protected static final String USER_EMAIL = "email";
    /** Json key for users password */
    protected static final String USER_PASSWORD = "password";
    /** Json key for users team name */
    protected static final String USER_TEAM_NAME = "teamname";
    /** Json key for users avatar image/identifier */
    protected static final String USER_AVATAR = "avatar";
    /** Json key for users score */
    protected static final String USER_SCORE = "score";
    /** Json key for the list of session associated with a user */
    protected static final String SESSIONS = "sessions";
    /** Path to json file that stores game/room information */
    protected static final String GAMES_FILE_NAME = "exitprotocol/src/main/resources/json/rooms.json";

}