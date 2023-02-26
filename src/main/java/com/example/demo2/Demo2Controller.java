package com.example.demo2;

import com.example.demo2.entity.SignUser;
import com.example.demo2.entity.User;
import com.example.demo2.entity.UserName;
import com.example.demo2.service.UserService;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class Demo2Controller {


    @Autowired
    UserService serv;


    @PostMapping("/1st/sign")
    public void signup(@RequestBody User user){
        serv.postUser(user);
    }

    @GetMapping("/2nd")
    public List<User> get(){
        return serv.getUser();
    }
    @GetMapping("/3rd/{id}")
    public User getbyid(@PathVariable Long id){

            return (serv.getUserById(id));
    }
    @PostMapping("/4th")
    public ResponseEntity<?> getbyname(@RequestBody SignUser user,HttpServletResponse response) throws OAuthSystemException, OAuthProblemException {
        OAuthClient client = new OAuthClient(new URLConnectionClient());

        OAuthClientRequest request =
                OAuthClientRequest.tokenLocation("https://dev-27375836.okta.com/oauth2/default/v1/token")
                        .setGrantType(GrantType.CLIENT_CREDENTIALS)
                        .setClientId("0oa84mfq1vbS4Vl0J5d7")
                        .setClientSecret("yf2WTqrABxJtpv6xkwzQ-ZhR9dmPvDAgQ5aOYXd4")
                        .setScope("myEndpoints")
                        .buildBodyMessage();
        String token = client.accessToken(request,
                OAuth.HttpMethod.POST,
                OAuthJSONAccessTokenResponse.class).getAccessToken();
         Cookie cookie=new Cookie("token",token);
        System.out.println("controller");
        System.out.println(cookie.getValue());
        if(serv.getByName(user).size()==0)

            return new ResponseEntity<>(null,HttpStatus.OK);
        else{
             response.addCookie(cookie);
            return new ResponseEntity<>(serv.getByName(user).get(0),HttpStatus.OK);}
    }

    @PostMapping("/6th")
    public User getName(@RequestBody UserName uname){
        if(serv.getName(uname).size()==0)
        return null;
        else
        return serv.getName(uname).get(0);
    }

    @DeleteMapping("/5th/{id}")
    public void delete(@PathVariable Long id){
        serv.delete(id);
    }
    @PostMapping("/1st/log")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the user's session
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        // Redirect the user to the login page
        return "/";
    }
}
