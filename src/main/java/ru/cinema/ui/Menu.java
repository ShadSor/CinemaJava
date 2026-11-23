package ru.cinema.ui;

import ru.cinema.domain.exception.CinemaException;
import ru.cinema.ui.command.Command;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Menu {

    private static final String EXIT_CHOICE = "0";
    private static final String LINE = "=".repeat(60);

    private final ConsoleIo io;
    private final List<MenuSection> sections;
    private final Map<Integer, Command> commandsByNumber = new LinkedHashMap<>();

    public Menu(ConsoleIo io, List<MenuSection> sections) {
        this.io = io;
        this.sections = sections;
        int number = 1;
        for (MenuSection section : sections) {
            for (Command command : section.commands()) {
                commandsByNumber.put(number++, command);
            }
        }
    }

    public void run() {
        io.println(LINE);
        io.println("                        КИНОТЕАТР");
        io.println(LINE);
        try {
            while (true) {
                printMenu();
                final String choice = io.readLine("Ваш выбор: ").trim();
                if (choice.equals(EXIT_CHOICE)) {
                    break;
                }
                runCommand(choice);
            }
        } catch (NoSuchElementException e) {
            io.println();
        }
        io.println("До свидания!");
    }

    private void runCommand(String choice) {
        final Command command = findCommand(choice);
        if (command == null) {
            io.println("Такого пункта нет, выберите из списка.");
            io.println();
            return;
        }
        io.println();
        try {
            command.execute();
        } catch (CinemaException e) {
            io.println("  Ошибка: " + e.getMessage());
        }
        io.println();
    }

    private Command findCommand(String choice) {
        try {
            return commandsByNumber.get(Integer.parseInt(choice));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void printMenu() {
        io.println(LINE);
        int number = 1;
        for (MenuSection section : sections) {
            io.println(" " + section.title());
            for (Command command : section.commands()) {
                io.println(String.format("   %2d. %s", number++, command.title()));
            }
        }
        io.println();
        io.println(String.format("   %2s. %s", EXIT_CHOICE, "Выход"));
        io.println(LINE);
    }
}
