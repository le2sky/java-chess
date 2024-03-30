package chess.presentation.controller.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommandFactory {

    private static final Map<Predicate<String>, Function<String, Command>> classifier;

    static {
        classifier = new HashMap<>();
        classifier.put(MoveCommand::canCreate, MoveCommand::new);
        classifier.put(EndCommand::canCreate, (ignore) -> new EndCommand());
        classifier.put(StatusCommand::canCreate, (ignore) -> new StatusCommand());
    }

    public static Command createCommand(String input) {
        return classifier.entrySet().stream()
                .filter(entry -> entry.getKey().test(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다."))
                .getValue()
                .apply(input);
    }
}
