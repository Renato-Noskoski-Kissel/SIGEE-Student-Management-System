package Model.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@ufsc\\.com$"); // *@ufsc.com, com "*" sendo qualquer combinação de letras e números
    private static final Pattern ENROLLMENT_PATTERN = Pattern.compile("^\\d{8}$"); // exatamente 8 dígitos
    private static final Pattern SIAPE_PATTERN = Pattern.compile("^\\d{8}$"); // exatamente 8 dígitos
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern LOCATION_PATTERN = Pattern.compile("^[\\p{L}0-9 .\\-,/()ºª]+$");


    private Validator() {
    }

    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isEnrollmentValid(String enrollment) {
        return ENROLLMENT_PATTERN.matcher(enrollment).matches();
    }

    public static boolean isSiapeValid(String siape) {
        return SIAPE_PATTERN.matcher(siape).matches();
    }

    public static boolean isNameValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String trimmedName = name.trim();

        if (trimmedName.length() < 2) {
            return false;
        }
        return NAME_PATTERN.matcher(trimmedName).matches();
    }

    public static boolean isLocationValid(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        String trimmedLocation = location.trim();
        if (trimmedLocation.length() < 3) {
            return false;
        }
        return LOCATION_PATTERN.matcher(trimmedLocation).matches();
    }
}
