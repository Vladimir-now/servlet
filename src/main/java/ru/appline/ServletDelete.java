package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.appline.common.Common.readRequest;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {

    private final Model model = Model.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        String rqLine = readRequest(request);
        JsonObject jobj = gson.fromJson(rqLine, JsonObject.class);
        PrintWriter pw = response.getWriter();

        int id = jobj.get("id").getAsInt();

        if (id == 0) {
            model.getFromList().clear();
            pw.print(gson.toJson("Все пользователи были удалены"));
        } else if (id > 0) {
            if (id > model.getFromList().size()) {
                pw.print(gson.toJson("Такого пользователя не существует"));
            } else {
                model.getFromList().remove(id);
                pw.print(gson.toJson("Пользователь с ID: " + id + " был удален!"));
            }
        } else {
            pw.print(gson.toJson("ID должен быть больше нуля"));
        }
    }
}
