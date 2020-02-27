package ru.zvv.manager.command;

import org.junit.jupiter.api.Test;
import ru.zvv.manager.ICommandResult;
import ru.zvv.manager.ParameterError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LsCommandTest {

    @Test
    void empty() {
        LsCommand lsCommand = new LsCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        lsCommand.parseParameters(request);
        ParameterError errors = lsCommand.getErrors();
        assertNotNull(errors);
    }

    @Test
    void execute() {
        LsCommand lsCommand = new LsCommand();
        lsCommand.dir = getClass().getResource(".").getFile();
        ICommandResult result = lsCommand.execute();
        assertEquals(HttpServletResponse.SC_OK,result.getHttpStatus());
        Object resultList = result.getResult().get("list");
        assertTrue(resultList instanceof List);
        List list = (List) resultList;
        boolean thisFileFinded = false;
        for (Object item : list) {
            assertTrue(item instanceof Map);
            Map map = (Map) item;
            if ("LsCommandTest.class".equals(map.get("name")))
                thisFileFinded = true;
        }
        assertTrue(thisFileFinded);

    }
}