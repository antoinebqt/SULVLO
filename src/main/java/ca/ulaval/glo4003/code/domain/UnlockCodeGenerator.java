package ca.ulaval.glo4003.code.domain;


import java.time.LocalDateTime;
import java.util.Random;


public class UnlockCodeGenerator {
    private final static int MAX_CODE_SIZE = 10;
    private final static int MIN_CODE_SIZE = 5;


    public UnlockCode generate() {
        String codeValue = generateCodeValue();

        return new UnlockCode(codeValue, LocalDateTime.now());
    }

    private String generateCodeValue() {
        Random rand = new Random();
        int codeSize = rand.nextInt(MAX_CODE_SIZE + 1 - MIN_CODE_SIZE) + MIN_CODE_SIZE;

        String numericString = "0123456789";

        StringBuilder codeValue = new StringBuilder(codeSize);

        for (int i = 0; i < codeSize; i++) {
            int randomIndex = (int) (Math.random() * numericString.length());

            codeValue.append(numericString.charAt(randomIndex));
        }


        return codeValue.toString();
    }
}
