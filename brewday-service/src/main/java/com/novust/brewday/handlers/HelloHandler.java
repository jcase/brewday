package com.novust.brewday.handlers;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jcase on 2/13/15
 * All Copyrights apply
 */
public class HelloHandler  extends AbstractHandler {
    final String greeting;
    final String body;

    public HelloHandler(String greeting, String body) {
        this.greeting = greeting;
        this.body = body;
    }

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.println("<h1>" + greeting + "</h1>");
        if(StringUtils.isNotBlank(body)) {
            out.println(body);
        }
        baseRequest.setHandled(true);
    }
}
