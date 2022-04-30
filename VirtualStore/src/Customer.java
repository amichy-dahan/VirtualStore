import java.awt.font.TextHitInfo;
import java.util.Date;

public class Customer extends Client {

    private boolean clubMember;
    private int totalPurchasesCost = 0;



    public Customer(Client client, boolean clubMember , int totalPurchasesCost) {
        super(client);
        this.clubMember = clubMember;

        this.totalPurchasesCost = totalPurchasesCost;
    }

    public boolean isClubMember() {
        return clubMember;
    }
    public void setClubMember(boolean clubMember) {
        this.clubMember = clubMember;
    }

    public int getTotalPurchasesCost() {
        return totalPurchasesCost;
    }

    public void setTotalPurchasesCost(int totalPurchasesCost) {
        this.totalPurchasesCost = totalPurchasesCost;
    }



    public String toString(){
        return "Hello " +  this.getFirstName() + " "+ this.getLastName() + "\nQuantity Of Purchases:"+ this.getNumOfPurchases()
                +"\nsum Of Purchases:" + getTotalPurchasesCost() +"\n" + "the last Purchase was:" + getLastPurchase() +"\n" ;
    }
}
