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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class Demo2Controller {

    @Autowired
    UserService serv;
    @PostMapping("/1st")
    public void signup(@RequestBody User user){
        serv.postUser(user);
    }

    @GetMapping("/2nd")
    public List<User> get(){
        return serv.getUser();
    }
    @GetMapping("/3rd/{id}")
    public User getbyid(@PathVariable Long id){
        return serv.getUserById(id);
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
}
