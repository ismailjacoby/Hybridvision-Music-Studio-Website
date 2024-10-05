package be.technobel.makerhub_backend.dal.models.entities;

import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import be.technobel.makerhub_backend.dal.models.enums.MusicGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "Production")
public class ProductionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int bpm;
    private int duration;
    private boolean available;
    @Column(name = "play_count")
    private long playCount;
    @Column(name = "total_purchase")
    private int totalPurchased;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "cover_image")
    private String coverImage;
    @Column(name = "audio_mp3")
    private String audioMp3;
    @Column(name = "audio_wav")
    private String audioWav;
    @Column(name = "audio_zip")
    private String audioZip;
    private String stripePriceId;
    private MusicGenre genre;
    @Column(name="license_type")
    private LicenseType licenseType;

    @ManyToMany(mappedBy = "productions")
    private List<WishlistEntity> wishlists;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems;

}
