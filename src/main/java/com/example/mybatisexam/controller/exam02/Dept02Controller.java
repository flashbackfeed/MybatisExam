package com.example.mybatisexam.controller.exam02;

import com.example.mybatisexam.model.common.PageReq;
import com.example.mybatisexam.model.common.PageRes;
import com.example.mybatisexam.model.vo.Dept;
import com.example.mybatisexam.service.exam01.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.mybatisexam.controller.exam02
 * fileName : Dept02Controller
 * author : GGG
 * date : 2023-10-13
 * description : 부서 컨트롤러 : @RestController ( react 용 )
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-10-13         GGG          최초 생성
 */
@RestController
@Slf4j
@RequestMapping("/exam02")
public class Dept02Controller {
    @Autowired
    DeptService deptService; // todo : di

    /**
     * 전체조회 : 부서명 like 검색
     */
    @GetMapping("/dept")
    public ResponseEntity<Object> getDeptAll(@RequestParam(defaultValue = "") String dname,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size
    ) {

        try {
            PageReq pageReq = new PageReq(page, size);

//     TODO : 전체 조회 함수 호출
            PageRes<Dept> pageRes = deptService.findByDnameContaining(dname, pageReq);

//     TODO : 정보 전달( 부서 배열, 페이징 정보 )
//         자료구조 (키, 값) : Map
            Map<String, Object> response = new HashMap<>();

            response.put("dept", pageRes.getContent()); // 부서배열
            response.put("currentPage", pageRes.getNumber()); // 현재페이지번호
            response.put("totalItems", pageRes.getTotalElements()); // 전체테이블 건수
            response.put("totalPages", pageRes.getTotalPages()); // 전체 페이지 개수

//              todo : 조회 성공
            if (pageRes.isEmpty() == false) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//              todo : 0건 조회
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     * 상세 조회
     */
    @GetMapping("/dept/{dno}")
    public ResponseEntity<Object> getDeptId(@PathVariable int dno) {
        try {
//                todo: 상세조회 함수 호출
            Optional<Dept> optionalDept
                    = deptService.findById(dno);

            if (optionalDept.isEmpty() == false) {
//                todo: 조회 성공
                return new ResponseEntity<>(optionalDept.get(), HttpStatus.OK);
            } else {
//                todo: 0건 조회 (실패)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 저장함수
     */
    @PostMapping("/dept")
    public ResponseEntity<Object> createDept(@RequestBody Dept dept) {
        try {
            int count = deptService.save(dept);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 수정함수
     */

    @PutMapping("/dept/{dno}")
    public ResponseEntity<Object> updateDept(@PathVariable int dno,
                                             @RequestBody Dept dept) {
        try {
            // todo : 수정함수(기본키가 있으면)
            int count = deptService.save(dept);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 삭제함수
     */
    @DeleteMapping("/dept/deletion/{dno}")
    public ResponseEntity<Object> deleteDept(@PathVariable int dno) {
        try {
//                todo: 삭제 함수 호출
            boolean bSuccess = deptService.removeById(dno);

            if (bSuccess == true) {
//                todo: 삭제 성공
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
//                todo: 0건 삭제 (실패)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * todo : dynamic sql
     */
    /** todo: dynamic sql */
    @GetMapping("/dept/dynamic")
    public ResponseEntity<Object> getDeptDynamic(
            @RequestParam(defaultValue = "") String dname,
            @RequestParam(defaultValue = "") String loc,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        try {
            PageReq pageReq = new PageReq(page, size);

//      todo: dynamic 조회 함수 호출
            PageRes<Dept> pageRes
                    = deptService.findByDynamicContaining(dname, loc, pageReq);

//      todo: 정보전달( 부서배열, 페이징정보 )
//        자료구조 (키, 값) : Map
            Map<String, Object> response = new HashMap<>();

            response.put("dept", pageRes.getContent()); // 부서배열
            response.put("currentPage", pageRes.getNumber()); // 현재 페이지 번호
            response.put("totalItems", pageRes.getTotalElements()); // 전체 테이블 건수
            response.put("totalPages", pageRes.getTotalPages()); // 전체 페이지 개수

            if(pageRes.isEmpty() == false) {
//                todo: 조회 성공
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//                todo: 0건 조회 (실패)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


