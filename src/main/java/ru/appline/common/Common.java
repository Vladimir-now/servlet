package ru.appline.common;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class Common {

    public static String readRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return sb.toString();
    }
}
