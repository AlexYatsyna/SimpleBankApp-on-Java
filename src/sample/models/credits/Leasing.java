package sample.models.credits;

import java.time.LocalDate;
import java.lang.Math;
public class Leasing extends Credit
{
    private  final  String name = "Leasing";
    private  final int percent = 3;
    private LeasingThings leasingThing;

    public  Leasing(int sum, LocalDate returnDate, LeasingThings leasingThing)
    {
        this.contractNumber = (int)((Math.random()*(999999-10001))+10001)+sum;
        this.leasingThing = leasingThing;
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

    public LeasingThings getLeasingThings(){return  this.leasingThing;}
}