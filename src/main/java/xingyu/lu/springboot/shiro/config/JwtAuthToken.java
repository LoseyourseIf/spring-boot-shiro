package xingyu.lu.springboot.shiro.config;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtAuthToken implements AuthenticationToken {

    private static final long serialVersionUID = 6198902331636481894L;
    private String token;

    private JwtAuthToken(String token) {
        this.token = token;
    }

    static JwtAuthToken buildToken(String token) {
        return new JwtAuthToken(token);
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
