package site.lzlz.mainbody.entity.dataobject;

import lombok.Data;

@Data
public class WxKeyword {

  private Integer id;
  private Integer messageId;
  private String key;
  private Boolean isDeleted;

}
