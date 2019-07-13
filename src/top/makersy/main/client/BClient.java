package top.makersy.main.client;

import java.io.IOException;

/**
 * Created by makersy on 2019
 */

public class BClient {
    public static void main(String[] args) throws IOException {
        new NioClient().start("BClient");
    }
}
