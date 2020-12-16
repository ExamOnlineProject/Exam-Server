package com.zyg.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyg.exam.common.DTO.BlackListDTO;
import com.zyg.exam.common.JsonBean;
import com.zyg.exam.common.ResVO;
import com.zyg.exam.model.BlackList;
import com.zyg.exam.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZZQ
 * @since 2020-04-15
 */
/*这是一条测试注释，用于测试git 同步*/
@RestController
public class BlackListController {
    @Autowired
    private BlackListService blackListService;

    @RequestMapping("selectBlackList")
    public ResVO selectBlackList(BlackListDTO blackListDTO){
        Page<BlackList> page = new Page<>(blackListDTO.getPageIndex(),blackListDTO.getPageSize());
        QueryWrapper<BlackList> queryWrapper = null;
        if (StringUtils.isNotBlank(blackListDTO.getType())){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type",blackListDTO.getType());
        }
        IPage<BlackList> list = blackListService.page(page,queryWrapper);
        return new ResVO(list.getRecords(),list.getTotal());
    }
    @RequestMapping("insertBlackList")
    public JsonBean insertBlackList(BlackList blackList){
        return new JsonBean(200,"success",blackListService.save(blackList));
    }
    @RequestMapping("updateBlackList")
    public JsonBean updateBlackList(BlackList blackList){
        return new JsonBean(200,"success",blackListService.updateById(blackList));
    }
    @RequestMapping("deleteBlackList")
    public JsonBean deleteBlackList(@RequestParam(value = "ids") List<Integer> ids){
        return new JsonBean(200,"success",blackListService.removeByIds(ids));
    }
    @RequestMapping("getBlackListTypes")
    public List<String> getBlackListTypes(){
        return blackListService.getBlackListTypes();
    }
}
