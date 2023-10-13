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
        String servletPath =  servletRequest.getServletPath();
        if(Paths.TASKS.getPath().startsWith(servletPath)){
            String authorization = servletRequest.getHeader("Authorization").substring("Basic".length()).trim();
            String authDecode = new String(Base64.getDecoder().decode(authorization));
            UserModel user = userService.getByUsername(authDecode.split(":")[0]);
            if(user == null) {
                servletResponse.sendError(401, "Unauthorized User");
            } else {
                BCrypt.Result verification =  BCrypt.verifyer().verify(authDecode.split(":")[1].toCharArray(), user.getPassword());
                if (verification.verified){
                    servletRequest.setAttribute("idUser", user.getId());
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    servletResponse.sendError(401, "Unauthorized User");
                }
            }
        } else  filterChain.doFilter(servletRequest,servletResponse);
    }
}
