package sidkbk.celemo.payload.request;

import jakarta.validation.constraints.NotBlank;

public class SigninRequest {
    @NotBlank
    public String username;

    @NotBlank String password;

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void  setPassword(String password){
        this.password = password;
    }
}
