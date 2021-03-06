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

import static ru.appline.common.Common.readRequest;

@WebServlet(urlPatterns = "/put")
public class ServletPut extends HttpServlet {

    private final Model model = Model.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        String rqLine = readRequest(request);
        JsonObject jobj = gson.fromJson(rqLine, JsonObject.class);
        PrintWriter pw = response.getWriter();

        int id = jobj.get("id").getAsInt();

        if(id <= 0) {
            pw.print(gson.toJson("ID пользователя должнен быть больше 0!"));
            return;
        }

        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();

        User user = new User(name, surname, salary);

        if (!model.getFromList().containsKey(id)) {
            pw.print(gson.toJson("Пользователь с ID: " + id + " не существует! Попробуйте снова."));
        } else {
            model.add(user, id);
            pw.print(gson.toJson("Пользователь с ID: " + id + " был изменен!"));
        }

        pw.print(gson.toJson(model.getFromList()));
    }
}
