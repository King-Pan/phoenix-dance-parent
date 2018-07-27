package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.common.ServerResponse;
import club.javalearn.fastsystem.model.DataDict;
import club.javalearn.fastsystem.service.DataDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/23
 * Time: 下午5:00
 * Description: No Description
 */
@Slf4j
@RestController
@RequestMapping("/")
public class DataDictController {

    @Autowired
    private DataDictService dataDictService;

    @RequestMapping(value = "dataDict", method = RequestMethod.GET)
    public ModelAndView dataDictPage() {
        ModelAndView view = new ModelAndView("system/dataDict");
        System.out.println(dataDictService.findAllType());
        view.addObject("dataDictList", dataDictService.findAllType());
        return view;
    }


    @RequestMapping(value = "dataDicts/", method = RequestMethod.GET)
    public Object findAll() {
        return dataDictService.findAll();
    }


    @GetMapping("dataDict/{id}")
    public Object findById(@PathVariable("id") Long id) {
        ServerResponse<DataDict> serverResponse;
        try {
            serverResponse = ServerResponse.createBySuccess("获取字典成功", dataDictService.findById(id));
        } catch (Exception e) {
            log.error("获取字典失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("获取字典失败\n " + e.getMessage());
        }
        return serverResponse;
    }

}
