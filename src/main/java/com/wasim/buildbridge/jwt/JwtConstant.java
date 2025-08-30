package com.wasim.buildbridge.jwt;

public class JwtConstant {
    public static final String SECRET_KEY = "xwemqweunY&Y#&hq3884x8WHUGFWE2uwxnuhajeduawhybbvhdnjafafeqw8u3ru893y7gyefwbDSMdsnjilw8329477e82ybqwbajkjwqhd8r23yAHUQWHx7y&AY&Y#&HUEDWFE&bdhwuI";
    public static final String JWT_HEADER = "Authorization";
    public static final String SUBJECT = "username";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_FULLNAME = "fullName";
    public static final String CLAIM_ID = "id";
    public static final long EXPIRATION_TIME = 30*60*1000;
    public static final String JWT_PREFIX = "Bearer ";
}
