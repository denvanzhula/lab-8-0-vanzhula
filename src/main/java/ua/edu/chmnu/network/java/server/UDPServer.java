package ua.edu.chmnu.network.java.server;

import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class UDPServer {

    static class ClientInfo {
        InetAddress address;
        int port;
        String name;
        String time;

        public ClientInfo(InetAddress address, int port, String name, String time) {
            this.address = address;
            this.port = port;
            this.name = name;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(12345);
            System.out.println("UDP Server is running...");

            List<ClientInfo> clients = new ArrayList<>();
            clients.add(new ClientInfo(InetAddress.getByName("localhost"), 9876, "Den Vanzhula", "12:30"));
            clients.add(new ClientInfo(InetAddress.getByName("localhost"), 9876, "Denis Vanzhula", "12:31"));

            while (true) {

                String currentTime = getCurrentTime();

                for (ClientInfo client : clients) {
                    if (client.time.equals(currentTime)) {
                        String message = "Hello " + client.name + ", this is a message sent at " + currentTime;
                        byte[] sendData = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.address, client.port);

                        socket.send(sendPacket);
                        System.out.println("Sent message to " + client.name + " at " + currentTime);
                    }
                }

                TimeUnit.SECONDS.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }
}
