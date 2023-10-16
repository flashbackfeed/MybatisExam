package com.example.mybatisexam.dao;

import com.example.mybatisexam.model.common.PageReq;
import com.example.mybatisexam.model.vo.Dept;
import com.example.mybatisexam.model.vo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.mybatisexam.dao
 * fileName : MemberDao
 * author : GGG
 * date : 2023-10-12
 * description : 사원 Dao ( Mybatis mapper : @Mapper 사용)
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-10-12         GGG          최초 생성
 */
@Mapper
public interface EmpDao {
    public List<Emp> findByEnameContaining(@Param("ename") String ename,
                                         PageReq pageReq);
    long countByEname(String ename);

    Optional<Emp> findById(int eno);

    int insert(Emp emp);

    int update(Emp emp);

    int deleteById(int eno);

    long existById(int eno);
    
/** todo : dynamic sql 조회 */
    public List<Emp> findByDynamicContaining(@Param("ename") String ename,
                                              @Param("job") String job,
                                              @Param("manager") Integer manager,
                                              @Param("hiredate") String hiredate,
                                              @Param("salary") Integer salary,
                                              @Param("commission") Integer commission,
                                              @Param("dno") Integer dno,
                                              PageReq pageReq);

/**  todo : dynamic sql count 조회 */
    long countByDynamic(String ename,
                        @Param("job") String job
                       ,@Param("manager") Integer manager
                       ,@Param("hiredate") String hiredate
                       ,@Param("salary") Integer salary
                       ,@Param("commission") Integer commission
                       , @Param("dno") Integer dno);
}

