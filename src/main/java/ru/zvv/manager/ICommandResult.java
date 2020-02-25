package ru.zvv.manager;

import java.util.Map;

public interface ICommandResult {
    int getHttpStatus();

    Map<String,?> getResult();
}
