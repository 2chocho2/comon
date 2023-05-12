package comon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class OAuthController {

    /**
     * 카카오 callback
     */
    @ResponseBody
    @GetMapping("/kakaoLogin")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println("토큰값");
        System.out.println(code);
    }
}
