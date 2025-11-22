package com.basket.api.config;

import com.basket.api.domain.entity.*;
import com.basket.api.domain.repository.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@Profile("dev") // Só roda se o perfil for 'dev' (para não limpar seu banco de produção)
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final LeagueRepository leagueRepository;
    private final TeamCategoryRepository teamCategoryRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final LeagueTeamRepository leagueTeamRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker(new Locale("pt-BR"));

    // Construtor com injeção de todas as dependências necessárias
    public DataSeeder(UserRepository userRepository, CategoryRepository categoryRepository,
                      TeamRepository teamRepository, PlayerRepository playerRepository,
                      LeagueRepository leagueRepository, TeamCategoryRepository teamCategoryRepository,
                      TeamPlayerRepository teamPlayerRepository, LeagueTeamRepository leagueTeamRepository,
                      GameRepository gameRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.leagueRepository = leagueRepository;
        this.teamCategoryRepository = teamCategoryRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.leagueTeamRepository = leagueTeamRepository;
        this.gameRepository = gameRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Evita duplicar dados se já existirem
        if (userRepository.count() > 0) {
            System.out.println("Banco de dados já populado. Pulando DataSeeder.");
            return;
        }

        System.out.println("Iniciando seed do banco de dados...");

        // 1. CRIAR USUÁRIO ADMIN
        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@basket.com");
        admin.setPassword(passwordEncoder.encode("123456")); // Senha padrão para testes
        userRepository.save(admin);

        // 2. CRIAR CATEGORIA (Ex: Adulto Masculino)
        Category categoria = new Category();
        categoria.setName("Adulto Masculino");
        categoria.setCategoryGender(CategoryGender.MALE);
        categoria.setDescription("Liga principal");
        categoryRepository.save(categoria);

        // 3. CRIAR LIGA
        League liga = new League();
        liga.setName("Copa Estadual 2024");
        liga.setCategory(categoria);
        liga.setUser(admin);
        liga.setStartDate(java.sql.Date.valueOf(LocalDate.now()));
        liga.setStatus(LeagueStatus.ACTIVE);
        leagueRepository.save(liga);

        // 4. CRIAR TIMES E JOGADORES
        List<Team> times = new ArrayList<>();
        for (int i = 0; i < 4; i++) { // Criar 4 times
            Team time = new Team();
            time.setName(faker.team().name() + " Basket");
            time.setLocation(faker.address().city());
            time.setLogoUrl("https://placehold.co/100");
            teamRepository.save(time);
            times.add(time);

            // 4.1 Vincular Time à Categoria
            TeamCategory tc = new TeamCategory();
            tc.setTeam(time);
            tc.setCategory(categoria);
            teamCategoryRepository.save(tc);

            // 4.2 Vincular Time à Liga
            LeagueTeam lt = new LeagueTeam();
            lt.setLeague(liga);
            lt.setTeam(time);
            lt.setTeamStatus(TeamStatus.ACTIVE);
            leagueTeamRepository.save(lt);

            // 4.3 Criar 5 Jogadores para o Time
            for (int j = 0; j < 5; j++) {
                Player player = new Player();
                player.setFirstName(faker.name().firstName());
                player.setLastName(faker.name().lastName());
                player.setDocument(faker.number().digits(11)); // CPF Fake
                player.setHeight(1.80f + (float)Math.random() * 0.40f); // Altura aleatória entre 1.80 e 2.20
                player.setJerseyNumber(faker.number().numberBetween(0, 99));
                playerRepository.save(player);

                // Vincular Jogador ao Time na Categoria correta
                TeamPlayer tp = new TeamPlayer();
                tp.setPlayer(player);
                tp.setTeam(time);
                tp.setCategory(categoria);
                tp.setIsActive(true);
                tp.setStartDate(LocalDateTime.now());
                teamPlayerRepository.save(tp);
            }
        }

        // 5. CRIAR UM JOGO AGENDADO (Entre os dois primeiros times)
        Game jogo = new Game();
        jogo.setLeague(liga);
        jogo.setHomeTeam(times.get(0));
        jogo.setAwayTeam(times.get(1));
        jogo.setScheduledDate(LocalDateTime.now().plusHours(2)); // Jogo daqui a 2h
        jogo.setStatus(GameStatus.SCHEDULED);
        jogo.setVenue("Ginásio Principal");
        gameRepository.save(jogo);

        System.out.println("Seed finalizado com sucesso! Usuário: admin@basket.com / 123456");
    }
}