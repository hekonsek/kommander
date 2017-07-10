package com.github.hekonsek.kommander;

import java.util.List;

public interface CommandHandler {

    List<String> onCommand(String user, String command);

}
