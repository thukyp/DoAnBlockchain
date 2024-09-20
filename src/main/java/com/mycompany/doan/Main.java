package com.mycompany.doan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Scanner scanner = new Scanner(System.in);
//        blockchain.createOrder(        // Mã đơn hàng
//            "Laptop",             // Tên sản phẩm
//            "Nguyen Van A",       // Tên người gửi
//            "Tran Thi B",         // Tên người nhận
//            "0123456789",         // Số điện thoại người gửi
//            "0987654321",         // Số điện thoại người nhận
//            "123 Main St",        // Địa chỉ người gửi
//            "456 Elm St",         // Địa chỉ người nhận
//            new Date(System.currentTimeMillis() + 5 * 24L * 60L * 60L * 1000L) // Thời gian dự tính giao hàng
//        );
//            blockchain.createOrder(
//            "Smartphone",         // Tên sản phẩm
//            "Le Thi C",           // Tên người gửi
//            "Nguyen Thi D",       // Tên người nhận
//            "0234567890",         // Số điện thoại người gửi
//            "0912345678",         // Số điện thoại người nhận
//            "789 Oak St",         // Địa chỉ người gửi
//            "321 Pine St",        // Địa chỉ người nhận
//            new Date(System.currentTimeMillis() + 3 * 24L * 60L * 60L * 1000L) // Thời gian dự tính giao hàng
//        );
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Tạo đơn hàng");
            System.out.println("2. Tra cứu đơn hàng");
            System.out.println("3. Cập nhật thông tin đơn hàng");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over after nextInt()

            switch (choice) {
                case 1:
                    // Tạo đơn hàng 
                    System.out.print("Nhập tên sản phẩm (Product Name): ");
                    String productName = scanner.nextLine();
                    System.out.print("Nhập tên người gửi (Sender Name): ");
                    String senderName = scanner.nextLine();
                    System.out.print("Nhập tên người nhận (Receiver Name): ");
                    String receiverName = scanner.nextLine();
                    System.out.print("Nhập số điện thoại người gửi (Sender Phone Number): ");
                    String senderPhoneNumber = scanner.nextLine();
                    System.out.print("Nhập số điện thoại người nhận (Receiver Phone Number): ");
                    String receiverPhoneNumber = scanner.nextLine();
                    System.out.print("Nhập địa chỉ người gửi (Sender Address): ");
                    String senderAddress = scanner.nextLine();
                    System.out.print("Nhập địa chỉ người nhận (Receiver Address): ");
                    String receiverAddress = scanner.nextLine();
                    System.out.print("Nhập số ngày dự tính giao hàng: ");
                    int deliveryDays = scanner.nextInt();
                    scanner.nextLine();  // Consume newline left-over after nextInt()
                    Date estimatedDeliveryTime = new Date(System.currentTimeMillis() + deliveryDays * 24L * 60L * 60L * 1000L);

                    blockchain.createOrder(productName, senderName, receiverName, senderPhoneNumber, receiverPhoneNumber, senderAddress, receiverAddress, estimatedDeliveryTime);
                    break;

                case 2:
                    // Tra cứu đơn hàng
                    System.out.print("Nhập mã đơn hàng để tra cứu: ");
                    String trackOrderId = scanner.nextLine();
                    blockchain.searchOrder(trackOrderId);
                    break;

                case 3:
                    // Cập nhật thông tin đơn hàng
                    System.out.print("Nhập mã đơn hàng cần cập nhật: ");
                    String updateOrderId = scanner.nextLine();
                    blockchain.updateOrder(updateOrderId);
                    break;

                case 4:
                    // Thoát chương trình
                    running = false;
                    System.out.println("Đang thoát chương trình...");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }

        scanner.close();
    }
}
