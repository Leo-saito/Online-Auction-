
import java.util.ArrayList;
import java.util.Scanner;

// ---------- User.java ----------
class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

// ---------- AuctionItem.java ----------
class AuctionItem {
    private int id;
    private String name;
    private String description;
    private String seller;
    private double startingPrice;
    private double highestBid;
    private String highestBidder;
    private boolean active;

    public AuctionItem(int id, String name, String description,
                       String seller, double startingPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.startingPrice = startingPrice;
        this.highestBid = startingPrice;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSeller() {
        return seller;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public boolean isActive() {
        return active;
    }

    public boolean placeBid(String bidder, double amount) {
        if (!active || bidder.equalsIgnoreCase(seller)
                || !Double.isFinite(amount)
                || amount <= highestBid || amount <= 0) {
            return false;
        }

        highestBid = amount;
        highestBidder = bidder;
        return true;
    }

    public void closeAuction() {
        active = false;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Item: " + name);
        System.out.println("Description: " + description);
        System.out.println("Seller: " + seller);
        System.out.println("Starting Price: " + startingPrice);
        System.out.println("Highest Bid: " + highestBid);
        System.out.println("Highest Bidder: "
                + (highestBidder == null
                ? "No bids yet" : highestBidder));
        System.out.println("Status: " + (active ? "Active" : "Closed"));
        System.out.println("--------------------------");
    }
}

// ---------- AuctionManager.java ----------
class AuctionManager {
    private ArrayList<AuctionItem> items = new ArrayList<>();
    private int nextId = 1;

    public void addItem(String name, String description,
                        String seller, double startingPrice) {
        AuctionItem item = new AuctionItem(
                nextId++, name, description, seller, startingPrice);

        items.add(item);
        System.out.println("Auction created successfully!");
        System.out.println("Item ID: " + item.getId());
    }

    public void viewAuctions() {
        if (items.isEmpty()) {
            System.out.println("No auctions available.");
            return;
        }

        for (AuctionItem item : items) {
            item.display();
        }
    }

    public void placeBid(int id, String bidder, double amount) {
        AuctionItem item = findItem(id);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (bidder.trim().isEmpty()) {
            System.out.println("Bidder name cannot be empty.");
            return;
        }

        if (item.placeBid(bidder, amount)) {
            System.out.println("Bid placed successfully!");
        } else {
            System.out.println(
                    "Bid rejected. Check price, seller, or auction status.");
        }
    }

    public void closeAuction(int id) {
        AuctionItem item = findItem(id);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (!item.isActive()) {
            System.out.println("Auction is already closed.");
            return;
        }

        item.closeAuction();
        System.out.println("Auction closed.");

        if (item.getHighestBidder() == null) {
            System.out.println("No bids were received.");
        } else {
            System.out.println("Winner: " + item.getHighestBidder());
            System.out.println("Winning price: " + item.getHighestBid());
        }
    }

    private AuctionItem findItem(int id) {
        for (AuctionItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}

// ---------- Main.java ----------
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuctionManager manager = new AuctionManager();

        while (true) {
            System.out.println("\n=== ONLINE AUCTION SYSTEM ===");
            System.out.println("1. Create Auction");
            System.out.println("2. View Auctions");
            System.out.println("3. Place Bid");
            System.out.println("4. Close Auction");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.print("Seller username: ");
                        String seller = sc.nextLine().trim();

                        System.out.print("Item name: ");
                        String name = sc.nextLine().trim();

                        System.out.print("Description: ");
                        String description = sc.nextLine().trim();

                        System.out.print("Starting price: ");
                        double price = Double.parseDouble(sc.nextLine().trim());

                        if (seller.isEmpty() || name.isEmpty()
                                || description.isEmpty()
                                || !Double.isFinite(price) || price <= 0) {
                            System.out.println(
                                    "Enter valid details and a positive price.");
                            break;
                        }

                        manager.addItem(name, description, seller, price);
                        break;

                    case 2:
                        manager.viewAuctions();
                        break;

                    case 3:
                        System.out.print("Item ID: ");
                        int bidId = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Bidder username: ");
                        String bidder = sc.nextLine().trim();

                        System.out.print("Bid amount: ");
                        double amount =
                                Double.parseDouble(sc.nextLine().trim());

                        if (bidder.isEmpty() || !Double.isFinite(amount)
                                || amount <= 0) {
                            System.out.println("Enter valid bid details.");
                            break;
                        }

                        manager.placeBid(bidId, bidder, amount);
                        break;

                    case 4:
                        System.out.print("Item ID to close: ");
                        int closeId =
                                Integer.parseInt(sc.nextLine().trim());

                        manager.closeAuction(closeId);
                        break;

                    case 5:
                        System.out.println(
                                "Thank you for using the system!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}
