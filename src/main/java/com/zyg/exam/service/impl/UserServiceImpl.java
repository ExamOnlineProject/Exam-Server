package com.zyg.exam.service.impl;

import com.zyg.exam.common.DTO.ImportUser;
import com.zyg.exam.common.DTO.StudentDTO;
import com.zyg.exam.common.JsonBean;
import com.zyg.exam.common.ResVO;
import com.zyg.exam.common.VO.QuestionVO;
import com.zyg.exam.dao.UserDao;
import com.zyg.exam.exception.MyException;
import com.zyg.exam.model.User;
import com.zyg.exam.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZZQ
 * @since 2020-04-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public ResVO selectStudent(StudentDTO studentDTO) {
        List<Object> students = userDao.selectStudent(studentDTO).get(0);
        long total  = (long)userDao.selectStudent(studentDTO).get(1).get(0);
        return new ResVO(students,total);
    }

    @Override
    public JsonBean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<ImportUser> importUsers = new ArrayList();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException(500,"上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        ImportUser importUser;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            importUser=new ImportUser();
//            if( row.getCell(0).getCellType() !=0){
//                throw new MyException(500,"导入失败(第"+(r+1)+"行,题目类型请设为文本格式)");
//            }
            row.getCell(0).setCellType(CellType.STRING);
            String username= row.getCell(0).getStringCellValue();

            if(username == null || username.isEmpty()){
                throw new MyException(500,"导入失败(第"+(r+1)+"行,用户名未填写)");
            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String password = row.getCell(1).getStringCellValue();
            if(password==null || password.isEmpty()){
                throw new MyException(500,"导入失败(第"+(r+1)+"行,密码未填写)");
            }
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String name=row.getCell(2).getStringCellValue();
            if(name==null || name.isEmpty()){
                throw new MyException(500,"导入失败(第"+(r+1)+"行,姓名未填写)");
            }
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            String tel=row.getCell(3).getStringCellValue();
            if(tel==null || tel.isEmpty()){
                throw new MyException(500,"导入失败(第"+(r+1)+"行,电话未填写)");
            }
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String role0=row.getCell(4).getStringCellValue();
            String role="";
            if(role0==null || role0.isEmpty()){
                throw new MyException(500,"导入失败(第"+(r+1)+"行,角色未填写)");
            }
            if(role0.equals("教师")){
                role="2";
            }else{
                role="3";
            }
            if(role=="3"){
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                String classname=row.getCell(5).getStringCellValue();
                if(classname==null || classname.isEmpty()){
                    throw new MyException(500,"导入失败(第"+(r+1)+"行,班级未填写)");
                }
                importUser.setClassname(classname);
            }
            importUser.setUsername(username);
            importUser.setPassword(password);
            importUser.setName(name);
            importUser.setRole(role);
            importUser.setTel(tel);
            System.out.println(importUser);
            importUsers.add(importUser);

        }
        int num=0;

        for (ImportUser importUser1 : importUsers) {

            try {
                num=userDao.importUsers(importUser1);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        JsonBean jsonBean=new JsonBean();
        if (num>0){
            jsonBean= new JsonBean(200,"导入成功","");
        }
        return jsonBean;
    }

}
