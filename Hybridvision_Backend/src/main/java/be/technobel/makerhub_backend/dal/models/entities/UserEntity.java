package be.technobel.makerhub_backend.dal.models.entities;

import be.technobel.makerhub_backend.dal.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User_")
@Getter @Setter
public class UserEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean active;
    private boolean blocked;
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.CLIENT;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private WishlistEntity wishlist;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ShoppingCartEntity shoppingCart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("Role_"+role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
