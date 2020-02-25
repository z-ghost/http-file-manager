package ru.zvv.servlet;

import ru.zvv.json.JsonWriter;
import ru.zvv.manager.ICommand;
import ru.zvv.manager.ICommandResult;
import ru.zvv.manager.ParameterError;
import ru.zvv.manager.command.ChangeLastModifiedCommand;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HttpFileManagerServlet extends HttpServlet {

    public static final String COMMAND_PARAMETER_NAME = "Command";
    public static final String COMMAND_UPLOAD = "upload";
    public static final String COMMAND_CHANGE_LAST_MODIFIED = "changeLastModified";

    private Map<String, ICommand> commands = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getHeader(COMMAND_PARAMETER_NAME);

        if (command != null && commands.containsKey(command)) {
            execute(commands.get(command), request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(JsonWriter.write("error", "There is no command"));
        }
    }

    @Override
    public void init() {
        commands.put(COMMAND_CHANGE_LAST_MODIFIED, new ChangeLastModifiedCommand());
    }


    private void execute(ICommand command, HttpServletRequest request, HttpServletResponse response) throws IOException {
        command.parseParameters(request);

        ParameterError error = command.getErrors();
        if (error != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(JsonWriter.write("error", error.getMessage()));
            return;
        }

        ICommandResult res =  command.execute();
        response.setStatus(res.getHttpStatus());
        response.getOutputStream().println(JsonWriter.write(res.getResult()));
    }

}
