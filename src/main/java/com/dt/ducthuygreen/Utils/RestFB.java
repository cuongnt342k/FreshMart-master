package com.dt.ducthuygreen.Utils;

import com.dt.ducthuygreen.dto.MyUserDetail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RestFB {

    @Autowired
    private Environment env;

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = String.format("https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s", "1171397126960974",
                "99d56346e58f3f29c10206db5d591a19", "https://ducthuygreen.com:8080/login-facebook", code);
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public com.restfb.types.User getUserInfo(final String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, env.getProperty("facebook.app.secret"),
                Version.LATEST);
        return facebookClient.fetchObject("me", com.restfb.types.User.class);
    }

    public MyUserDetail buildUser(com.restfb.types.User user) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        MyUserDetail userDetail = new MyUserDetail(user.getId() + user.getName(), "", enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        Pattern pattern = Pattern.compile("[^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]*$]");
        Matcher matcher = pattern.matcher(userDetail.getUsername());
        userDetail.setFullName(matcher.replaceAll(""));
        return userDetail;
    }
}
