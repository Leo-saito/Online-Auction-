
import java.util.Scanner;

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
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Seller username: ");
                        String seller = sc.nextLine().trim();

                        System.out.print("Item name: ");
                        String name = sc.nextLine().trim();

                        System.out.print("Description: ");
                        String description = sc.nextLine().trim();

                        System.out.print("Starting price: ");
                        double price = Double.parseDouble(
                                sc.nextLine());

                        if (seller.isEmpty() || name.isEmpty()
                                || description.isEmpty()
                                || !Double.isFinite(price)
                                || price <= 0) {
                            System.out.println(
                                "Enter valid details and a "
                                + "positive starting price.");
                            break;
                        }

                        manager.addItem(name, description,
                                seller, price);
                        break;

                    case 2:
                        manager.viewAuctions();
                        break;

                    case 3:
                        System.out.print("Item ID: ");
                        int bidId = Integer.parseInt(
                                sc.nextLine());

                        System.out.print("Bidder username: ");
                        String bidder = sc.nextLine().trim();

                        System.out.print("Bid amount: ");
                        double amount = Double.parseDouble(
                                sc.nextLine());

                        if (bidder.isEmpty()
                                || !Double.isFinite(amount)) {
                            System.out.println(
                                "Enter valid bid details.");
                            break;
                        }

                        manager.placeBid(
                                bidId, bidder, amount);
                        break;

                    case 4:
                        System.out.print("Item ID to close: ");
                        int closeId = Integer.parseInt(
                                sc.nextLine());

                        manager.closeAuction(closeId);
                        break;

                    case 5:
                        System.out.println(
                                "Thank you for using the system!");
                        sc.close();
                        return;

                    default:
                        System.out.println(
                                "Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid number. Please try again.");
            }
        }
    }
}
