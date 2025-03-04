package lk.ac.cmb.ucsc.utils.logging;

import java.util.logging.SimpleFormatter;

public class FileLogFormatter extends SimpleFormatter {
    @Override
    public String format(java.util.logging.LogRecord record) {
        return String.format(
                "[%1$tF %1$tT] [%2$s] [%3$s] %4$s %n",
                record.getMillis(),
                record.getLevel().getLocalizedName(),
                record.getSourceClassName(),
                formatMessage(record)
        );
    }
}
