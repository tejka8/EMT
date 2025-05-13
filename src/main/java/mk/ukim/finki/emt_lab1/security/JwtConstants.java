package mk.ukim.finki.emt_lab1.security;

public class JwtConstants {
    public static final String SECRET_KEY = "1cebd833d8f1cb032622bcbcc9a75bd6affa02f1a55e0a7fa323404df1790a8d";
    public static final Long EXPIRATION_TIME = 864000000L;
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public JwtConstants() {
    }
}
