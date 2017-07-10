package com.github.hekonsek.kommander;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class EchoCommandFactory implements CommandFactory {

    @Override
    public Command createCommand(String command) {
        return new EchoCommand(command);
    }

    static class EchoCommand implements Command {

        private final String command;

        private OutputStream outputStream;

        private ExitCallback exitCallback;

        EchoCommand(String command) {
            this.command = command;
        }

        @Override
        public void setInputStream(InputStream inputStream) {
        }

        @Override
        public void setOutputStream(OutputStream out) {
            this.outputStream = out;
        }

        @Override
        public void setErrorStream(OutputStream err) {
        }

        @Override
        public void setExitCallback(ExitCallback callback) {
            this.exitCallback = callback;
        }

        @Override
        public void start(Environment env) throws IOException {
            outputStream.write(command.getBytes());
            outputStream.flush();
            exitCallback.onExit(0);
        }

        @Override
        public void destroy() throws Exception {

        }
    }

}
