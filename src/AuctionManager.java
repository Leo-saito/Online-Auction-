
import java.util.ArrayList;

public class AuctionManager {
    private ArrayList<AuctionItem> items = new ArrayList<>();
    private int nextId = 1;

    public void addItem(String name, String description,
                        String seller, double startingPrice) {
        AuctionItem item = new AuctionItem(
                nextId++, name, description,
                seller, startingPrice);

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

        if (item.placeBid(bidder, amount)) {
            System.out.println("Bid placed successfully!");
        } else {
            System.out.println(
                "Bid rejected. Check the price, seller, "
                + "or auction status.");
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
            System.out.println("Winner: "
                    + item.getHighestBidder());
            System.out.println("Winning price: "
                    + item.getHighestBid());
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
