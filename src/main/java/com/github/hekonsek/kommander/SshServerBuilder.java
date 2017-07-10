/**
 * Licensed to the Rhiot under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.hekonsek.kommander;

import com.google.common.io.Files;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.springframework.util.SocketUtils;

import java.io.File;

public class SshServerBuilder {

    private PasswordAuthenticator authenticator = new AnyCredentialsPasswordAuthenticator();

    private int port = SocketUtils.findAvailableTcpPort();

    private File root = Files.createTempDir();

    public SshServer build() {
        return new SshServer(authenticator, port, root);
    }

    public SshServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public SshServerBuilder root(File root) {
        this.root = root;
        return this;
    }

    SshServerBuilder authenticator(PasswordAuthenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

}
