package ru.cinema.ui;

import ru.cinema.ui.command.Command;

import java.util.List;

public record MenuSection(String title, List<Command> commands) {
}
