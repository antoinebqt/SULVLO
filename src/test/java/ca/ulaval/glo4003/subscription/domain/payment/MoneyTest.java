package ca.ulaval.glo4003.subscription.domain.payment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    private static final int AN_AMOUNT = 428;
    private static final int ANOTHER_AMOUNT = 37;

    @Test
    public void givenTwoAmountsOfMoney_whenAdding_thenShouldCreateTheSumOfTheAmounts() {
        Money anAmountOfMoney = new Money(AN_AMOUNT);
        Money anotherAmountOfMoney = new Money(ANOTHER_AMOUNT);

        Money returnedMoney = anAmountOfMoney.add(anotherAmountOfMoney);

        int expectedCalculatedSum = 465;
        assertEquals(expectedCalculatedSum, returnedMoney.toDouble());
    }

    @Test
    public void givenTwoIdenticalAmountsOfMoney_whenComparing_thenShouldBeEquivalent() {
        Money anAmountOfMoney = new Money(AN_AMOUNT);
        Money anotherIdenticalAmountOfMoney = new Money(AN_AMOUNT);

        boolean isEqual = anAmountOfMoney.equals(anotherIdenticalAmountOfMoney);

        assertTrue(isEqual);
    }

    @Test
    public void givenDifferentAmountsOfMoney_whenComparing_itShouldNotBeEquivalent() {
        Money anAmountOfMoney = new Money(AN_AMOUNT);
        Money anotherNonIdenticalAmountOfMoney = new Money(ANOTHER_AMOUNT);

        boolean isEqual = anAmountOfMoney.equals(anotherNonIdenticalAmountOfMoney);

        assertFalse(isEqual);
    }

}