package com.ctsi.springboot.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author lb
 *
 * @since 2018年4月23日
 *
 *
 */
@Component
public class JwtUtil {
	
	private static final Logger log = Logger.getLogger(JwtUtil.class);
	
	// 默认 3600 秒
//	@Value("${filter.token.time:3600}")
	private static int time = 3600;
	
	/**
     * 密钥
     */
    private static final String secret = "nc.moc.istc";
    
    private JwtUtil() {
    	log.info("JwtUtil 初始化默认时间 " + time);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String generateToken(Map<String, Object> claims) {
    	log.info("JwtUtil 设置默认时间 " + time);
//    	long time = 3600 * 1000 * 2; // 2小时
//    	long time = 60 * 1000 * 5; // 5分钟
    	int tokenTime = time * 1000;
    	long date = System.currentTimeMillis() + tokenTime;
        Date expirationDate = new Date(date);
        log.info("时间有效期 " + date + ", " + expirationDate);
        
        String token = Jwts.builder().setClaims(claims).setExpiration(expirationDate).
        		signWith(SignatureAlgorithm.HS512, secret).compact();
        log.info(token);
        
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static  Claims getClaimsFromToken(String token) {
        Claims claims;
        claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        try {
//        } 
//        catch (ExpiredJwtException e) {
//        	e.printStackTrace();
//        	claims = null;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            claims = null;
//        }
        return claims;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     */
    public static void validateToken(String token) throws Exception {
    	Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    	try {
//    	}
//    	catch (Exception ex) {
//    		log.info("解析令牌失败");
//    		ex.printStackTrace();
//    	}
    }

    @Value("${filter.token.time:3600}")
	public void setTime(int time) {
		JwtUtil.time = time;
	}

}
