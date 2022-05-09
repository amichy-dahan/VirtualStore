import java.util.*;

public class VirtualStore 
    private Client session;
    private ArrayList<Client> clients;
    private LinkedList<Product> shop;

    public VirtualStore() {
        this.clients = new ArrayList<>();
        this.shop = new LinkedList<>();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int optionUser;
        try {
            do {
                System.out.println(" WELCOME TO OUR VIRTUAL STORE !\n" +
                        "-CHOOSE OPTION TO START-");
                System.out.println("1 - Create Account\n" +
                        "2 - Login\n" +
                        "3 - Exit\n");
                optionUser = scanner.nextInt();

                switch (optionUser) {
                    case optionChoose.SIGNUP:
                        System.out.println("SIGNUP SCREEN:");
                        createAccount();
                        break;
                    case optionChoose.LOGIN:
                        System.out.println("LOGIN SCREEN:");
                        boolean success = login();
                        if (success) {
                            if (this.session instanceof Worker) {
                                Worker worker = (Worker) this.session;
                                System.out.print("Hello " + worker.getFirstName() + " " + worker.getLastName());
                                switch (worker.getDegree()) {
                                    case optionChoose.REGULAR:
                                        System.out.println(" (WORKER)\n");
                                        break;
                                    case optionChoose.MANAGER:
                                        System.out.println(" (MANAGER)\n");
                                        break;
                                    case optionChoose.CO_MANAGER:
                                        System.out.println(" (CO MANAGER)\n");
                                        break;
                                }
                                workerMenu();
                            } else {
                                Customer customer = (Customer) this.session;
                                if (customer.isClubMember()) {
                                    System.out.println("(VIP)");
                                }
                                System.out.println(customer);
                                purchaseProduct();
                            }
                        }
                        break;
                    case optionChoose.EXIT:
                        break;
                    default:
                        System.out.println("The input is invalid, try again!");
                        break;
                }
            } while (optionUser != optionChoose.EXIT);
        } catch (Exception e) {
            System.out.println("wrong option");
            start();
        }
    }

    public void createAccount() {
        int optionOfAcc = selectWorkerOrCustomer();
        switch (optionOfAcc) {
            case optionChoose.WORKER:
                createWorkerAccount();
                break;
            case optionChoose.CUSTOMER:
                createCustomerAccount();
                break;
            default:
                System.out.println("Invalid input!");
        }
    }

    private Client createBaseClient() {
        Scanner scanner = new Scanner(System.in);
        String firstName, lastName, username, password;

        do {
            System.out.println("Enter Your first name:");
            firstName = scanner.nextLine();
            if (isContainDigit(firstName)) {
                System.out.println("Enter Again , Without Digit!");
            }
        } while (isContainDigit(firstName));

        do {
            System.out.println("Enter Your last name :");
            lastName = scanner.nextLine();
            if (isContainDigit(lastName)) {
                System.out.println("Enter Again , Without Digit!");
            }
        } while (isContainDigit(lastName));


        do {
            System.out.println("Enter Your username :");
            username = scanner.nextLine();

            if (isUserExist(username, optionChoose.WORKER) || isUserExist(username, optionChoose.CUSTOMER)) {
                System.out.println("This username is already taken, try again!");
            }
        } while (isUserExist(username, optionChoose.WORKER) || isUserExist(username, optionChoose.CUSTOMER));

        do {
            System.out.println("Enter your Password (at least 6 chars): ");
            password = scanner.nextLine();
            if (!isPasswordValid(password)) {
                System.out.println("Password to short, select again :");
            }
        } while (!isPasswordValid(password));

        return new Client(firstName, lastName, username, password, new LinkedList<>() ,optionChoose.ZERO , new Date());
    }

    private void createWorkerAccount() {
        Scanner scanner = new Scanner(System.in);
        int degree;
        String answer;

        Client client = createBaseClient();

        do {
            System.out.println("Enter degree: \n [1] REGULAR \n [2] MANAGER \n [3] CO MANAGER ");
            degree = scanner.nextInt();
            if (degree < optionChoose.REGULAR || degree > optionChoose.CO_MANAGER) {
                System.out.println("INVALID DEGREE TRY AGAIN!");
            }
        } while (degree < optionChoose.REGULAR || degree > optionChoose.CO_MANAGER);

        // CLEANING BUFFER
        scanner.nextLine();
        do {
            System.out.println("Are you also a Customer? [YES|NO]");
            answer = scanner.nextLine();
            if (!answer.equals("YES") && !answer.equals("NO")) {
                System.out.println("INVALID INPUT TRY AGAIN!");
            }
        } while (!answer.equals("YES") && !answer.equals("NO"));

        if (answer.equals("YES")) {
            do {
                System.out.println("Are you member in our club? [YES|NO]");
                answer = scanner.nextLine();
                if (!answer.equals("YES") && !answer.equals("NO")) {
                    System.out.println("INVALID INPUT TRY AGAIN!");
                }
            } while (!answer.equals("YES") && !answer.equals("NO"));

            Customer customer = new Customer(client, answer.equals("YES") ,optionChoose.ZERO);
            clients.add(customer);
        }

        Worker worker = new Worker(client, degree);
        clients.add(worker);


    }

    private void createCustomerAccount() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        int degree;
        Client client = createBaseClient();

        do {
            System.out.println("Are you member in our club? [YES|NO]");
            answer = scanner.nextLine();
            if (!answer.equals("YES") && !answer.equals("NO")) {
                System.out.println("INVALID INPUT TRY AGAIN!");
            }
        } while (!answer.equals("YES") && !answer.equals("NO"));

        Customer customer = new Customer(client, answer.equals("YES"),optionChoose.ZERO);
        clients.add(customer);

        do {
            System.out.println("Are you also a Worker? [YES|NO]");
            answer = scanner.nextLine();
            if (!answer.equals("YES") && !answer.equals("NO")) {
                System.out.println("INVALID INPUT TRY AGAIN!");
            }
        } while (!answer.equals("YES") && !answer.equals("NO"));

        if (answer.equals("YES")) {
            do {
                System.out.println("Enter degree: \n [1] REGULAR \n [2] MANAGER \n [3] CO MANAGER ");
                degree = scanner.nextInt();
                if (degree < optionChoose.REGULAR || degree > optionChoose.CO_MANAGER) {
                    System.out.println("INVALID DEGREE TRY AGAIN!");
                }
            } while (degree < optionChoose.REGULAR || degree > optionChoose.CO_MANAGER);
            Worker worker = new Worker(client, degree);
            clients.add(worker);
        }
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        String username, password;
        int optionOfAcc = selectWorkerOrCustomer();

        if (optionOfAcc == optionChoose.WORKER) {
            System.out.println("ENTER USERNAME: ");
            username = scanner.nextLine();
            if (isUserExist(username, optionChoose.WORKER)) {
                System.out.println("ENTER PASSWORD: ");
                password = scanner.nextLine();
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i) instanceof Worker && clients.get(i).getUsername().equals(username) && clients.get(i).getPassword().equals(password)) {
                        this.session = clients.get(i);
                        return true;
                    }
                }
                System.out.println("PASSWORD NOT CORRECT!");
            } else {
                System.out.println("USERNAME NOT EXISTS!");
            }
            return false;
        } else {
            System.out.println("ENTER USERNAME: ");
            username = scanner.nextLine();
            if (isUserExist(username, optionChoose.CUSTOMER)) {
                System.out.println("ENTER PASSWORD: ");
                password = scanner.nextLine();
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i) instanceof Customer && clients.get(i).getUsername().equals(username) && clients.get(i).getPassword().equals(password)) {
                        this.session = clients.get(i);
                        return true;
                    }
                }
                System.out.println("PASSWORD NOT CORRECT!");
            } else {
                System.out.println("USERNAME NOT EXISTS!");
            }
            return false;
        }
    }

    private int selectWorkerOrCustomer() {
        Scanner scanner = new Scanner(System.in);
        int optionOfAcc;

        do {
            System.out.println("[1] WORKER \n[2] CUSTOMER");
            optionOfAcc = scanner.nextInt();

            if (optionOfAcc != optionChoose.WORKER && optionOfAcc != optionChoose.CUSTOMER) {
                System.out.println("INVLID INPUT, TRY AGAIN!");
            }
        } while (optionOfAcc != optionChoose.WORKER && optionOfAcc != optionChoose.CUSTOMER);
        return optionOfAcc;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= optionChoose.SIX;
    }

    private boolean isContainDigit(String firstName) {
        for (int i = 0; i < firstName.length(); i++) {
            if (Character.isDigit(firstName.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserExist(String userName, int typeOfClient) {
        if (typeOfClient == optionChoose.WORKER) {
            for (int i = 0; i < clients.size(); i++) {
                if (this.clients.get(i) instanceof Worker && this.clients.get(i).getUsername().equals(userName))
                    return true;
            }
        } else {
            for (int i = 0; i < clients.size(); i++) {
                if (this.clients.get(i) instanceof Customer && this.clients.get(i).getUsername().equals(userName))
                    return true;
            }
        }
        return false;
    }

    public void workerMenu() {
        Scanner scanner = new Scanner(System.in);
        int optionUser;
        try {
            do {
                System.out.println("WELCOME TO THE WORKER MENU\nCHOOSE OPTION TO START");
                System.out.println("[1] Print all customers.");
                System.out.println("[2] Print all VIP customers. ");
                System.out.println("[3] Print customers that have made a purchase. ");
                System.out.println("[4] Print the highest buying customer. ");
                System.out.println("[5] Add new product to shop. ");
                System.out.println("[6] Update availability status of product. ");
                System.out.println("[7] Buy a product - SPECIAL OFFERS FOR WORKERS!");
                System.out.println("[8] Logout - return to main menu. ");
                optionUser = scanner.nextInt();
                switch (optionUser) {
                    case optionChoose.ONE:
                        printAllCustomers();
                        break;
                    case optionChoose.TWO:
                        printVIPCustomers();
                        break;
                    case optionChoose.THREE:
                        printCustomersThatPurchased();
                        break;
                    case optionChoose.FOUR:
                        printCustomerBiggestPurchased();
                        break;
                    case optionChoose.FIVE:
                        AddingProductToTheStore();
                        break;
                    case optionChoose.SIX:
                        ChangeStatusForProduct();
                        break;
                    case optionChoose.SEVEN:
                        purchaseProduct();
                        break;
                    case optionChoose.EIGHT:
                        showTotalPrice();
                        System.out.println("Credit or cash?\n");
                        start();
                        break;
                }
            } while (optionUser != optionChoose.EIGHT);
        } catch (Exception e) {
            System.out.println("wrong option\n");
            workerMenu();
        }
    }

    private void purchaseProduct() {
        Scanner scanner = new Scanner(System.in);
        int sumOfPurchase;
        int choose;
        int numOfPro;
        int numOfPurchase = 0;
        if (this.session instanceof Customer) {
            try {
            do {
                showProduct();
                System.out.println("choose a product to your cart: or -1 to Finish your sopping and back the Main menu");
                choose = scanner.nextInt();
                if (choose == -1) {
                    showTotalPrice();
                    System.out.println("Credit or cash?\n");
                    start();
                }else if (choose != shop.get(choose).getId()){
                    purchaseProduct();
                }else {
                    if (choose == shop.get(choose).getId() && shop.get(choose).isExist()) {

                        System.out.println("how many you want?");
                        numOfPro = scanner.nextInt();
                        shop.get(choose).setNumOfProducts(numOfPro);
                        this.session.getCart().add(shop.get(choose));
                        numOfPurchase++;
                        this.session.setNumOfPurchases(numOfPurchase);
                        System.out.println("your cart is :");
                        for (int i = 0; i < this.session.getCart().size(); i++) {
                            System.out.println("- " + this.session.getCart().get(i).getNumOfProducts() + " -" + " of :" + this.session.getCart().get(i).getName()+" price --->" + this.session.getCart().get(i).getPrice());
                        }
                        sumOfPurchase = biggestPrice();
                        ((Customer) this.session).setTotalPurchasesCost(sumOfPurchase);
                        showTotalPrice();
                        ((Customer) this.session).setLastPurchase(new Date());

                    } else {
                        System.out.println("this kind product is not in the stock , choose agian ");
                    }
                }
            }while (choose != -1 );
        } catch (Exception e) {
            System.out.println("wrong option\n");
                purchaseProduct();
        }
        }else {
            showProduct();
            System.out.println("choose a product to your cart: or -1 to Finish your sopping and back the Main menu");
            choose = scanner.nextInt();
            if (choose == -1) {
                showTotalPrice();
                System.out.println("Credit or cash?\n");
                start();
            }
            if (choose == shop.get(choose).getId() && shop.get(choose).isExist()) {

                System.out.println("how many you want?");
                numOfPro = scanner.nextInt();
                shop.get(choose).setNumOfProducts(numOfPro);
                this.session.getCart().add(shop.get(choose));


                System.out.println("your cart is :");
                for (int i = 0; i < this.session.getCart().size(); i++) {
                    System.out.println("- " + this.session.getCart().get(i).getNumOfProducts() + " -" + " of :" + this.session.getCart().get(i).getName()+" price --->" + this.session.getCart().get(i).getPrice());
                }
                ((Worker) this.session).setLastPurchase(new Date());
                showTotalPrice();

            } else {
                System.out.println("this kind product is not in the stock , choose agian ");
            }
        }
    }

    private void showProduct() {
        for (Product product : this.shop) {
            if (product.isExist())
                System.out.println(product);
        }
    }

    private void showTotalPrice() {
        int totalPrice = 0;
        int flag = 0;
        for (int i = 0; i < this.session.getCart().size(); i++) {
            totalPrice = this.session.getCart().get(i).getNumOfProducts();
            flag += totalPrice *  this.session.getCart().get(i).getPrice();
            if (this.session instanceof Customer ) {
                ((Customer) this.session).setTotalPurchasesCost(flag);
                System.out.println("the total price is :" +flag +"₪\n");
            }
        }

        if (this.session instanceof Worker) {
            if (((Worker) this.session).getDegree() == optionChoose.REGULAR && flag!=optionChoose.ZERO){
                System.out.println("the total price is :" +flag +"₪");
                System.out.println("after 10%");
                flag -=((flag/100)*10);
                System.out.println("the total price is :" +flag +"₪\n");
            }
            if (((Worker) this.session).getDegree() == optionChoose.MANAGER && flag!=optionChoose.ZERO){
                System.out.println("the total price is :" +flag +"₪");
                System.out.println("after 20%");
                flag -=((flag/100)*20);
                System.out.println("the total price is :" +flag +"₪\n");
            }else if (((Worker) this.session).getDegree() == optionChoose.CO_MANAGER && flag!=optionChoose.ZERO){
                System.out.println("the total price is :" +flag +"₪");
                System.out.println("after 30%");
                flag -=((flag/100)*30);
                System.out.println("the total price is :" +flag +"₪\n");
            }
        }

    }

    private void ChangeStatusForProduct() {
        Scanner scanner = new Scanner(System.in);
        int answer;
        String inStockOrNot;
        boolean notIn = false;
        System.out.println("Choose product to change is status");
        for (Product product : this.shop) {
            System.out.println(product);
        }
        answer = scanner.nextInt();
        for (Product product : this.shop) {
            if (product.getId() == answer) {
                System.out.println("Available in stock or not ---->  [YES / NO]");
                inStockOrNot = scanner.nextLine();
                while (!inStockOrNot.equals("YES") && !inStockOrNot.equals("NO")) {
                    System.out.println("INVALID INPUT TRY AGAIN!");
                    System.out.println("Available in stock or not ---->  [YES / NO]");
                    inStockOrNot = scanner.nextLine();
                }

                if (inStockOrNot.equals("NO")) {
                    product.setExist(false);
                    workerMenu();
                } else {
                    if (!product.isExist()) {
                        product.setExist(true);
                        workerMenu();
                    }
                }
            }
        }
    }

    private void AddingProductToTheStore() {
        Scanner scanner = new Scanner(System.in);
        String nameOfPro;
        boolean exist = true;
        int price;
        int discount ;
        System.out.println("What is the name of the Product");
        nameOfPro = scanner.nextLine();
        System.out.println("What is the price of the Product");
        price = scanner.nextInt();
        System.out.println("How much discount for member club");
        discount =scanner.nextInt();
        Product product = new Product(nameOfPro ,exist , price ,shop.size() ,discount ,optionChoose.ZERO);
        shop.add(product);
    }

    public void printAllCustomers() {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) instanceof Customer) {
                System.out.println(i + ")" + " " + clients.get(i));
            }
        }
    }

    public void printVIPCustomers() {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) instanceof Customer && ((Customer) clients.get(i)).isClubMember()) {
                System.out.println(i + ")" + " " + (Customer) clients.get(i));
            }
        }
    }

    public void printCustomersThatPurchased() {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) instanceof Customer && clients.get(i).getNumOfPurchases() != 0) {
                System.out.println(i + ")" + (Customer) clients.get(i)+ " This costumer do :"+ this.clients.get(i).getNumOfPurchases() + " Purchase.\n");
            }
        }
    }

    public void printCustomerBiggestPurchased() {
        int max =0;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) instanceof Customer && clients.get(i).getNumOfPurchases() != 0) {
                if (max < ((Customer) clients.get(i)).getTotalPurchasesCost()) {
                    max = ((Customer) clients.get(i)).getTotalPurchasesCost();
                }
            }
        }
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) instanceof Customer && ((Customer) clients.get(i)).getTotalPurchasesCost() ==max){
                System.out.println("the Customer with the Biggest Purchased is : " + (Customer) clients.get(i));
            }
        }
        System.out.println(" With: " + max + " Purchased\n");
    }

    private int biggestPrice() {
        int totalPrice = 0;
        int flag = 0;
        int sumOfPurchase = 0;
        for (int i = 0; i < this.session.getCart().size(); i++) {
            totalPrice = this.session.getCart().get(i).getNumOfProducts();
            flag += totalPrice *  this.session.getCart().get(i).getPrice();
        }

        return flag;
    }

    public ArrayList<Client> getClients() {
        return this.clients;
    }

}
