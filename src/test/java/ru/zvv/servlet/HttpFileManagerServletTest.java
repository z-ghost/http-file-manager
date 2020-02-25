package ru.zvv.servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.zvv.manager.command.ChangeLastModifiedCommand;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class HttpFileManagerServletTest {

    @Test
    void doPostEmpty() throws IOException {
        HttpFileManagerServlet servlet = new HttpFileManagerServlet();
        servlet.init();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        servlet.doPost(request, response);
        verify(response).setStatus(Mockito.eq(HttpServletResponse.SC_BAD_REQUEST));
    }

    @Test
    void doPostErrorCommand() throws IOException {
        HttpFileManagerServlet servlet = new HttpFileManagerServlet();
        servlet.init();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        when(request.getHeader(HttpFileManagerServlet.COMMAND_PARAMETER_NAME)).thenReturn(HttpFileManagerServlet.COMMAND_CHANGE_LAST_MODIFIED);

        servlet.doPost(request, response);
        verify(response).setStatus(Mockito.eq(HttpServletResponse.SC_BAD_REQUEST));
    }

    @Test
    void doPostSuccessCommand() throws IOException, ParseException {
        File tempFile = File.createTempFile("temp", "doPostSuccessCommand");
        System.out.println(tempFile.getAbsolutePath());
        String newDate = "01.01.2000";
        long time = new SimpleDateFormat("dd.MM.yyyy").parse(newDate).getTime();
        assertNotEquals(time, tempFile.lastModified());
        HttpFileManagerServlet servlet = new HttpFileManagerServlet();
        servlet.init();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        when(request.getHeader(HttpFileManagerServlet.COMMAND_PARAMETER_NAME)).thenReturn(HttpFileManagerServlet.COMMAND_CHANGE_LAST_MODIFIED);
        when(request.getHeader(ChangeLastModifiedCommand.FILE_NAME_PARAM)).thenReturn(tempFile.getAbsolutePath());
        when(request.getHeader(ChangeLastModifiedCommand.LAST_MODIFIED_DATE_PARAM)).thenReturn(newDate);

        servlet.doPost(request, response);
        verify(response).setStatus(Mockito.eq(HttpServletResponse.SC_OK));

        assertEquals(time, tempFile.lastModified());
    }
}