package site.lzlz.mainbody.entity.dataobject;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name="wx_message")
public class WxMessageDO {

    @Id
    private Integer id;
    private String message;
    @Column(name="is_template")
    private String isTemplate;
    @Column(name="is_deleted")
    private Boolean isDeleted;

}
