package net.megatree.shortenurl.dao;

import net.megatree.shortenurl.model.ShortUrlLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by mythss on 2018-11-18.
 */
@Repository
public interface ShortUrlDao {

    ShortUrlLog selectShortUrlById(@Param("id") Long id);

    ShortUrlLog selectShortUrlByDstUrl(@Param("dst") String dst);

    int insertShortUrl(ShortUrlLog shortUrlLog);

    int updateShortUrlById(ShortUrlLog shortUrlLog);
}
