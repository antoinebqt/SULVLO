package ca.ulaval.glo4003.code.domain;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UnlockCodeGeneratorTest {
    private UnlockCodeGenerator unlockCodeGenerator;

    @BeforeEach
    public void setUp() {
        unlockCodeGenerator = new UnlockCodeGenerator();
    }

    @Test
    public void whenGenerateCode_thenShouldGenerateCodeWithALengthOfAtLeast5Characters() {
        UnlockCode code = unlockCodeGenerator.generate();

        assertTrue(code.getValue().length() >= 5);
    }

    @Test
    public void whenGenerateCode_thenShouldGenerateCodeWithALengthOfAtMost10Characters() {
        UnlockCode code = unlockCodeGenerator.generate();

        assertTrue(code.getValue().length() <= 10);
    }

    @Test
    public void whenGenerateCode_thenShouldGenerateCodeWithOnlyNumbers() {
        UnlockCode code = unlockCodeGenerator.generate();

        assertTrue(NumberUtils.isDigits(code.getValue()));
    }

}
