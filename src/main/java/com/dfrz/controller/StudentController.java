package com.dfrz.controller;

import com.dfrz.bean.Student;
import com.dfrz.mapper.StudentMapper;
import com.dfrz.service.StudentSerivce;
import com.dfrz.utils.POIUtils;
import com.dfrz.utils.Result;
import com.dfrz.utils.ResultUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentSerivce studentSerivce;

    @RequestMapping("/toExportUser")
    public ModelAndView toExportUser() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("student_export");
        return mv;
    }

    @RequestMapping("/toStudentList")
    public ModelAndView toStudentList() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("student_list");
        return mv;
    }

    @RequestMapping("/exportStudent")
    public Result exportStudent(MultipartFile file, String destdir) {
        logger.info("execute uploadfile");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path=" + path);
            logger.info("filename=" + file.getOriginalFilename());
            File projectPath = new File(path);
            File upload = new File(projectPath.getAbsolutePath(), "static/" + destdir);
            if (!upload.exists()) {
                upload.mkdirs();
            }
            //构建在服务端上传目录的文件
            File dest = new File(upload.getAbsolutePath() + File.separator + file.getOriginalFilename());
            //文件IO读写
//            file.transferTo(dest);   错的
//            .... 副本。。。从输入流到输出流， 覆盖资源
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(dest));
            //POI进行解析
            logger.info("execute POI parse");
            // Workbook workbook = POIUtils.getWorkbookAuto(file);
            //获取根目录
            File spath = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!spath.exists()) spath = new File("");
            System.out.println("path:" + spath.getAbsolutePath());

            //如果上传目录为/static/images/upload/，则可以如下获取：
            File supload = new File(spath.getAbsolutePath(), "static/upload/user/export/" + file.getOriginalFilename());
           
//            is = new FileInputStream(supload);
            Workbook workbook = WorkbookFactory.create(new FileInputStream(supload));
            Sheet sheet = workbook.getSheetAt(0);//默认只有一个sheet
            int rows = sheet.getPhysicalNumberOfRows();//获得sheet有多少行
            List<Student> list = new ArrayList<>();
            //读第一个sheet
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                String realname = "";
                String school = "";
                String mobile = "";
                String email = "";
                int age = 0;
                String gender = "";
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {

                        switch (cell.getCellType()) {
                            case STRING:
                                if (j == 1) {
                                    realname = cell.getRichStringCellValue().getString();
                                }
                                if (j == 2) {
                                    school = cell.getRichStringCellValue().getString();
                                }
                                if (j == 4) {
                                    email = cell.getRichStringCellValue().getString();
                                }
                                if (j == 6) {
                                    gender = cell.getRichStringCellValue().getString();
                                }
                                System.out.println(cell.getRichStringCellValue().getString());
                                break;
                            case NUMERIC:
                                //给电话号码转型
                                DecimalFormat df = new DecimalFormat("#");
                                System.out.println(String.valueOf(df.format(cell.getNumericCellValue())));

                                if (j == 3) {
                                    mobile = String.valueOf(df.format(cell.getNumericCellValue()));
                                }
                                if (j == 5) {
                                    age = Integer.parseInt(String.valueOf(df.format(cell.getNumericCellValue())));
                                }
                                break;
                            case BOOLEAN:
                                System.out.println(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                System.out.println(cell.getCellFormula());
                                break;
                            case BLANK:
                                System.out.println();
                                break;
                            default:
                                System.out.println();
                        }
                    }
                }
                Student student = new Student(realname, school, mobile, email, age, gender);
                //将每一行都封装成一个对象，存到list里面进行批量导入
                list.add(student);
            }
            //批量添加到用户表，学生表
            studentSerivce.insertStudentBatch(list);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放资源");
//            is.close();
        }
        Map<String, String> map = new HashMap<>();
        map.put("src", file.getOriginalFilename());
        result.setCode(0);
        result.setData(map);
        return result;
    }
}
