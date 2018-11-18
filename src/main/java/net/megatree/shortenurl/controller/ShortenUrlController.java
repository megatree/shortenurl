package net.megatree.shortenurl.controller;

import lombok.extern.slf4j.Slf4j;
import net.megatree.shortenurl.exception.Asserts;
import net.megatree.shortenurl.service.OperateService;
import net.megatree.shortenurl.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by mythss on 2018-11-19.
 */
@Controller
@Slf4j
public class ShortenUrlController {

    @Autowired
    OperateService operateService;


    @GetMapping(path = "/{path}")
    public String test(@PathVariable("path") String shortPath, HttpServletResponse httpServletResponse) {
        Asserts.to(Encrypt.isValid(shortPath), "路径不合法");

        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        String url = operateService.getLongUrlByShort(shortPath);
        return "redirect:"+url;

    }

}
