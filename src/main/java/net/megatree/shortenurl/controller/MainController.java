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
@RequestMapping("/opt")
public class MainController {

    @Autowired
    OperateService operateService;

    @PostMapping("/test")
    @ResponseBody
    public String test(@RequestBody OperateDTO operateDTO, HttpServletRequest request) {
        Asserts.to(StringUtils.isNotBlank(operateDTO.getLongUrl()), "长地址不能为空");
        ShortUrlLog log = operateService.generateShortUrl(operateDTO);
        //todo 根据信息渲染view

        return new StringBuilder().append(request.getScheme())
                .append("://").append(request.getServerName())
                .append(":").append(request.getServerPort())
                .append("/").append(log.getDstUrl()).toString();

    }

    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public String main(@ModelAttribute OperateDTO operateDTO, Model model, HttpServletRequest request) {
        IndexPageVO vo = new IndexPageVO();

        if (StringUtils.isBlank(operateDTO.getLongUrl())) {
            model.addAttribute("dto", new OperateDTO());
            model.addAttribute("vo", vo);
            return "index";
        }

        ShortUrlLog log = operateService.generateShortUrl(operateDTO);

        String shortUrl = new StringBuilder().append(request.getScheme())
                .append("://").append(request.getServerName())
                .append(":").append(request.getServerPort())
                .append("/").append(log.getDstUrl()).toString();

        vo.setLongUrl(log.getSrcUrl());
        vo.setShortUrl(shortUrl);
        vo.setEffectTime(log.getUpdateAt());

        model.addAttribute("dto", operateDTO);
        model.addAttribute("vo", vo);

        return "index";
    }

    @PostMapping("/query")
    public String query(@ModelAttribute OperateDTO operateDTO, Model model, HttpServletRequest request) {
        Asserts.to(StringUtils.isNotBlank(operateDTO.getLongUrl()), "长地址不能为空");
        ShortUrlLog log = operateService.generateShortUrl(operateDTO);

        String shortUrl = new StringBuilder().append(request.getScheme())
                .append("://").append(request.getServerName())
                .append(":").append(request.getServerPort())
                .append("/").append(log.getDstUrl()).toString();

        model.addAttribute("dto", operateDTO);
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("time", log.getUpdateAt());
        return "index";
    }


}
