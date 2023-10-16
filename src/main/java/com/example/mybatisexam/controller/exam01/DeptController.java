package com.example.mybatisexam.controller.exam01;

import com.example.mybatisexam.model.common.PageReq;
import com.example.mybatisexam.model.common.PageRes;
import com.example.mybatisexam.model.vo.Dept;
import com.example.mybatisexam.service.exam01.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;


/**
 * packageName : com.example.mybatisexam.controller.exam01
 * fileName : DeptController
 * author : GGG
 * date : 2023-10-12
 * description : 부서 컨트롤러 : jsp 연동 : @Controller
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
public class DeptController {
    @Autowired
    DeptService deptService; // 서비스 객체 가져오기

//  TODO : @RequestParam = url? 변수=값&변수2=값2 (쿼리스트링 방식)
    /** 전체 조회 : like 기능 (+) */
    @GetMapping("/dept")
    public String getDeptAll(@RequestParam(defaultValue = "") String dname,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "3") int size,
                             Model model){
//     TODO : 페이징 요청 객체에 정보 저장
//        page : 현재 페이지 번호, size : 1 페이지당 개수
        PageReq pageReq = new PageReq(page,size);

//     TODO : 전체 조회 함수 호출
        PageRes<Dept> pageRes = deptService.findByDnameContaining(dname,pageReq);
//     TODO : jsp 정보 전달( 부서 배열, 페이징 정보 )
        model.addAttribute("dept",pageRes.getContent()); // 부서배열
        model.addAttribute("currentPage",pageRes.getNumber()); // 현재페이지번호
        model.addAttribute("totalItems",pageRes.getTotalElements()); // 전체테이블 건수
        model.addAttribute("totalPages",pageRes.getTotalPages()); // 전체 페이지 개수
        model.addAttribute("startPage",pageRes.getStartPage()); // 현재 시작 페이지 번호
        model.addAttribute("endPage",pageRes.getEndPage()); // 현재 끝 페이지 번호

        log.debug(model.toString()); // 로그 출력

        return "exam01/dept/dept_all.jsp";
    }


    /** 상세 조회 */
    @GetMapping("/dept/{dno}")
    public String getDeptById(@PathVariable int dno,
                              Model model){
//      서비스 상세 조회 함수 호출
        Optional<Dept> optionalDept = deptService.findById(dno);
        model.addAttribute("dept",optionalDept.get());
        return "exam01/dept/dept_id.jsp";
    }

    /** 저장 함수 : 저장 페이지로 이동 */
    @GetMapping("/dept/addition")
    public String addDept(){
        return "exam01/dept/add_dept.jsp";
    }
    
    /** 저장 함수 : db 저장 */
    @PostMapping("/dept/add")
    public RedirectView createDept(@ModelAttribute Dept dept){
        
        deptService.save(dept); // DB 저장
//      전체 조회페이지로 강제 이동
        return new RedirectView("/exam01/dept");
    }

    /** 수정 함수 : 수정 페이지로 이동 + 상세조회*/
    @GetMapping("/dept/edition/{dno}")
    public String editDept(@PathVariable int dno,Model model){
//      서비스 상세 조회 함수 호출
        Optional<Dept> optionalDept = deptService.findById(dno);
        model.addAttribute("dept",optionalDept.get());
        return "exam01/dept/update_dept.jsp";
    }
    /** 수정 함수 : db 수정 저장*/
    @PutMapping("/dept/edit/{dno}")
    public RedirectView updateDept(@PathVariable int dno,@ModelAttribute Dept dept){
        deptService.save(dept); // db 수정 저장
//      전체 조회 페이지로 강제 이동
        return new RedirectView("/exam01/dept");
    }
    
   /** 삭제함수 */
   @DeleteMapping("/dept/delete/{dno}")
    public RedirectView deleteDept(@PathVariable int dno){
       deptService.removeById(dno); // db 삭제

       return new RedirectView("/exam01/dept");
   }
   
// todo : 연습 5) 부서 클래스를 참고하여 사원 삭제기능을 추가하세요
//    empDao, emp.xml, EmpService, EmpController, update_emp.jsp 수정
//    url : /dept/delete/{eno}
//    redirect jsp : /exam01/emp

}
