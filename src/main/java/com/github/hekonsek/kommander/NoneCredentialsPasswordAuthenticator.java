package com.github.hekonsek.kommander;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

class NoneCredentialsPasswordAuthenticator implements PasswordAuthenticator {

    @Override
    public boolean authenticate(String username, String password, ServerSession session) {
        return false;
    }

}
