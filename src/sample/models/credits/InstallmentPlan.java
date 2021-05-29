package sample.models.credits;

import java.time.LocalDate;
import java.lang.Math;
public class InstallmentPlan extends Credit {

    private final String name ="Installment Plan";
    private final int percent = 0;

    public  InstallmentPlan(int sum,LocalDate returnDate)
    {
        this.contractNumber = (int)((Math.random()*(999999-10001))+10001)+sum;
        this.sumOfCredit= sum;
        this.date = LocalDate.now();
        this.returnDate = returnDate;
    }

    public String getName(){return this.name;}

    public int getPercent(){return this.percent;}

    public int getContractNumber(){return  this.contractNumber;}

    public int getSumOfCredit(){return this.sumOfCredit;}

    public LocalDate getCreateDate() {return  this.date;}

    public LocalDate getReturnDate(){return this.returnDate;}
}