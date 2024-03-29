package com.ua.statosudiscord.apirequests;

import org.springframework.stereotype.Component;

@Component
public class APIEndpoints {

    public static final String GET_USER_CLASSIC = "https://osu.ppy.sh/api/v2/users/{1}/osu";

    public static final String GET_TOKEN = "https://osu.ppy.sh/oauth/token";

    public static final String GET_MULTIPLE_USERS_CLASSIC = "https://osu.ppy.sh/api/v2/users";
}
