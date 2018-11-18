package net.megatree.shortenurl.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by mythss on 2018-11-18.
 */
@Data
public class ShortUrlLog {
    private Long id;
    private String srcUrl;
    private String dstUrl;
    private Timestamp insertAt;
    private Timestamp updateAt;
    private int deleted;
}
