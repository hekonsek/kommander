package com.github.hekonsek.kommander;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import static java.io.File.createTempFile;

/**
 * Plugable SSH server which be used to expose Java services via SSH.
 */
class SshServer {

    // Collaborators

    private final PasswordAuthenticator authenticator;

    // Internal collaborators

    private org.apache.sshd.server.SshServer internalServer;

    // Configuration

    private final int port;

    private final File root;

    // Constructors

    SshServer(PasswordAuthenticator authenticator, int port, File root) {
        this.authenticator = authenticator;
        this.port = port;
        this.root = root;
    }

    // Life-cycle

    SshServer start() {
        try {
            internalServer = org.apache.sshd.server.SshServer.setUpDefaultServer();
            internalServer.setPort(port);
            internalServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(createTempFile("smolok", "host_keys")));
            internalServer.setPasswordAuthenticator(authenticator);
            internalServer.setCommandFactory(new EchoCommandFactory());
            internalServer.setFileSystemFactory(new VirtualFileSystemFactory(root.toPath()));
            internalServer.setSubsystemFactories(Arrays.asList(new SftpSubsystemFactory()));
            internalServer.start();

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void stop() {
        try {
            internalServer.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Factory methods

//    SshClient client(String username, String password) {
//        new SshClient('localhost', port, username, password)
//    }

    Properties config(String path) {
        try {
            Properties properties = new Properties();
            String absolutePath = new File(root, path).getAbsolutePath();
            properties.load(new FileInputStream(Paths.get(absolutePath).toFile()));
            return properties;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Accessors

    int port() {
        return port;
    }

    File root() {
        return root;
    }

}