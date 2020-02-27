package ru.zvv.manager.result;

import ru.zvv.manager.ICommandResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class NotFountResult implements ICommandResult {

    private final String msg;

    public NotFountResult(String msg) {
        this.msg = msg;
    }

    @Override
    public int getHttpStatus() {
        return HttpServletResponse.SC_NOT_FOUND;
    }

    @Override
    public Map<String, ?> getResult() {
        return Collections.singletonMap("error", msg);
    }
}
