package schema.response;

public class LoginResponseSchema extends BaseResponseSchema{
    public String token;

    public LoginResponseSchema(String token) {
        this.token = token;
    }
}
