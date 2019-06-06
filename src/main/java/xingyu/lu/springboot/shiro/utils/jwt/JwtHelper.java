package xingyu.lu.springboot.shiro.utils.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * (╯‵□′)╯︵┻━┻
 *
 * @author xingyu.lu
 * @date 16/11/11 15:42
 */
public class JwtHelper {
    private static final String UID = "uid";
    private static final String TYP = "typ";
    private static final String JWT = "JWT";

    /**
     * 解析token
     *
     * @param jWTString      token
     * @param base64Security key
     * @return
     */
    public static Claims parseJWT(String jWTString, String base64Security) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jWTString).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 生成token
     */
    public static String createJwt(Integer uid, String userName, String realName, String audience,
                                   Date nowDate, Date expiry, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(TYP, JWT)
                .claim(UID, uid)
                .claim("userName", userName)
                .claim("realName", realName)
                //该JWT的签发者
                .setIssuer(JwtConstant.ISSUER)
                //签发时间
                .setIssuedAt(nowDate)
                //该JWT所面向的用户
                .setSubject(uid.toString())
                //接收该JWT的一方
                .setAudience(audience)
                //UUID 避免重发
                .setId(UUID.randomUUID().toString())
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiry != null) {
            //过期时间
            builder.setExpiration(expiry)
                    //开始时间
                    .setNotBefore(nowDate);
        }
        //生成JWT
        return builder.compact();
    }

    /**
     * 检查token
     *
     * @param token token
     * @return
     */
    public static Claims checkLoginToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        //token实体
        Claims claims = JwtHelper.parseJWT(token, JwtConstant.BASE64);
        if (claims != null
                && JwtConstant.ISSUER.equals(claims.getIssuer())) {
            return claims;
        }

        return null;
    }
}
