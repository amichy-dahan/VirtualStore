import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int numOfPurchases =0 ;
    private LinkedList<Product> cart;
    private Date lastPurchase;


    public Client(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.username = client.getUsername();
        this.password = client.getPassword();
        this.numOfPurchases = client.numOfPurchases;
        this.cart = client.cart;
        this.lastPurchase = null;

    }

    public Client(String firstName, String lastName, String username, String password ,LinkedList<Product> cart,int numOfPurchases , Date date ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cart =cart;
        this.numOfPurchases = numOfPurchases;
        this.lastPurchase = null;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LinkedList<Product> getCart() {
        return cart;
    }

    public void setCart(LinkedList<Product> cart) {
        this.cart = cart;
    }

    public int getNumOfPurchases() {
        return numOfPurchases;
    }

    public void setNumOfPurchases(int numOfPurchases) {
        this.numOfPurchases = numOfPurchases;
    }
    public Date getLastPurchase() {
        return lastPurchase;
    }

    public void setLastPurchase(Date lastPurchase) {
        this.lastPurchase = lastPurchase;
    }

}