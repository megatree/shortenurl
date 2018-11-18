package net.megatree.shortenurl.controller;

import lombok.extern.slf4j.Slf4j;
import net.megatree.shortenurl.exception.Asserts;
import net.megatree.shortenurl.model.ShortUrlLog;
import net.megatree.shortenurl.model.dto.OperateDTO;
import net.megatree.shortenurl.service.OperateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
