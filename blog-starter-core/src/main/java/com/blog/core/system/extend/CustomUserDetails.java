package com.blog.core.system.extend;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

/**
 * 定制UserDetail对象
 */
public class CustomUserDetails extends User {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String nickname;


    public CustomUserDetails(String username, String password, Long userId, String nickname,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }


    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(userId, getUsername(), nickname);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final CustomUserDetails other = (CustomUserDetails) obj;
        return Objects.equals(this.userId, other.userId)
                && Objects.equals(this.getUsername(), other.getUsername())
                && Objects.equals(this.nickname, other.nickname);
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "userId=" + userId +
                ", username='" + getUsername() + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
