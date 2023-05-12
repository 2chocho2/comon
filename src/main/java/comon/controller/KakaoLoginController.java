package comon.controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comon.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KakaoLoginController {

    @PostMapping("/loginFromSocial")
    public String login(UserDto userDto) {
       return "";
    }
    
    @PostMapping("/checkUserExistence")
    public ResponseEntity<?> checkUserExistence(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        // DB에서 사용자 이름이 존재하는지 여부를 확인
        // 존재하는 경우 exists 변수를 true로 설정
        // 존재하지 않는 경우 exists 변수를 false로 설정
        boolean exists = checkIfUserExists(userName);
        
        // exists 변수를 클라이언트에게 전송
        return ResponseEntity.ok(Map.of("exists", exists));

    }

   
   private boolean checkIfUserExists(String userName) {
       // JDBC를 사용하여 DB에 연결
       String jdbcUrl = "jdbc:mysql://192.168.0.37:3306/comondb";
       String dbUser = "root";
       String dbPassword = "root";
       try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
           // PreparedStatement를 사용하여 쿼리를 실행
           String sql = "SELECT COUNT(*) FROM t_user WHERE user_name = ?";
           
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setString(1, userName);
           ResultSet resultSet = statement.executeQuery();
           resultSet.next();
           int count = resultSet.getInt(1);
           return count > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }
}