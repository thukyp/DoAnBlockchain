package com.mycompany.doan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class Blockchain {
    private final List<Block> blockchain;
    private final Set<String> orderIds; // Tập hợp để lưu các mã đơn hàng đã được tạo
    private final int difficulty = 4;  // Độ khó cho Proof of Work (số lượng ký tự '0' trong hash)

    public Blockchain() {
        blockchain = new ArrayList<>();
        orderIds = new HashSet<>();  // Khởi tạo Set để lưu mã đơn hàng
        // Tạo block gốc (genesis block)
        Block genesisBlock = new Block("Genesis Block", "System", "System", "0000000000", "0000000000", 
                                       "No Address", "No Address", new Date(), "0", this);
        genesisBlock.mineBlock(difficulty);
        blockchain.add(genesisBlock);
    }
    
    public List<Block> getBlockchain() {
        return blockchain;
    }
    
    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            // Kiểm tra hash của block hiện tại có khớp với hash được tính toán không
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Hash của block hiện tại không khớp");
                return false;
            }

            // Kiểm tra hash của block trước có khớp với previousHash không
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Hash của block trước không khớp");
                return false;
            }
        }
        return true;
    }   
    
     public boolean isOrderIdUnique(String orderId) {
        return !orderIds.contains(orderId);
    }

    // Thêm mã đơn hàng vào danh sách
    public void addOrderId(String orderId) {
        orderIds.add(orderId);
    }
    
    // Tạo đơn hàng (block) mới
   public void createOrder(String productName, String senderName, String receiverName,
                        String senderPhoneNumber, String receiverPhoneNumber, String senderAddress, 
                        String receiverAddress, Date estimatedDeliveryTime) {
    String previousHash = blockchain.get(blockchain.size() - 1).getHash(); // Lấy hash của block trước
    Block newBlock = new Block(productName, senderName, receiverName, senderPhoneNumber,
                               receiverPhoneNumber, senderAddress, receiverAddress, estimatedDeliveryTime, previousHash, this);
    newBlock.mineBlock(difficulty);  // Thực hiện Proof of Work cho block mới
    blockchain.add(newBlock);        // Thêm block mới vào chuỗi

    // Kiểm tra tính toàn vẹn của chuỗi blockchain sau khi tạo block mới
    if (isChainValid()) {
        System.out.println("Đơn hàng được tạo thành công: " + newBlock);
    } else {
        System.out.println("Chuỗi blockchain không hợp lệ sau khi tạo đơn hàng!");
    }
}



    // Tra cứu đơn hàng
    public void searchOrder(String orderId) {
        Block block = getOrderById(orderId);
        if (block != null) {
            System.out.println("Thông tin đơn hàng:\n" + block);
        } else {
            System.out.println("Không tìm thấy đơn hàng với mã: " + orderId);
        }
    }

    // Cập nhật thông tin đơn hàng
    public void updateOrder(String orderId) {
    Block block = getOrderById(orderId);
    if (block != null) {
        Scanner scanner = new Scanner(System.in);

        boolean updating = true;
        while (updating) {
            System.out.println("\n===== CẬP NHẬT ĐƠN HÀNG =====");
            System.out.println("1. Cập nhật tên người gửi");
            System.out.println("2. Cập nhật tên người nhận");
            System.out.println("3. Cập nhật số điện thoại người gửi");
            System.out.println("4. Cập nhật số điện thoại người nhận");
            System.out.println("5. Cập nhật địa chỉ người gửi");
            System.out.println("6. Cập nhật địa chỉ người nhận");
            System.out.println("7. Cập nhật địa chỉ giao hàng");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng (1-10): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over after nextInt()

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên người gửi mới: ");
                    String newSenderName = scanner.nextLine();
                    block.setSenderName(newSenderName);
                    break;

                case 2:
                    System.out.print("Nhập tên người nhận mới: ");
                    String newReceiverName = scanner.nextLine();
                    block.setReceiverName(newReceiverName);
                    break;

                case 3:
                    System.out.print("Nhập số điện thoại người gửi mới: ");
                    String newSenderPhoneNumber = scanner.nextLine();
                    block.setSenderPhoneNumber(newSenderPhoneNumber);
                    break;

                case 4:
                    System.out.print("Nhập số điện thoại người nhận mới: ");
                    String newReceiverPhoneNumber = scanner.nextLine();
                    block.setReceiverPhoneNumber(newReceiverPhoneNumber);
                    break;

                case 5:
                    System.out.print("Nhập địa chỉ người gửi mới: ");
                    String newSenderAddress = scanner.nextLine();
                    block.setSenderAddress(newSenderAddress);
                    break;

                case 6:
                    System.out.print("Nhập địa chỉ người nhận mới: ");
                    String newReceiverAddress = scanner.nextLine();
                    block.setReceiverAddress(newReceiverAddress);
                    break;               
                case 7:
                    System.out.print("Nhập địa chỉ giao hàng mới: ");
                    String newAddress = scanner.nextLine();
                    block.setReceiverAddress(newAddress);
                    break;

                case 8:
                    updating = false;
                    System.out.println("Đang thoát cập nhật thông tin đơn hàng...");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }

            // Kiểm tra tính toàn vẹn của blockchain sau khi cập nhật
            if (isChainValid()) {
                System.out.println("Cập nhật thành công đơn hàng!");
            } else {
                System.out.println("Chuỗi blockchain không hợp lệ sau khi cập nhật!");
            }
        }
    } else {
        System.out.println("Không tìm thấy đơn hàng với mã: " + orderId);
    }
}


    // Tìm đơn hàng theo mã
    private Block getOrderById(String orderId) {
        for (Block block : blockchain) {
            if (block.getOrderId().equals(orderId)) {
                return block;
            }
        }
        return null; // Không tìm thấy đơn hàng
    }
}
