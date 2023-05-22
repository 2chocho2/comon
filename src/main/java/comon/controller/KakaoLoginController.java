package comon.controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.sql.DataSource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comon.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KakaoLoginController {

    private final DataSource dataSource;

    public KakaoLoginController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/loginFromSocial")
    public String login(UserDto userDto) {
       return "";
    }
    
    @PostMapping("/checkUserExistence")
    public CompletableFuture<ResponseEntity<?>> checkUserExistence(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        
        return CompletableFuture.supplyAsync(() -> checkIfUserExists(userName))
                .thenApply(exists -> ResponseEntity.ok(Map.of("exists", exists)));
    }

    private boolean checkIfUserExists(String userName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM t_user WHERE user_name = ?");
        ) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            log.error("Error checking user existence", e);
            return false;
        }
    }
}