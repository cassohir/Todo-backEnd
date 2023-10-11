package br.com.server.todolist.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.server.todolist.models.UserModel;
import br.com.server.todolist.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String authorization = servletRequest.getHeader("Authorization").substring("Basic".length()).trim();
        String authDecode = new String(Base64.getDecoder().decode(authorization));
        String username = authDecode.split(":")[0];
        String password = authDecode.split(":")[1];
        UserModel user = userService.getByUsername(username);
        if(user == null) {
            servletResponse.sendError(401, "Unauthorized User");
        } else {
         BCrypt.Result verification =  BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (verification.verified){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.sendError(401, "Unauthorized User");
            }
        }



    }
}
