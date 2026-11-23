package ru.cinema.ui;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleIo {

    public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final Scanner scanner;

    public ConsoleIo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void println(String text) {
        System.out.println(text);
    }

    public void println() {
        System.out.println();
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public String readNonBlank(String prompt) {
        while (true) {
            final String value = readLine(prompt);
            if (!value.isBlank()) {
                return value.trim();
            }
            println("Это поле нельзя оставлять пустым.");
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt).trim());
            } catch (NumberFormatException e) {
                println("Нужно целое число, попробуйте ещё раз.");
            }
        }
    }

    public BigDecimal readMoney(String prompt) {
        while (true) {
            try {
                return new BigDecimal(readLine(prompt).trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                println("Нужна сумма, например 350 или 450.50.");
            }
        }
    }

    public LocalDateTime readDateTime(String prompt) {
        while (true) {
            try {
                return LocalDateTime.parse(readLine(prompt).trim(), DATE_TIME);
            } catch (DateTimeParseException e) {
                println("Не получилось разобрать дату. Пример: 25.11.2026 19:30");
            }
        }
    }

    public boolean confirm(String question) {
        final String answer = readLine(question + " (да/нет): ").trim().toLowerCase();
        return answer.equals("да") || answer.equals("д") || answer.equals("y") || answer.equals("yes");
    }

    public <T> Optional<T> choose(List<T> items, String prompt) {
        final String input = readLine(prompt).trim();
        final int number;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            println("Нужно ввести номер из списка.");
            return Optional.empty();
        }
        if (number < 1 || number > items.size()) {
            println("В списке нет номера " + number + ".");
            return Optional.empty();
        }
        return Optional.of(items.get(number - 1));
    }

    public void printTable(List<String> headers, List<List<String>> rows) {
        final int columns = headers.size() + 1;
        final int[] widths = new int[columns];
        widths[0] = Math.max(1, String.valueOf(rows.size()).length());
        for (int c = 1; c < columns; c++) {
            widths[c] = headers.get(c - 1).length();
        }
        for (List<String> row : rows) {
            for (int c = 1; c < columns; c++) {
                widths[c] = Math.max(widths[c], row.get(c - 1).length());
            }
        }
        printTableRow(widths, "№", headers);
        println(separator(widths));
        for (int i = 0; i < rows.size(); i++) {
            printTableRow(widths, String.valueOf(i + 1), rows.get(i));
        }
    }

    private void printTableRow(int[] widths, String number, List<String> cells) {
        final StringBuilder line = new StringBuilder("  ");
        line.append(pad(number, widths[0]));
        for (int c = 1; c < widths.length; c++) {
            line.append("  ").append(pad(cells.get(c - 1), widths[c]));
        }
        println(line.toString().stripTrailing());
    }

    private String separator(int[] widths) {
        int total = 2 * (widths.length - 1);
        for (int width : widths) {
            total += width;
        }
        return "  " + "-".repeat(total);
    }

    private String pad(String text, int width) {
        return text + " ".repeat(width - text.length());
    }
}
