package com.app.jimcarry.controller;

import com.app.jimcarry.domain.dto.PageDTO;
import com.app.jimcarry.domain.dto.SearchDTO;
import com.app.jimcarry.domain.vo.Criteria;
import com.app.jimcarry.domain.vo.StorageVO;
import com.app.jimcarry.domain.vo.UserVO;
import com.app.jimcarry.service.ReviewService;
import com.app.jimcarry.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/storages/*")
@RequiredArgsConstructor
@Slf4j
public class StorageController {

    private final StorageService storageService;
    private final ReviewService reviewService;

    @GetMapping("register")
    public String register() { return "/storageRegister/storageRegister"; }


    /*창고등록*/
    @PostMapping("register")
    public RedirectView storageSave(StorageVO storageVO, HttpSession httpSession) {
        log.info("1234");
        storageVO.setUserId(((UserVO)httpSession.getAttribute("user")).getUserId());
        log.info("5678");
        storageService.register(storageVO);

        return new RedirectView("/main/main");
    }

    /*헤더 지역별 창고 조회 */
    @GetMapping("list/{storageAddressNumber}")
    public String showList(@PathVariable("storageAddressNumber") int storageAddressNumber, Model model, Criteria criteria){
        /* 한 페이지에 보여줄 게시글 개수 */
        int amount = 3;
        /* 검색된 결과의 총 개수 */
        int total = 0;

        SearchDTO searchDTO = new SearchDTO().createTypes(new ArrayList<>(Arrays.asList("storageAddressNumber")));
        searchDTO.setStorageAddressNumber(storageAddressNumber);

        //         페이지 번호가 없을 때, 디폴트 1페이지
        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total = storageService.getTotalBy(searchDTO);
        PageDTO pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("storageAddressNumber", storageAddressNumber);
        model.addAttribute("total", total);
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("storage", storageService.getStorageDTOBy(pageDTO));
        log.info(storageService.getStorageDTOBy(pageDTO).toString());

        /*return storageService.getStorageDTOBy(pageDTO);*/
        return "/main/search-page";
    }


}
