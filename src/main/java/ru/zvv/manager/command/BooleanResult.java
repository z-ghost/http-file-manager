package ru.zvv.manager.command;

import ru.zvv.manager.ICommandResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class BooleanResult implements ICommandResult {

    private final boolean result;

    public BooleanResult(boolean result) {
        this.result = result;
    }

    @Override
    public int getHttpStatus() {
        return result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public Map<String,?> getResult() {
        return Collections.singletonMap("result", result ? "success" : "error");
    }
}
