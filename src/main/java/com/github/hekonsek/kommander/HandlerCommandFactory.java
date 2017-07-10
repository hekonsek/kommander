package com.github.hekonsek.kommander;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static org.springframework.util.StringUtils.collectionToDelimitedString;

public class HandlerCommandFactory implements CommandFactory {

    private final CommandHandler handler;

    public HandlerCommandFactory(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public Command createCommand(String command) {
        return new HandlerCommand(command);
    }

    class HandlerCommand implements Command {

        private final String command;

        private OutputStream outputStream;

        private ExitCallback exitCallback;

        HandlerCommand(String command) {
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
            List<String> result = handler.onCommand(env.getEnv().get("USER"), command);
            outputStream.write(collectionToDelimitedString(result, "\n").getBytes());
            outputStream.flush();
            exitCallback.onExit(0);
        }

        @Override
        public void destroy() throws Exception {

        }
    }

}
