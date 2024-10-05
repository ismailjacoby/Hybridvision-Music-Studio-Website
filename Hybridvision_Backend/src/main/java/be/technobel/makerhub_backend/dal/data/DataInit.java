package be.technobel.makerhub_backend.dal.data;

import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import be.technobel.makerhub_backend.dal.models.entities.SamplePackEntity;
import be.technobel.makerhub_backend.dal.models.entities.ShoppingCartEntity;
import be.technobel.makerhub_backend.dal.models.entities.UserEntity;
import be.technobel.makerhub_backend.dal.models.enums.MusicGenre;
import be.technobel.makerhub_backend.dal.models.enums.UserRole;
import be.technobel.makerhub_backend.dal.repositories.ProductionRepository;
import be.technobel.makerhub_backend.dal.repositories.SamplePackRepository;
import be.technobel.makerhub_backend.dal.repositories.ShoppingCartRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInit implements InitializingBean {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductionRepository productionRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final SamplePackRepository samplePackRepository;

    public DataInit(UserRepository userRepository, PasswordEncoder passwordEncoder,
                    ProductionRepository productionRepository, ShoppingCartRepository shoppingCartRepository,
                    SamplePackRepository samplePackRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productionRepository = productionRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.samplePackRepository = samplePackRepository;
    }

    /*
    * Creates a custom dummy database to test out different methods.
    * */
    @Override
    public void afterPropertiesSet() throws Exception {
        //ADMIN - Main
        UserEntity hybridvision = new UserEntity();
        hybridvision.setId(1L);
        hybridvision.setUsername("hybridvision");
        hybridvision.setFirstName("Hybrid");
        hybridvision.setLastName("Vision");
        hybridvision.setEmail("hybridvisionmusic@gmail.com");
        hybridvision.setActive(true);
        hybridvision.setBlocked(false);
        hybridvision.setRole(UserRole.ADMIN);
        hybridvision.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(hybridvision);


        //ADMIN - Ismail
        UserEntity ismail = new UserEntity();
        ismail.setId(2L);
        ismail.setUsername("ismailjacoby");
        ismail.setFirstName("Ismail");
        ismail.setLastName("Jacoby");
        ismail.setEmail("ismailjacoby@gmail.com");
        ismail.setActive(true);
        ismail.setBlocked(false);
        ismail.setRole(UserRole.ADMIN);
        ismail.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(ismail);


        //ADMIN - Francisco
        UserEntity francisco = new UserEntity();
        francisco.setId(3L);
        francisco.setUsername("franciscolopesmorais");
        francisco.setFirstName("Francisco");
        francisco.setLastName("Lopes Morais");
        francisco.setEmail("n1djfable@gmail.com");
        francisco.setActive(true);
        francisco.setBlocked(false);
        francisco.setRole(UserRole.ADMIN);
        francisco.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(francisco);

        //CLIENT - string
        UserEntity user1 = new UserEntity();
        user1.setId(4L);
        user1.setUsername("stringstring");
        user1.setFirstName("String");
        user1.setLastName("String");
        user1.setEmail("string@gmail.com");
        user1.setActive(true);
        user1.setBlocked(false);
        user1.setRole(UserRole.CLIENT);
        user1.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(user1);

        ShoppingCartEntity user1Cart = new ShoppingCartEntity();
        user1Cart.setUser(user1);
        shoppingCartRepository.save(user1Cart);

        //CLIENT - string
        UserEntity user2 = new UserEntity();
        user2.setId(5L);
        user2.setUsername("testtest");
        user2.setFirstName("Test");
        user2.setLastName("Test");
        user2.setEmail("test@gmail.com");
        user2.setActive(true);
        user2.setBlocked(false);
        user2.setRole(UserRole.CLIENT);
        user2.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(user2);

        ShoppingCartEntity user2Cart = new ShoppingCartEntity();
        user2Cart.setUser(user2);
        shoppingCartRepository.save(user2Cart);

        // CLIENT - Travis Scott
        UserEntity travisScott = new UserEntity();
        travisScott.setId(6L);
        travisScott.setUsername("travisscott");
        travisScott.setFirstName("Travis");
        travisScott.setLastName("Scott");
        travisScott.setEmail("travisscott@example.com");
        travisScott.setActive(true);
        travisScott.setBlocked(false);
        travisScott.setRole(UserRole.CLIENT);
        travisScott.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(travisScott);

        ShoppingCartEntity travisScottCart = new ShoppingCartEntity();
        travisScottCart.setUser(travisScott);
        shoppingCartRepository.save(travisScottCart);

        // CLIENT - Nicki Minaj
        UserEntity nickiminaj = new UserEntity();
        nickiminaj.setId(7L);
        nickiminaj.setUsername("nickiminaj");
        nickiminaj.setFirstName("Nicki");
        nickiminaj.setLastName("Minaj");
        nickiminaj.setEmail("nickiminaj@example.com");
        nickiminaj.setActive(true);
        nickiminaj.setBlocked(false);
        nickiminaj.setRole(UserRole.CLIENT);
        nickiminaj.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(nickiminaj);

        ShoppingCartEntity nickiMinajCart = new ShoppingCartEntity();
        nickiMinajCart.setUser(nickiminaj);
        shoppingCartRepository.save(nickiMinajCart);

        // CLIENT - Pop Smoke
        UserEntity popsmoke = new UserEntity();
        popsmoke.setId(8L);
        popsmoke.setUsername("popsmoke");
        popsmoke.setFirstName("Pop");
        popsmoke.setLastName("Smoke");
        popsmoke.setEmail("popsmoke@example.com");
        popsmoke.setActive(true);
        popsmoke.setBlocked(false);
        popsmoke.setRole(UserRole.CLIENT);
        popsmoke.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(popsmoke);

        ShoppingCartEntity popSmokeCart = new ShoppingCartEntity();
        popSmokeCart.setUser(popsmoke);
        shoppingCartRepository.save(popSmokeCart);

        // CLIENT - Snoop Dogg
        UserEntity snoopdogg = new UserEntity();
        snoopdogg.setId(9L);
        snoopdogg.setUsername("snoopdogg");
        snoopdogg.setFirstName("Snoop");
        snoopdogg.setLastName("Dogg");
        snoopdogg.setEmail("snoopdogg@example.com");
        snoopdogg.setActive(true);
        snoopdogg.setBlocked(false);
        snoopdogg.setRole(UserRole.CLIENT);
        snoopdogg.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(snoopdogg);

        ShoppingCartEntity snoopDoggCart = new ShoppingCartEntity();
        snoopDoggCart.setUser(snoopdogg);
        shoppingCartRepository.save(snoopDoggCart);

        // CLIENT - Big Sean
        UserEntity bigsean = new UserEntity();
        bigsean.setId(10L);
        bigsean.setUsername("bigsean");
        bigsean.setFirstName("Big");
        bigsean.setLastName("Sean");
        bigsean.setEmail("bigsean@example.com");
        bigsean.setActive(true);
        bigsean.setBlocked(false);
        bigsean.setRole(UserRole.CLIENT);
        bigsean.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(bigsean);

        ShoppingCartEntity bigSeanCart = new ShoppingCartEntity();
        bigSeanCart.setUser(bigsean);
        shoppingCartRepository.save(bigSeanCart);

        // CLIENT - Post Malone
        UserEntity postmalone = new UserEntity();
        postmalone.setId(11L);
        postmalone.setUsername("postmalone");
        postmalone.setFirstName("Post");
        postmalone.setLastName("Malone");
        postmalone.setEmail("postmalone@example.com");
        postmalone.setActive(true);
        postmalone.setBlocked(false);
        postmalone.setRole(UserRole.CLIENT);
        postmalone.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(postmalone);

        ShoppingCartEntity postMaloneCart = new ShoppingCartEntity();
        postMaloneCart.setUser(postmalone);
        shoppingCartRepository.save(postMaloneCart);

        // CLIENT - Kanye West
        UserEntity kanyewest = new UserEntity();
        kanyewest.setId(12L);
        kanyewest.setUsername("kanyewest");
        kanyewest.setFirstName("Kanye");
        kanyewest.setLastName("West");
        kanyewest.setEmail("kanyewest@example.com");
        kanyewest.setActive(true);
        kanyewest.setBlocked(false);
        kanyewest.setRole(UserRole.CLIENT);
        kanyewest.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(kanyewest);

        ShoppingCartEntity kanyeWestCart = new ShoppingCartEntity();
        kanyeWestCart.setUser(kanyewest);
        shoppingCartRepository.save(kanyeWestCart);

        // CLIENT - Client
        UserEntity client = new UserEntity();
        client.setId(13L);
        client.setUsername("client");
        client.setFirstName("Client");
        client.setLastName("Client");
        client.setEmail("client@example.com");
        client.setActive(true);
        client.setBlocked(false);
        client.setRole(UserRole.CLIENT);
        client.setPassword(passwordEncoder.encode("Test1234="));
        userRepository.save(client);

        ShoppingCartEntity clientCart = new ShoppingCartEntity();
        clientCart.setUser(client);
        shoppingCartRepository.save(clientCart);

        //Beat - Requiem
        ProductionEntity requiem = new ProductionEntity();
        requiem.setId(1L);
        requiem.setTitle("Requiem");
        requiem.setBpm(150);
        requiem.setAudioMp3("https://hybridvision.s3.eu-west-3.amazonaws.com/c787f3a1-3712-4ac7-a232-97f852844d7c.mp3");
        requiem.setCoverImage("https://hybridvision.s3.eu-west-3.amazonaws.com/05d90b6b-de35-48d4-b630-eedbb324123e.png");
        requiem.setReleaseDate(LocalDate.now());
        requiem.setGenre(MusicGenre.DRILL);
        requiem.setStripePriceId("price_1OxvHcIOjZy9IHl8UaT9RGwm");
        requiem.setDuration(179);
        requiem.setAvailable(true);
        productionRepository.save(requiem);

        //Beat - Too late
        ProductionEntity toolate = new ProductionEntity();
        toolate.setId(2L);
        toolate.setTitle("Too Late");
        toolate.setBpm(142);
        toolate.setAudioMp3("https://hybridvision.s3.eu-west-3.amazonaws.com/6481919f-5873-4b8b-86dd-cfb1e1e36025.mp3");
        toolate.setCoverImage("https://hybridvision.s3.eu-west-3.amazonaws.com/4ab07b8b-ea7e-4c3b-8cbd-e94ab3d40634.png");
        toolate.setReleaseDate(LocalDate.now());
        toolate.setGenre(MusicGenre.DRILL);
        toolate.setStripePriceId("price_1OxvFLIOjZy9IHl8Z02GTIkV");
        toolate.setDuration(67);
        toolate.setAvailable(true);
        productionRepository.save(toolate);

        //Beat - Dakar
        ProductionEntity dakar = new ProductionEntity();
        dakar.setId(3L);
        dakar.setTitle("Dakar");
        dakar.setBpm(98);
        dakar.setAudioMp3("https://hybridvision.s3.eu-west-3.amazonaws.com/3a6dc9ea-0f69-4298-83af-10eeda60657e.mp3");
        dakar.setCoverImage("https://hybridvision.s3.eu-west-3.amazonaws.com/f9167a11-067a-4365-8e1a-b06f3a4c7b2a.png");
        dakar.setReleaseDate(LocalDate.now());
        dakar.setGenre(MusicGenre.AFROBEATS);
        dakar.setStripePriceId("price_1OxvCpIOjZy9IHl8jxR9sjwC");
        dakar.setDuration(196);
        dakar.setAvailable(true);
        productionRepository.save(dakar);

        //Beat - Isla Rhythm
        ProductionEntity latin = new ProductionEntity();
        latin.setId(4L);
        latin.setTitle("Isla Rhythm");
        latin.setBpm(136);
        latin.setAudioMp3("https://hybridvision.s3.amazonaws.com/e4e898f5-ddc5-4502-8235-413c8b527939.mp3");
        latin.setCoverImage("https://hybridvision.s3.amazonaws.com/cb8b2cf1-ff66-41e5-a5a7-14abe27af9ad.png");
        latin.setReleaseDate(LocalDate.now());
        latin.setGenre(MusicGenre.LATIN);
        latin.setStripePriceId("price_1OwhRNIOjZy9IHl84IPa0q7N");
        latin.setDuration(67);
        latin.setAvailable(true);
        productionRepository.save(latin);

        //Beat - Lies
        ProductionEntity lies = new ProductionEntity();
        lies.setId(5L);
        lies.setTitle("Lies");
        lies.setBpm(96);
        lies.setAudioMp3("https://hybridvision.s3.amazonaws.com/88869491-4198-4d40-b42c-37dfd0f4f558.mp3");
        lies.setCoverImage("https://hybridvision.s3.amazonaws.com/2ea0f1f3-31dd-47d5-a497-4f6d6676a631.png");
        lies.setReleaseDate(LocalDate.now());
        lies.setGenre(MusicGenre.RNB);
        lies.setStripePriceId("price_1OwhcTIOjZy9IHl8q5nVYdDf");
        lies.setDuration(136);
        lies.setAvailable(true);
        productionRepository.save(lies);

        //Beat - Toxic
        ProductionEntity toxic = new ProductionEntity();
        toxic.setId(6L);
        toxic.setTitle("Toxic");
        toxic.setBpm(100);
        toxic.setAudioMp3("https://hybridvision.s3.amazonaws.com/7196390b-cb11-42aa-8b50-1ed6bbde656c.mp3");
        toxic.setCoverImage("https://hybridvision.s3.amazonaws.com/d96e1a04-7d71-4b70-8479-26c5e6fc107f.png");
        toxic.setReleaseDate(LocalDate.now());
        toxic.setGenre(MusicGenre.CLUB);
        toxic.setDuration(146);
        toxic.setAvailable(true);
        productionRepository.save(toxic);


        //Beat - Chill
        ProductionEntity chill = new ProductionEntity();
        chill.setId(7L);
        chill.setTitle("Chill Trap");
        chill.setBpm(150);
        chill.setAudioMp3("https://hybridvision.s3.amazonaws.com/2d8ac8f0-8f2a-4a29-9d99-85662aaedbec.wav");
        chill.setCoverImage("https://hybridvision.s3.amazonaws.com/7a7155d8-4aa3-4ad3-9e8f-c4e6bfe7cf3e.png");
        chill.setReleaseDate(LocalDate.now());
        chill.setGenre(MusicGenre.EDM);
        chill.setStripePriceId("price_1OwhfIIOjZy9IHl87H7o7qU3");
        chill.setDuration(89);
        chill.setAvailable(true);
        productionRepository.save(chill);

        //Beat - Ruthless
        ProductionEntity drill = new ProductionEntity();
        drill.setId(8L);
        drill.setTitle("Ruthless");
        drill.setBpm(150);
        drill.setAudioMp3("https://hybridvision.s3.amazonaws.com/fb6d4279-963c-4d20-a24c-ebdfd4ad9aae.mp3");
        drill.setCoverImage("https://hybridvision.s3.amazonaws.com/4cd095a2-a554-475d-b250-ce161e1bd0ba.png");
        drill.setReleaseDate(LocalDate.now());
        drill.setGenre(MusicGenre.DRILL);
        drill.setStripePriceId("price_1OwhfrIOjZy9IHl8LBlq5gRr");
        drill.setDuration(29);
        drill.setAvailable(true);
        productionRepository.save(drill);

        //Beat - Dancehall
        ProductionEntity dancehall = new ProductionEntity();
        dancehall.setId(9L);
        dancehall.setTitle("Safari");
        dancehall.setBpm(100);
        dancehall.setAudioMp3("https://hybridvision.s3.amazonaws.com/dc92286a-27a1-41ac-8e9f-e6009b731c56.wav");
        dancehall.setCoverImage("https://hybridvision.s3.amazonaws.com/27ddc8cc-63a4-4da4-8666-3e26fe95ef36.png");
        dancehall.setReleaseDate(LocalDate.now());
        dancehall.setGenre(MusicGenre.AFROBEATS);
        dancehall.setStripePriceId("price_1OwhgTIOjZy9IHl851Hyr0cb");
        dancehall.setDuration(45);
        dancehall.setAvailable(true);
        productionRepository.save(dancehall);

        //Beat - Afrobeat
        ProductionEntity guidance = new ProductionEntity();
        guidance.setId(10L);
        guidance.setTitle("No Guidance");
        guidance.setBpm(99);
        guidance.setAudioMp3("https://hybridvision.s3.amazonaws.com/a93a465e-6b6b-48f4-b9d6-76c41f88bb8c.mp3");
        guidance.setCoverImage("https://hybridvision.s3.amazonaws.com/f218f578-1273-4602-b55e-890041d9615b.png");
        guidance.setReleaseDate(LocalDate.now());
        guidance.setGenre(MusicGenre.AFROBEATS);
        guidance.setStripePriceId("price_1OwhiUIOjZy9IHl8BUBTprIO");
        guidance.setDuration(175);
        guidance.setAvailable(true);
        productionRepository.save(guidance);

        //Beat - Lean on me
        ProductionEntity leanOnMe = new ProductionEntity();
        leanOnMe.setId(11L);
        leanOnMe.setTitle("Lean On Me");
        leanOnMe.setBpm(101);
        leanOnMe.setAudioMp3("https://hybridvision.s3.amazonaws.com/d9156966-4091-4f47-88b9-f1d2e927e387.mp3");
        leanOnMe.setCoverImage("https://hybridvision.s3.amazonaws.com/10c84393-6423-44aa-99cf-d91c870ee067.png");
        leanOnMe.setReleaseDate(LocalDate.now());
        leanOnMe.setGenre(MusicGenre.AFROBEATS);
        leanOnMe.setStripePriceId("price_1OwhiyIOjZy9IHl8t0s2wSnt");
        leanOnMe.setDuration(172);
        leanOnMe.setAvailable(true);
        productionRepository.save(leanOnMe);

        //Beat - Flashback
        ProductionEntity flashback = new ProductionEntity();
        flashback.setId(12L);
        flashback.setTitle("Flashback");
        flashback.setBpm(100);
        flashback.setAudioMp3("https://hybridvision.s3.amazonaws.com/0582abf3-6894-432c-b31e-18e34640c6e7.mp3");
        flashback.setCoverImage("https://hybridvision.s3.amazonaws.com/118d8eb7-73ad-430f-b9a2-e75543db5c93.png");
        flashback.setReleaseDate(LocalDate.now());
        flashback.setGenre(MusicGenre.AFROBEATS);
        flashback.setStripePriceId("price_1OwhjLIOjZy9IHl80DtGYII6");
        flashback.setDuration(162);
        flashback.setAvailable(true);
        productionRepository.save(flashback);

        // Sample Pack 1
        SamplePackEntity pack1 = new SamplePackEntity();
        pack1.setId(1L);
        pack1.setTitle("HV - Essentials Vol.1");
        pack1.setDescription("With Hybridvision\'s Essential Sample Pack series, the possibilities are ENDLESS! Having Essentials in your sound arsenal will step up your production quality, and will give you the inspiration you need to start & FINISH tracks!");
        pack1.setPrice(24.95);
        pack1.setCoverImageUrl("https://hybridvision.s3.amazonaws.com/b4ede81e-5fcb-40d4-8723-677d7ff5ec9b.jpg");
        pack1.setAudioUrl("https://hybridvision.s3.amazonaws.com/09060024-ca61-4466-8586-8ccc99ecdfce.mp3");
        pack1.setStripePriceId("price_1OwhKyIOjZy9IHl87Os9JcdA");
        samplePackRepository.save(pack1);

        // Sample Pack 2
        SamplePackEntity pack2 = new SamplePackEntity();
        pack2.setId(2L);
        pack2.setTitle("HV - Essentials Vol.2");
        pack2.setDescription("With Hybridvision\'s Essential Sample Pack series, the possibilities are ENDLESS! Having Essentials in your sound arsenal will step up your production quality, and will give you the inspiration you need to start & FINISH tracks!");
        pack2.setPrice(24.95);
        pack2.setCoverImageUrl("https://hybridvision.s3.amazonaws.com/8e978c32-2da5-47bf-aa91-56469b164ef6.jpg");
        pack2.setAudioUrl("https://hybridvision.s3.amazonaws.com/e9156116-40a0-4f86-9367-2dbc5cc41c68.mp3");
        pack2.setStripePriceId("price_1OwhLVIOjZy9IHl8kqPWUu5S");
        samplePackRepository.save(pack2);

        // Sample Pack 3
        SamplePackEntity pack3 = new SamplePackEntity();
        pack3.setId(3L);
        pack3.setTitle("HV - Essentials Vol.3");
        pack3.setDescription("With Hybridvision\'s Essential Sample Pack series, the possibilities are ENDLESS! Having Essentials in your sound arsenal will step up your production quality, and will give you the inspiration you need to start & FINISH tracks!");
        pack3.setPrice(24.95);
        pack3.setCoverImageUrl("https://hybridvision.s3.amazonaws.com/c9bee787-e423-48c0-8c8e-90035d567cc0.png");
        pack3.setAudioUrl("https://hybridvision.s3.amazonaws.com/fc01ca6d-dc51-46ff-a016-27aaaef274b5.mp3");
        pack3.setStripePriceId("price_1OwhLyIOjZy9IHl8lUQrHVON");
        samplePackRepository.save(pack3);

        // Sample Pack 4
        SamplePackEntity pack4 = new SamplePackEntity();
        pack4.setId(4L);
        pack4.setTitle("HV - Essentials Vol.4");
        pack4.setDescription("With Hybridvision\'s Essential Sample Pack series, the possibilities are ENDLESS! Having Essentials in your sound arsenal will step up your production quality, and will give you the inspiration you need to start & FINISH tracks!");
        pack4.setPrice(24.95);
        pack4.setCoverImageUrl("https://hybridvision.s3.amazonaws.com/5d81edc2-1764-4f10-9e93-9d796f4ed281.jpg");
        pack4.setAudioUrl("https://hybridvision.s3.amazonaws.com/6d068ab5-3370-4338-9cfa-b53129b3100a.mp3");
        pack4.setStripePriceId("price_1OwhMSIOjZy9IHl84VyJnhRa");
        samplePackRepository.save(pack4);




    }
}
