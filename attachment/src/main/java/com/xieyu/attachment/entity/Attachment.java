package com.xieyu.attachment.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 附件

 * @author 谢宇
 * Date: 2019/04/11 04:07:59
 */
@Data
@ApiModel(value = "Attachment", description = "附件") 
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件ID", required = false)
    private String id;

    @ApiModelProperty(value = "文件名", required = false)
    private String fileName;

    @ApiModelProperty(value = "文件大小", required = false)
    private Long fileSize;

    @ApiModelProperty(value = "文件路径", required = false)
    private String path;

    @ApiModelProperty(value = "文件MD5码，文件", required = false)
    private String md5;

    @ApiModelProperty(value = "创建人", required = false)
    private String creatorId;

    @ApiModelProperty(value = "创建", required = false)
    private Date createTime;

    @ApiModelProperty(value = "更新", required = false)
    private Date updateTime;
}
