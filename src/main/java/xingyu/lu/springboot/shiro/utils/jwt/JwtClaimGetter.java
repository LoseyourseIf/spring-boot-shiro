package xingyu.lu.springboot.shiro.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Objects;

/**
 * @author xingyu.lu
 * @create 2018-05-09 11:06
 **/
public class JwtClaimGetter {

    public static final String UID = "uid";

    public static <T> T getClaimValue(String token,
                                      String claimName,
                                      Class<T> requiredType) {
        Claims claims = JwtHelper.parseJWT(token, JwtConstant.BASE64);
        T claimValue = null;
        if (claims != null) {
            claimValue = Objects.requireNonNull(claims.get(claimName, requiredType));
        }
        return claimValue;
    }

    public static <T> T getClaimValue(HttpServletRequest request,
                                      String claimName,
                                      Class<T> requiredType) {
        String token = request.getHeader(JwtConstant.TOKEN_KEY);
        Claims claims = JwtHelper.parseJWT(token, JwtConstant.BASE64);
        T claimValue = null;
        if (claims != null) {
            claimValue = Objects.requireNonNull(claims.get(claimName, requiredType));
        }
        return claimValue;
    }

    public static <T> T getExpiredClaimValue(HttpServletRequest request,
                                             String claimName,
                                             Class<T> requiredType) {
        String token = request.getHeader(JwtConstant.TOKEN_KEY);
        Claims claims = JwtClaimGetter.parseExpiredJWT(token, JwtConstant.BASE64);
        T claimValue = null;
        if (claims != null) {
            claimValue = Objects.requireNonNull(claims.get(claimName, requiredType));
        }
        return claimValue;
    }

    /**
     * 解析token
     *
     * @param jWTString      token
     * @param base64Security key
     * @return
     */
    public static Claims parseExpiredJWT(String jWTString, String base64Security) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jWTString).getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

}
