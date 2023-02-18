package ca.ulaval.glo4003.main.application.message;

public class ErrorMessages {

    public static final String EMAIL_FORMAT = "email must be in the accepted format";
    public static final String MISSING_EMAIL = "email is required";
    public static final String MISSING_PASSWORD = "password is required";

    public static final String MISSING_U_LAVAL_ID = "uLavalId is required";
    public static final String U_LAVAL_ID_FORMAT = "uLavalId must contain exactly 9 digits";

    public static final String MISSING_BIRTH_DATE = "birthDate is required";
    public static final String MISSING_GENDER = "gender is required";
    public static final String MISSING_NAME = "name is required";
    public static final String MISSING_REQUEST = "request body is required";

    public static final String MISSING_CODE = "code is required";
    public static final String MISSING_DEPARTURE_LOCATION = "departureLocation is required";
    public static final String MISSING_ARRIVAL_LOCATION = "arrivalLocation is required";
    public static final String MISSING_CHARGING_POINT_ID = "chargingPointId is required";
    public static final String CODE_FORMAT = "code must contain only digits";
    public static final String CODE_LENGTH = "code length must be between 5 and 10";
    public static final String NUMBER_FORMAT = "creditCard.number must be a 16 digits number";
    public static final String EXPIRATION_DATE_FORMAT =
            "creditCard must have a valid expiration date in MM/yy format";
    public static final String SECURITY_CODE_FORMAT = "creditCard.securityCode must be a 3 digits number";

    public static final String MISSING_TRAVEL_TIME_PLAN = "travelTimePlan is required";
    public static final String MISSING_SEMESTER = "semester is required";
    public static final String MISSING_CREDIT_CARD = "creditCard is required";
    public static final String MISSING_SECURITY_CODE = "creditCard.securityCode is required";
    public static final String MISSING_NUMBER = "creditCard.number is required";
    public static final String MISSING_OWNER_NAME = "creditCard.ownerName is required";
    public static final String MISSING_EXPIRATION_DATE = "creditCard.expirationDate is required";
    public static final String MISSING_CHARGING_POINT_IDS = "chargingPointIds is required";
    public static final String MISSING_IN_MAINTENANCE = "inMaintenance is required";
}
