package ru.zvv.manager;

import javax.servlet.http.HttpServletRequest;

public interface ICommand {
    void parseParameters(HttpServletRequest request);

    ICommandResult execute();

    ParameterError getErrors();
}
