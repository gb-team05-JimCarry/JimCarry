package com.app.jimcarry.controller;

import com.app.jimcarry.domain.vo.UserVO;
import com.app.jimcarry.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users/*")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserService userService;

    @PostMapping("identifications-duplicate")
    public boolean checkIdentificationDuplicate(String userIdentification) {
        return userService.checkIdentificationDuplicate(userIdentification);
    }

    @PostMapping("emails-duplicate")
    public boolean checkEmailDuplicate(String userEmail) {
        return userService.checkEmailDuplicate(userEmail);
    }

    @PostMapping("userPhones-duplicate")
    public boolean checkPhoneDuplicate(String userPhone) {
        String userIdentification = null, userName = null;

        if(userService.findIdByPhone(userIdentification, userName, userPhone) == null) {
            return true;
        }
        return false;
    }

    @GetMapping("send-sms")
    public String sendSMS(String userPhone) throws CoolsmsException {
        return userService.sendRandomNumber(userPhone);
    }

    @PostMapping("login-naver")
    public boolean loginNaver(String userIdentification, String userEmail, HttpSession session) {
        UserVO userVO = userService.findByIdentification(userIdentification, userEmail);

        if(userVO == null || userVO.getUserStatus() == 0 || userVO.getUserStatus() == 1) {
            return false;
        }

        session.setAttribute("user", userVO);
        return true;
    }

    @PostMapping("check-kakao-login")
    public boolean checkKakaoLogin(HttpSession session) {
        if(((UserVO)session.getAttribute("user")).getUserStatus() != 1) {
            return false;
        }
        return true;
    }
}
