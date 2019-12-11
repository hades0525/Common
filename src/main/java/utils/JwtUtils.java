//package com.citycloud.ccuap.intellisense.utils;
//
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
///**
// * @description: jwt示例
// * @author: tsf
// * @create: 2019-11-19 17:36
// **/
//@Component
//public class JwtUtils {
//
//    //私钥
//    @Value("${private.key}")
//    private String privateKey;
//
//    //token过期时间
//    private  Duration timeout = Duration.ofMinutes(5);
//
//    //生成token
//    public  String create()
//    {
//        LocalDateTime localDateTime =  LocalDateTime.now().plusMinutes(timeout.toMinutes());
//        Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
//        String token ="";
//        try {
//            token = JWT.create()
//                    .withExpiresAt(date)
//                    .sign(Algorithm.HMAC256(privateKey));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return token;
//    }
//
//
//    /***
//     * 校验token是否有效
//     * @param token
//     * @return
//     */
//    public  boolean verify(String token)
//    {
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey))
//                    .build();
//            verifier.verify(token);
//            return  true;
//        } catch (JWTVerificationException e) {
//            e.printStackTrace();
//            return  false;
//        }
//    }
//
//}
