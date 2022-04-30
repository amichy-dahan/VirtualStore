public class Worker extends Client{

    private int degree;


    public Worker(Client client, int degree) {
        super(client);
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }


}
