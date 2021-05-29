package sample.models.clients;

public class Person {
    public Person(String firstName,String lastName,String address, String numberPhone)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.numberPhone = numberPhone;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String numberPhone;


    public String getFirstName() { return this.firstName; }

    public String getLastName(){return  this.lastName;}

    public String getAddress() {
        return this.address;
    }

    public String getNumberPhone() {
        return this.numberPhone;
    }
}