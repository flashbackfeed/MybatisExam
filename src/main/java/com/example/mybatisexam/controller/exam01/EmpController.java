package com.example.mybatisexam.controller.exam01;

import com.example.mybatisexam.model.common.PageReq;
import com.example.mybatisexam.model.common.PageRes;
import com.example.mybatisexam.model.vo.Dept;
import com.example.mybatisexam.model.vo.Emp;
import com.example.mybatisexam.service.exam01.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

/**
 * packageName : com.example.mybatisexam.controller.exam01
 * fileName : MemberController
 * author : GGG
 * date : 2023-10-12
 * description : 사원 컨트롤러 (@Controller : jsp용)
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-10-12         GGG          최초 생성
 */
@Controller
@Slf4j
@RequestMapping("/exam01")
public class EmpController {
    @Autowired
    EmpService empService; // 1개 가져오기 (DI)

    //  TODO : @RequestParam = url? 변수=값&변수2=값2 (쿼리스트링 방식)
//          page : 현재페이지번호, size : 1 페이지당 개수
    /** 전체 조회 : ename like 기능 (+) */
    @GetMapping("/emp")
    public String getDeptAll(@RequestParam(defaultValue = "") String ename,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "3") int size,
                             Model model){
//     TODO : 페이징 요청 객체에 정보 저장
        PageReq pageReq = new PageReq(page,size);

//     TODO : 전체 조회 함수 호출
        PageRes<Emp> pageRes = empService.findByEnameContaining(ename ,pageReq);
//     TODO : jsp 정보 전달( 부서 배열, 페이징 정보 )
        model.addAttribute("emp",pageRes.getContent()); // 부서배열
        model.addAttribute("currentPage",pageRes.getNumber()); // 현재페이지번호
        model.addAttribute("totalItems",pageRes.getTotalElements()); // 전체테이블 건수
        model.addAttribute("totalPages",pageRes.getTotalPages()); // 전체 페이지 개수
        model.addAttribute("startPage",pageRes.getStartPage()); // 현재 시작 페이지 번호
        model.addAttribute("endPage",pageRes.getEndPage()); // 현재 끝 페이지 번호

        log.debug(model.toString()); // 로그 출력

        return "exam01/emp/emp_all.jsp";
    }

    /** 상세 조회 */
    @GetMapping("/emp/{eno}")
    public String getEmpId(@PathVariable int eno,
                           Model model){
        Optional<Emp> optionalEmp = empService.findById(eno);

        model.addAttribute("emp",optionalEmp.get());
        return "exam01/emp/emp_id.jsp";
    }

    /** 저장 함수 : 저장 페이지로 이동 */
    @GetMapping("/emp/addition")
    public String addEmp(){

        return "exam01/emp/add_emp.jsp";
    }


    /** 저장 함수 : db 저장 */
    @PostMapping("/emp/add")
    public RedirectView createEmp(@ModelAttribute Emp emp){

        empService.save(emp); // DB 저장
//      전체 조회페이지로 강제 이동
        return new RedirectView("/exam01/emp");
    }

    /** 수정 함수 : 수정 페이지로 이동 + 상세 정보 */
    @GetMapping("/emp/edition/{eno}")
    public String editEmp(@PathVariable int eno, Model model){
        Optional<Emp> optionalEmp = empService.findById(eno);
        model.addAttribute("emp",optionalEmp.get());
        return "exam01/emp/update_emp.jsp";
    }

    /** 수정 함수 : db 수정 저장 */
    @PutMapping("/emp/edit/{eno}")
    public RedirectView updateEmp(@PathVariable int eno, @ModelAttribute Emp emp){
        empService.save(emp);
        return new RedirectView("/exam01/emp");
    }

    /** 삭제 함수 */
    @DeleteMapping("/emp/delete/{eno}")
    public RedirectView deleteEmp(@PathVariable int eno){
        empService.removeById(eno);
        return new RedirectView("/exam01/emp");
    }



}
