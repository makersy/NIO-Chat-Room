package top.makersy.main.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by makersy on 2019
 */

/*
 NIO客户端，封装启动代码
 */
public class NioClient {

    /**
     * 启动方法
     */
    public void start(String nickname) throws IOException {

        //连接服务器端
        SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("127.0.0.1", 8000));

        System.out.println("客户端 " + nickname + " 启动成功!");

        //接收服务器端响应
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);  //非阻塞
        socketChannel.register(selector, SelectionKey.OP_READ);  //注册为接收模式
        //需要新开一个线程，专门负责接收服务端的响应数据。因为当前线程需要负责发送给服务器端数据
        new Thread(new NioClientHandler(selector)).start();  //新开线程

        //向服务器端发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String request = scanner.nextLine();
            if (request != null && request.length() > 0) {
                socketChannel.write(Charset.forName("UTF-8").encode(nickname + " : " + request));
            }
        }
    }

}
