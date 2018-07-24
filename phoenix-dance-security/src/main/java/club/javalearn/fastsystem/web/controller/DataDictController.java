package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.service.DataDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "dataDict",method = RequestMethod.GET)
    public ModelAndView dataDictPage(){
        ModelAndView view = new ModelAndView("system/dataDict");
        System.out.println(dataDictService.findAllType());
        view.addObject("dataDictList",dataDictService.findAllType());
        return view;
    }


    @RequestMapping(value = "dataDicts/",method = RequestMethod.GET)
    public Object findAll(){
        return dataDictService.findAll();
    }

}
