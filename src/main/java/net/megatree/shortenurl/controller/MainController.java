package net.megatree.shortenurl.controller;

import lombok.extern.slf4j.Slf4j;
import net.megatree.shortenurl.exception.Asserts;
import net.megatree.shortenurl.model.ShortUrlLog;
import net.megatree.shortenurl.model.dto.OperateDTO;
import net.megatree.shortenurl.model.vo.IndexPageVO;
import net.megatree.shortenurl.service.OperateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mythss on 2018-11-18.
 */

@Controller
@Slf4j
@RequestMapping("/a")
public class MainController {

    @Autowired
    OperateService operateService;

    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public String main(@ModelAttribute OperateDTO operateDTO, Model model, HttpServletRequest request) {
        //vo
        IndexPageVO vo = new IndexPageVO();

        //首次访问页面
        if (StringUtils.isBlank(operateDTO.getLongUrl())) {
            model.addAttribute("dto", new OperateDTO());
            model.addAttribute("vo", vo);
            return "index";
        }

        //拦截器已验证longUrl合法
        ShortUrlLog log = operateService.generateShortUrl(operateDTO);
        String shortUrl = request.getScheme() +
                "://" + request.getServerName() +
                ":" + request.getServerPort() +
                "/" + log.getDstUrl();

        vo.setLongUrl(log.getSrcUrl());
        vo.setShortUrl(shortUrl);
        vo.setEffectTime(log.getUpdateAt());

        model.addAttribute("dto", operateDTO);
        model.addAttribute("vo", vo);

        return "index";
    }


    @RequestMapping(value = "/ban", method = {RequestMethod.POST, RequestMethod.GET})
    public String ban() {
        return "ban";
    }

}
