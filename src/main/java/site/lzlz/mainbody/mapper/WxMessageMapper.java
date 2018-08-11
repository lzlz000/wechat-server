package site.lzlz.mainbody.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import site.lzlz.mainbody.entity.dataobject.WxMessageDO;
import tk.mybatis.mapper.common.Mapper;
import site.lzlz.mainbody.entity.dataobject.WxKeyword;

@Repository
public interface WxMessageMapper extends Mapper<WxKeyword> {
    /**
     * 根据关键词获得消息
     */
    @Select("select v.id,v.message,v.isTemplate from wx_keyword k " +
            "left join wx_message v on k.messageId = v.id " +
            "where k.isDeleted=0 and v.isDeleted=0 and k.`key`=#{keyword}")
    WxMessageDO selectMessageByKey(@Param("keyword")String keyword);
}
