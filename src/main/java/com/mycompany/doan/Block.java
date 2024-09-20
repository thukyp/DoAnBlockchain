package com.mycompany.doan;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import java.util.Date;
import java.util.Random;

public class Block {
    private String orderId;
    private String productName;
    private String senderName;
    private String receiverName;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private String senderAddress;
    private String receiverAddress;
    private String shippingStatus;
    private Date estimatedDeliveryTime;
    private String previousHash;
    private String hash;
    private long timestamp;
    private int nonce;
    private Blockchain blockchain;  // Tham chiếu đến Blockchain để kiểm tra mã đơn hàng

    public Block(String productName, String senderName, String receiverName, String senderPhoneNumber,
                 String receiverPhoneNumber, String senderAddress, String receiverAddress, Date estimatedDeliveryTime,
                 String previousHash, Blockchain blockchain) {
        this.blockchain = blockchain;
        this.orderId = generateUniqueOrderId();  // Tạo mã đơn hàng duy nhất
        this.productName = productName;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderPhoneNumber = senderPhoneNumber;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.shippingStatus = "Chưa giao";
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateHash();
    }

    // Phương thức sinh mã đơn hàng duy nhất
    private String generateUniqueOrderId() {
        String orderId;
        do {
            orderId = generateOrderId();  // Sinh mã đơn hàng
        } while (!blockchain.isOrderIdUnique(orderId));  // Kiểm tra mã có trùng không
        blockchain.addOrderId(orderId);  // Thêm mã đơn hàng vào danh sách
        return orderId;
    }

    // Phương thức sinh mã đơn hàng ngẫu nhiên
    private String generateOrderId() {
        Random random = new Random();
        int randomNumber = random.nextInt(99999999);
        return String.format("VN%08d", randomNumber);
    }
    // Tính toán mã hash
    public String calculateHash() {
        String dataToHash = previousHash + orderId + productName + senderName + receiverName +
                            senderPhoneNumber + receiverPhoneNumber + senderAddress + receiverAddress +
                            shippingStatus + estimatedDeliveryTime + timestamp + nonce;
        MessageDigest digest;
        String encoded = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            encoded = bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encoded;
    }
    
    
    
    // Proof of Work
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! Hash: " + hash);
    }

    // Chuyển đổi byte array thành chuỗi hex
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public Date getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    // Setters
    public void setSenderName(String senderName) {
        this.senderName = senderName;
        this.hash = calculateHash();
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        this.hash = calculateHash();
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.hash = calculateHash();
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.hash = calculateHash();
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
        this.hash = calculateHash();
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
        this.hash = calculateHash();
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
        this.hash = calculateHash();
    }

    @Override

    public String toString() {
        return "Order ID: " + orderId + "\n" +
           "Product Name: " + productName + "\n" +
           "Sender Name: " + senderName + "\n" +
           "Receiver Name: " + receiverName + "\n" +
           "Sender Phone Number: " + senderPhoneNumber + "\n" +
           "Receiver Phone Number: " + receiverPhoneNumber + "\n" +
           "Sender Address: " + senderAddress + "\n" +
           "Receiver Address: " + receiverAddress + "\n" +
           "Shipping Status: " + shippingStatus + "\n" +
           "Estimated Delivery Time: " + estimatedDeliveryTime + "\n" +
           "Previous Hash: " + previousHash + "\n";
}

}
 