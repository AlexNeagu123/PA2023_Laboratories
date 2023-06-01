package com.ro.server.communication;

import com.ro.server.command.*;
import com.ro.server.exception.*;
import com.ro.server.game.Board;
import com.ro.server.game.Player;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * The <tt>CommandParser</tt> class is responsible for parsing a line of text introduced by the user and map it with a
 * valid {@link Command}.
 * <p>
 * Syntactic and semantic errors are also caught by the methods provided in this class and signaled to the user.
 */
@RequiredArgsConstructor
public class CommandParser {
    private final Player player;

    private String checkIfSetNameIsValid(String[] arguments) throws InvalidCommandArgumentsException, PlayerInGameException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("set-name", 1, arguments.length);
        }
        if (player.inGame()) {
            throw new PlayerInGameException("set-name");
        }
        return arguments[0];
    }

    private void checkIfCreateGameIsValid(String[] arguments) throws InvalidCommandArgumentsException,
            PlayerInGameException, PlayerUnknownException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("create-game", 0, arguments.length);
        }
        if (player.inGame()) {
            throw new PlayerInGameException("create-game");
        }
        if (player.getName() == null) {
            throw new PlayerUnknownException("create-game");
        }
    }

    private void checkIfJoinGameIsValid(String[] arguments) throws InvalidCommandArgumentsException,
            PlayerInGameException, PlayerUnknownException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("join-game", 0, arguments.length);
        }
        if (player.inGame()) {
            throw new PlayerInGameException("join-game");
        }
        if (player.getName() == null) {
            throw new PlayerUnknownException("join-game");
        }
    }

    private void checkIfSubmitMoveIsValid(String[] arguments) throws InvalidCommandArgumentsException, PlayerNotInGameException, InvalidCoordinatesException, InvalidArgumentTypeException {
        if (arguments.length != 2) {
            throw new InvalidCommandArgumentsException("submit-move", 2, arguments.length);
        }
        if (!player.inGame()) {
            throw new PlayerNotInGameException("submit-move");
        }
        try {
            int row = Integer.parseInt(arguments[0]);
            int col = Integer.parseInt(arguments[1]);
            if (Board.isOutOfBorder(row, col)) {
                throw new InvalidCoordinatesException();
            }
        } catch (NumberFormatException nfe) {
            throw new InvalidArgumentTypeException();
        }
    }

    public Command matchAndBuildCommand(String commandName, String[] arguments) throws GameException {
        if (commandName.equals("stop")) {
            return new StopCommand(player);
        }
        if (commandName.equals("create-game")) {
            checkIfCreateGameIsValid(arguments);
            return new CreateGameCommand(player);
        }
        if (commandName.equals("set-name")) {
            String name = checkIfSetNameIsValid(arguments);
            return new SetNameCommand(player, name);
        }
        if (commandName.equals("join-game")) {
            checkIfJoinGameIsValid(arguments);
            return new JoinGameCommand(player);
        }
        if (commandName.equals("submit-move")) {
            checkIfSubmitMoveIsValid(arguments);
            return new SubmitMoveCommand(player, Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
        }
        throw new NonExistentCommand(commandName);
    }

    public Command parseCommand(String commandText) throws GameException {
        String[] splitCommand = commandText.split("\\s+");
        return matchAndBuildCommand(splitCommand[0], Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
    }
}
