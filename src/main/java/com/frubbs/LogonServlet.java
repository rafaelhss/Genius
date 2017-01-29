package com.frubbs;

import com.github.scribejava.apis.GeniusApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by rafa on 29/01/2017.
 */
public class LogonServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        final String clientId = "Ken8_bis7iD8B67CrIFonncYO7XOghld-3Zsrtq2SDIeC6V-Zx9ikeSFmu0j4G5k";
        final String clientSecret = "m2nzpu5OGMbWHT7JoQG679X5kiaFmsKwdxNP1Ax7LL1M_kyiFbX36xft7SD18IqxAija782A-dlJeVyyThtn7g";
        final String secretState = "100";
        final OAuth20Service service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .scope("me")
                .state(secretState)
                .callback("http://localhost:8080/CallBack")
                .userAgent("ScribeJava")
                .build(GeniusApi.instance());

        final String authorizationUrl = service.getAuthorizationUrl();

        response.sendRedirect(authorizationUrl);

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx : authorizationUrl: " + authorizationUrl);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
