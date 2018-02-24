package id.veintechnology.apps.library.id.veintechnology.apps.dao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "authentication_token")
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_id")
    private AuthenticationUser authUser;

    @Column(name = "token")
    private String token;

    @Column(name = "expired_at")
    private Date expiredAt;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "last_accessed_on")
    private Date lastAccessOn;

    public AuthenticationToken() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthenticationUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthenticationUser authUser) {
        this.authUser = authUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastAccessOn() {
        return lastAccessOn;
    }

    public void setLastAccessOn(Date lastAccessOn) {
        this.lastAccessOn = lastAccessOn;
    }
}
