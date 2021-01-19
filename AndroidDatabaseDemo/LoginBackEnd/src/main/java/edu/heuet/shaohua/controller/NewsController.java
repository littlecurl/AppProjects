package edu.heuet.shaohua.controller;

import edu.heuet.shaohua.dataobject.NewsDO;
import edu.heuet.shaohua.response.CommonReturnType;
import edu.heuet.shaohua.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {
    @Autowired
    NewsService newsService;

    @GetMapping("/detail/id")
    @ResponseBody
    public CommonReturnType detailById(@RequestParam(name = "id") long id) {
        NewsDO newsDO = newsService.selectDetailById(id);
        List<NewsDO> newsDOList = new ArrayList<>();
        newsDOList.add(newsDO);
        return CommonReturnType.create(newsDOList);
    }

    @GetMapping("/detail/title")
    @ResponseBody
    public CommonReturnType detailByTitle(@RequestParam(name = "title") String title) {
        List<NewsDO> newsDOList = newsService.selectDetailByTitle(title);
        return CommonReturnType.create(newsDOList);
    }

    @GetMapping("/list")
    @ResponseBody
    public CommonReturnType list() {
        List<NewsDO> newsDOList = newsService.selectAll();
        return CommonReturnType.create(newsDOList);
    }
}
