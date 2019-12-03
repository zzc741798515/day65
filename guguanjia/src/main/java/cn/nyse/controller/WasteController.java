package cn.nyse.controller;

import cn.nyse.entity.Waste;
import cn.nyse.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/waste")
public class WasteController {

    @Autowired
    WasteService service;


    /**
     * 根据大类id查询所有的waste
     * @param selected
     * @return
     */
    @RequestMapping("selectWaste")
    public List<Waste> selectWaste( long selected ){
        Waste waste = new Waste();
        waste.setParentId(selected);
        return service.select(waste);
    }


}
