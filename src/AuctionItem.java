
public class AuctionItem {
    private int id;
    private String name;
    private String description;
    private String seller;
    private double startingPrice;
    private double highestBid;
    private String highestBidder;
    private boolean active;

    public AuctionItem(int id, String name,
                       String description, String seller,
                       double startingPrice) {
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
        System.out.println("Status: "
                + (active ? "Active" : "Closed"));
        System.out.println("--------------------------");
    }
}
