package net.megatree.shortenurl.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import net.megatree.shortenurl.dao.ShortUrlDao;
import net.megatree.shortenurl.exception.Asserts;
import net.megatree.shortenurl.model.ShortUrlLog;
import net.megatree.shortenurl.model.dto.OperateDTO;
import net.megatree.shortenurl.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by mythss on 2018-11-18.
 */

@Service
@Slf4j
public class OperateService {

    @Autowired
    ShortUrlDao shortUrlDao;

    Cache<String, Long> cache = Caffeine.newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .maximumSize(1024)
            .build();

    /**
     * 生成短链接方法入口
     *
     * @param operateDTO
     * @return
     */
    public ShortUrlLog generateShortUrl(OperateDTO operateDTO) {
        String longUrl = operateDTO.getLongUrl();
        operateDTO.setLongUrl(longUrl.trim());
        ShortUrlLog log = null;
        String key = Encrypt.md5(longUrl);

        Long id = cache.getIfPresent(key);
        if (id == null) {
            //缓存中不存在
            log = this.addNewShortenUrl(operateDTO);
            cache.put(key, log.getId());
            return log;
        }

        //缓存存在，延长时间
        log = shortUrlDao.selectShortUrlById(id);
        cache.put(key, id);
        return log;

    }

    /**
     * 根据短链接获取原链接
     *
     * @param shortUrl
     * @return
     */
    public String getLongUrlByShort(String shortUrl) {
        Long id = Encrypt._62_to_10(shortUrl);
        ShortUrlLog log = shortUrlDao.selectShortUrlById(id);
        Asserts.to(log != null, "该短链接已失效");
        return log.getSrcUrl();
    }


    /**
     * 增加一条记录
     *
     * @param operateDTO
     */
    public ShortUrlLog addNewShortenUrl(OperateDTO operateDTO) {
        ShortUrlLog model = new ShortUrlLog();
        model.setSrcUrl(operateDTO.getLongUrl());

        int count = shortUrlDao.insertShortUrl(model);
        Asserts.to(count > 0, "记录写入失败");

        model.setDstUrl(Encrypt._10_to_62(model.getId()));
        int upCount = shortUrlDao.updateShortUrlById(model);
        Asserts.to(upCount > 0, "短链接更新失败");

        ShortUrlLog newModel = shortUrlDao.selectShortUrlById(model.getId());
        return newModel;
    }

}
