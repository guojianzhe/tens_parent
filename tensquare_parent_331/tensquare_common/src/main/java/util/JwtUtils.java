package util;

import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Date;


@ConfigurationProperties("jwt.config")
public class JwtUtils {
    //超时时间(分)
//    @Value("jwt.config.ttl")
    private Long ttl;

    //秘钥
//    @Value("jwt.config.key")
    private String key;

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //生成token

    /**
     *
     * @param id 登录用户名id
     * @param name 登录用户名称
     * @param roles 登录用户角色
     * @return
     */
    public String generateToken(String id,String name,String roles){
        //获得当前时间的毫秒数
        long  currentTime = System.currentTimeMillis();

        //获得过期时间数
        long expirtionTime = currentTime+ 1000*60*ttl;

        String token = Jwts.builder()//获得JwtToken的构建工具
                .setId(id)   //设置载荷部分的键值对
                .setSubject(name)  //设置载荷部分的键值对
                .claim("role", roles)       //设置自定义载荷,自定义载荷需要放置到有效时间之前
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirtionTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return token;
    }


    //解析token

    public Claims parseToken(String token){
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
