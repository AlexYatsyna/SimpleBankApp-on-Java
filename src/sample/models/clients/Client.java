package sample.models.clients;

public class Client  extends  Person{
    public Client(String firstName,String lastName,String address, String numberPhone,Person contactPerson,String password)
    {
        super(firstName,lastName,address,numberPhone);
        this.contactPerson = contactPerson;
        this.password = password;
    }

    private String password;
    private Person contactPerson;

    public String getPassword() {
        return this.password;
    }

    public Person getContactPerson() { return this.contactPerson;}

    public String getContactPersonPhone(){return  this.contactPerson.getNumberPhone();}

}