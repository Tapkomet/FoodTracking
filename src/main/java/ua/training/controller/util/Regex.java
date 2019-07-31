package ua.training.controller.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ua.training.controller.util.Regex.RegexEnum.*;
import static ua.training.controller.util.Regex.RegexLoggerMessageEnum.*;

/**
 * Class for Regex and methods to compare with regex
 */
public class Regex {

    private static final Logger logger = LogManager.getLogger(Regex.class.getName());

    public enum RegexEnum {
        SURNAME("^[a-zA-Z\\\\s]+"),
        PASSWORD("^(?=\\S+$).{5,}$"),
        EMAIL("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$"),
        NUMBER("[\\d]+");
        public final String regex;

        RegexEnum(String regex) {
            this.regex = regex;
        }
    }

    public enum RegexLoggerMessageEnum {
        SURNAME_MATCHES("surname.matches({}) : {}"),
        PASSWORD_MATCHES("password.matches({}) : {}"),
        EMAIL_MATCHES("email.matches({}) : {}"),
        NUMBER_MATCHES("number.matches({}) : {}");
        public final String message;

        RegexLoggerMessageEnum(String message) {
            this.message = message;
        }
    }

    public static boolean isSurnameWrong(String surname) {
        if (surname == null) {
            return true;
        }
        boolean match = surname.matches(SURNAME.regex);
        logger.debug(SURNAME_MATCHES.message, surname, match);
        return !match;
    }

    public static boolean isPasswordWrong(String password) {
        if (password == null || password.isEmpty()) {
            return true;
        }
        boolean match = password.matches(PASSWORD.regex);
        logger.debug(PASSWORD_MATCHES.message, password, match);
        return !match;
    }

    public static boolean isEmailWrong(String email) {
        if (email == null) {
            return true;
        }
        boolean match = email.matches(EMAIL.regex);
        logger.debug(EMAIL_MATCHES.message, email, match);
        return !match;
    }


    public static boolean isNumberWrong(String numberString) {
        if (numberString == null) {
            return true;
        }
        boolean match = numberString.matches(NUMBER.regex);
        logger.debug(NUMBER_MATCHES.message, numberString, match);
        return !match;
    }
}
