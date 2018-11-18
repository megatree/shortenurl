package net.megatree.shortenurl.exception;

/**
 * Created by mythss on 2018-11-19.
 */
public class Asserts {

    public static void to(boolean rightCondition,String msg){
        if (!rightCondition){
            throw new ServiceException(msg);
        }
    }
}
