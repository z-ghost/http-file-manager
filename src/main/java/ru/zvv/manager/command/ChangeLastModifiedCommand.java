package ru.zvv.manager.command;

import ru.zvv.manager.ICommand;
import ru.zvv.manager.ICommandResult;
import ru.zvv.manager.ParameterError;
import ru.zvv.manager.result.BooleanResult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeLastModifiedCommand implements ICommand {

    public static final String FILE_NAME_PARAM = "FileName";
    public static final String LAST_MODIFIED_DATE_PARAM = "LastModifiedDate";
    private String fileName;
    private Date newDate;

    @Override
    public void parseParameters(HttpServletRequest request) {
        fileName = request.getHeader(FILE_NAME_PARAM);
        String newLastModified = request.getHeader(LAST_MODIFIED_DATE_PARAM);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        newDate = null;
        if (newLastModified != null) {
            try {
                newDate = sdf.parse(newLastModified);
            } catch (ParseException ignored) {

            }
        }
    }

    @Override
    public ICommandResult execute() {
        return new BooleanResult(new File(fileName).setLastModified(newDate.getTime()));

    }

    @Override
    public ParameterError getErrors() {
        return fileName == null || newDate == null ? new ParameterError("There is no file data or last modified date") : null;
    }
}
