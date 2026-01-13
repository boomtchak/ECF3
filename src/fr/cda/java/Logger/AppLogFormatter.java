package fr.cda.java.Logger;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * FormatterLog
 *
 * <p>Description : Un logger avec un message complet.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 30/10/2025
 */
public class AppLogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        StringBuilder result = new StringBuilder();
        result.append(dateTimeFormatter.format(LocalDateTime.now()));
        result.append(" Level : ");
        result.append(record.getLevel());
        result.append(" / Message : ");
        result.append(record.getMessage());
        result.append(" / Classe : ");
        result.append(record.getSourceClassName());
        result.append(" / Méthode : ");
        result.append(record.getSourceMethodName());
        result.append("\n");
        return result.toString();

    }
}
