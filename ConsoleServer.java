package socket;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

public class ConsoleServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("¼àÌý¶Ë¿ÚºÅ£º8888");

            while(true){
                Socket s = ss.accept();
                System.out.println(s);
                    new SendThread(s).start();
                    new RecieveThread(s).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}