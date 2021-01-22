package edu.heuet.shaohua.controller;

import com.alibaba.druid.util.StringUtils;
import edu.heuet.shaohua.dao.StudentDOMapper;
import edu.heuet.shaohua.dataobject.StudentDO;
import edu.heuet.shaohua.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("student")
@RequestMapping("/student")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class StudentController extends BaseController {

    @Autowired
    StudentDOMapper studentDOMapper;

    @PostMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody StudentDO studentDO) {
        int result = studentDOMapper.insertSelective(studentDO);
        return CommonReturnType.create(result);
    }

    @PostMapping("/insert/batch")
    @ResponseBody
    public CommonReturnType insert(@RequestBody List<StudentDO> students) {
        Long result = -1L;
        try {
            for (StudentDO studentDO : students) {
                result++;
                studentDOMapper.insertSelective(studentDO);
            }
        } catch (Exception e) {
            return CommonReturnType.create(-1L);
        }

        return CommonReturnType.create(result + 1);
    }

    @PostMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam(name = "studentNo") String studentNo) {
        int result = studentDOMapper.deleteByPrimaryKey(studentNo);
        return CommonReturnType.create(result);
    }

    @PostMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody StudentDO studentDO) {
        int result = studentDOMapper.updateByPrimaryKeySelective(studentDO);
        return CommonReturnType.create(result);
    }

    @GetMapping("/query")
    @ResponseBody
    public CommonReturnType query(
            @RequestParam(name = "studentNo", required = false) String studentNo,
            @RequestParam(name = "name", required = false) String name) {
        if (!StringUtils.isEmpty(studentNo)) {
            StudentDO studentDO1 = studentDOMapper.selectByPrimaryKey(studentNo);

            if (studentDO1 != null) {
                return CommonReturnType.create(studentDO1.getName());
            } else {
                return CommonReturnType.create("");
            }
        } else if (!StringUtils.isEmpty(name)) {
            StudentDO studentDO2 = studentDOMapper.selectByName(name);

            if (studentDO2 != null) {
                return CommonReturnType.create(studentDO2.getStudentNo());
            } else {
                return CommonReturnType.create("");
            }
        } else {
            List<StudentDO> studentDOS = studentDOMapper.getAll();
            StringBuilder studentBuilder = new StringBuilder("");
            for (StudentDO student : studentDOS) {
                studentBuilder.append("学号：").append(student.getStudentNo()).append("\n\n");
                studentBuilder.append("姓名：").append(student.getName()).append("\n\n");
                studentBuilder.append("----------------\n\n");
            }
            return CommonReturnType.create(studentBuilder.toString());
        }
    }

}
