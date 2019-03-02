package com.example.syllasnap.auth;

public class AuthManager {

    private static AuthManager sInstance;

    private AuthManager() {

    }

    public static synchronized AuthManager getInstance() {
        if (sInstance == null) {
            sInstance = getInstance();
        }
        return sInstance;
    }

    
}
