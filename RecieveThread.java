package socket;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;

public class RecieveThread extends Thread {
    private Socket s;

    public RecieveThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            is = s.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String s1 = reader.readLine();
//            System.out.println(s1);
//            System.out.println(s1.indexOf('/'));
            String resource = s1.substring(s1.indexOf('/'), s1.lastIndexOf('/') - 5);
            System.out.println("the resource you request is: " + resource);


            String resData = null;

            StringBuffer stringBuffer = new StringBuffer();
            File htmlData = new File("res/html" + resource);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(htmlData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                StringBuilder response = new StringBuilder();
                response.append("HTTP/1.1 200 OK\r\n")
                        .append("Content-Length: ").append(resData.getBytes().length).append("\r\n")
                        .append("Content-Type: text/html; charset=utf-8\r\n")
                        .append("\r\n")
                        .append("404 not found").append("\r\n");
                System.out.println(response);
                OutputStream outputStream = this.s.getOutputStream();
                outputStream.write(response.toString().getBytes());
                outputStream.flush();
            }

            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                stringBuffer.append(new String(bytes, 0, len));
            }
            resData = stringBuffer.toString();
            //System.out.println(resData);


            StringBuilder response = new StringBuilder();
            response.append("HTTP/1.1 200 OK\r\n")
                    .append("Content-Length: ").append(resData.getBytes().length).append("\r\n")
                    .append("Content-Type: text/html; charset=utf-8\r\n")
                    .append("\r\n")
                    .append(resData).append("\r\n");
            System.out.println(response);
            OutputStream outputStream = this.s.getOutputStream();
            outputStream.write(response.toString().getBytes());
            outputStream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}