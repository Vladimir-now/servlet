package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.appline.common.Common.readRequest;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private final Model model = Model.getInstance();
    private final AtomicInteger counter = new AtomicInteger(model.getFromList().size() + 1);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        String rqLine = readRequest(request);
        JsonObject jobj = gson.fromJson(rqLine, JsonObject.class);

        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.add(user, counter.getAndIncrement());

        PrintWriter pw = response.getWriter();
        pw.print(gson.toJson(model.getFromList()));
    }


}
