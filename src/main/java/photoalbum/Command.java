package photoalbum;

import java.util.EnumSet;

public enum Command {
    PHOTO_ALBUM("photo-album", 2),
    PHOTO("photo", 2),
    HELP("help", 1),
    QUIT("quit", 1),
    INVALID("invalid", 1);

    public final String command;
    public final int length;

    Command(String command, int length) {
        this.command = command;
        this.length = length;
    }

    public static Command fromString(String commandText) {
        return EnumSet.allOf(Command.class).stream()
                .filter(c -> c.command.equals(commandText))
                .findFirst()
                .orElse(INVALID);
    }

}
