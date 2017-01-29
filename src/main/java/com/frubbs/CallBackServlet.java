package com.frubbs;

import com.github.scribejava.apis.GeniusApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by rafa on 29/01/2017.
 */
public class CallBackServlet extends HttpServlet {
    private static final String PROTECTED_RESOURCE_URL = "https://api.genius.com/songs/378195";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ExecutionException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");

        final String code = request.getParameter("code");
        final String value = request.getParameter("state");

        if ("100".equals(value)) {
            System.out.println("State value does match!");
        } else {
            System.out.println("Ooops, state value does not match!");
            System.out.println("Got      = " + value);
            System.out.println();
        }

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
        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(View Access Token contents: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Accessing a protected resource...");
        final OAuthRequest req = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, req);
        final Response resp = service.execute(req);
        System.out.println("Got it! Viewing contents...");
        System.out.println();
        System.out.println(resp.getCode());
        System.out.println(resp.getBody());

        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");

        try (PrintWriter out = response.getWriter()) {
           out.println(resp.getCode());
           out.println(resp.getBody());

        }


        }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
