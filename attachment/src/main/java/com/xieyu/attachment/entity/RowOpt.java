package com.xieyu.attachment.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 返回数据行的格式化/转码配置
 *
 * @author 谢宇
 * Date: 2019/4/12 15:05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "RowOpt", description = "返回信息配置")
public class RowOpt implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SHOW_ALL_COLUMES = "1";

    public static final String SHOW_ONLY_OPT = "2";
    /**
     * 码表翻译失败，使用原值
     */
    public static final String CODE_FAIL_USESCODE = "1";
    /**
     * 码表翻译失败，使用空字符
     */
    public static final String CODE_FAIL_USESPACE = "2";
    /**
     * 默认时间格式化格式
     */
    public static final String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * 默认日期格式化格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 是否显示Entity所有字段，1返回所有字段（默认），2只返回ColumnOpts中配置的字段
     */
    @ApiModelProperty(value = "1返回Entity所有字段（默认），2只返回ColumnOpts中配置的字段", required = false)
    private String showAllColumes = SHOW_ALL_COLUMES;
    /**
     * 码表翻译失败，1返回原值（默认），2返回空字符串
     */
    @ApiModelProperty(value = "如果码表翻译失败，1返回原值（默认），2返回空字符串", required = false)
    private String codeFail = CODE_FAIL_USESCODE;
    /**
     * 日期格式化
     */
    @ApiModelProperty(value = "如果数据类型为Date，提供日期格式化，yyyy-MM-dd hh:mm:ss（默认）", required = false)
    private String format = TIME_FORMAT;

    /**
     * 字段配置列表，针对每个字段的具体配置
     */
    @ApiModelProperty(value = "字段配置列表，针对每个字段的具体配置", required = true)
    private List<ColumnOpt> columnOpts = new ArrayList<>();

    public void addColumnOpt(ColumnOpt columnOpt){
        this.columnOpts.add(columnOpt);
    }

}
