package sample.models.credits;

import java.time.LocalDate;
public abstract class Credit
{
    protected int contractNumber;
    protected int sumOfCredit;
    protected LocalDate date;
    protected LocalDate returnDate;
}