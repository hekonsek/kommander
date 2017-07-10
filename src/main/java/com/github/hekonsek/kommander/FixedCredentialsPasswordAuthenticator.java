package com.github.hekonsek.kommander;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

public class FixedCredentialsPasswordAuthenticator implements PasswordAuthenticator {

    private final String username;

    private final String password;

    FixedCredentialsPasswordAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean authenticate(String username, String password, ServerSession session) {
        return username.equals(this.username) && password.equals(this.password);
    }

}