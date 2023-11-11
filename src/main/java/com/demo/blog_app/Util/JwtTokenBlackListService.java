package com.demo.blog_app.Util;

import com.demo.blog_app.Exception.MyCustomException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
@EnableScheduling
public class JwtTokenBlackListService {
    private Set<String> blacklistedTokens = new HashSet<>();
    public String  blackList(HttpServletRequest request) {
       String tokenHeader =  request.getHeader("Authorization");
        if (StringUtils.hasText(tokenHeader)&&tokenHeader.startsWith("Bearer ")) {
            String token1 = tokenHeader.substring(7);
           blacklistedTokens.add(token1);
           return "Successfully Logged out";
        }else {
            throw new MyCustomException("Unable to log out");
        }
}
public boolean isBlackListed(String token){
       return blacklistedTokens.contains(token);
}
@Scheduled(fixedRate = 3000000)
public void clearTokens(){
        blacklistedTokens.clear();
}
}
