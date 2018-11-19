package net.megatree.shortenurl.model.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by wangzhe.bj on 2018-11-19.
 */
@Data
public class IndexPageVO {

    private String longUrl;
    private String shortUrl;
    private Timestamp effectTime;
    private String msg;
}
