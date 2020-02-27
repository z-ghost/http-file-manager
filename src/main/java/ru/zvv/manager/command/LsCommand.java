package ru.zvv.manager.command;

import ru.zvv.manager.ICommand;
import ru.zvv.manager.ICommandResult;
import ru.zvv.manager.ParameterError;
import ru.zvv.manager.result.FileListResult;
import ru.zvv.manager.result.NotFountResult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class LsCommand implements ICommand {

    private static final String DIR_NAME_PARAM = "DirName";
    String dir;

    @Override
    public void parseParameters(HttpServletRequest request) {
        dir = request.getHeader(DIR_NAME_PARAM);
    }

    @Override
    public ICommandResult execute() {
        File dir = new File(this.dir);
        if (!dir.exists() && !dir.isDirectory())
            return new NotFountResult("There is no such directory");

        return new FileListResult(dir.listFiles());
    }

    @Override
    public ParameterError getErrors() {
        return dir == null  ? new ParameterError("There is no dir name") : null;
    }
}
