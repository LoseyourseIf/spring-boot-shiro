package xingyu.lu.springboot.shiro.utils.jwt;


/**
 * (╯‵□′)╯︵┻━┻
 *
 * <p>description</p>
 *
 * @author xingyu.lu
 * @date 18/10/18 15:41
 */
public interface JwtConstant {

    String BASE64 = "eGluZ3l1Lmx1LmRldg==";

    String ISSUER = "xingyu.lu";

    String ADMIN_AUDIENCE = "ADMIN";

    String USER_AUDIENCE = "USER";

    String BLACKLIST_AUDIENCE = "BLACKLIST";

    String TOKEN_KEY = "XY-TOKEN";
}
