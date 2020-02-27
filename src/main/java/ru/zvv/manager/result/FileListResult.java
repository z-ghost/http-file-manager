package ru.zvv.manager.result;

import ru.zvv.manager.ICommandResult;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileListResult implements ICommandResult {

    private final File[] list;

    public FileListResult(File[] list) {
        this.list = list;
    }

    @Override
    public int getHttpStatus() {
        return HttpServletResponse.SC_OK;
    }

    @Override
    public Map<String, ?> getResult() {
        ArrayList<Map> fileList = new ArrayList<>();
        for (File file : list) {
            LinkedHashMap<String, Object> fd = new LinkedHashMap<>();
            fd.put("name", file.getName());
            fd.put("lastModified", file.lastModified());
            fd.put("type", file.isDirectory() ? "dir" : "file");
            fileList.add(fd);
        }
        return Collections.singletonMap("list", fileList);
    }
}
