public class Product {
    private String name;
    private int id;
    private int price;
    private boolean exist;
    private int discount ;
    private int numOfProducts;

    public Product(String name , boolean exist , int price ,int id ,int discount , int numOfProducts){
        this.price =price;
        this.name = name;
        this.exist = exist;
        this.id =id;
        this.discount =discount;
        this.numOfProducts = numOfProducts;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    @Override
    public String toString() {
        return "Product" + "[" + id +  "]" + name + "---- price : " + price +"₪\n" ;
    }
}
