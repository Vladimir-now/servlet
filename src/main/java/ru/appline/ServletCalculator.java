package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.appline.common.Common.readRequest;

@WebServlet(urlPatterns = "/calc")
public class ServletCalculator extends HttpServlet {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        String rqLine = readRequest(request);
        JsonObject jobj = gson.fromJson(rqLine, JsonObject.class);
        PrintWriter pw = response.getWriter();

        int a = jobj.get("a").getAsInt();
        int b = jobj.get("b").getAsInt();
        String sign = jobj.get("math").getAsString();
        int result;

        switch (sign) {
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            default:
                pw.print(gson.toJson("Неизвестная операция!"));
                return;
        }

        Result res = new Result(result);
        pw.print(gson.toJson(res));
    }
}
