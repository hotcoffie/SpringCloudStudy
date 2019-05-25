package com.xieyu.attachment.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/4/12 15:05
 */
//@ApiModel(value = "pageInfo", description = "列表字段信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "column", description = "字段配置信息")
public class ColumnOpt implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_STRING = "String";
    public static final String TYPE_DATE = "Date";
    public static final String TYPE_CODE = "Code";


    /**
     * 列名
     */
    @ApiModelProperty(value = "列名", required = true)
    private String name;
    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型:String(默认）Date日期 Code码表", required = true)
    private String type = "String";
    /**
     * 码表
     */
    @ApiModelProperty(value = "如果数据类型为Code，提供码表表名，会自动对字段进行转码,默认sys_code表", required = false)
    private String codeTable = "sys_code";
    /**
     * 码表翻译失败，1返回原值（默认），2返回空字符串
     */
    @ApiModelProperty(value = "如果码表翻译失败，1返回原值（默认），2返回空字符串", required = false)
    private String codeFail;
    /**
     * 日期格式化
     */
    @ApiModelProperty(value = "如果数据类型为Date，提供日期格式化，默认yyyy-MM-dd hh:mm:ss", required = false)
    private String format = RowOpt.TIME_FORMAT;

    /**
     * 生成一个码表翻译配置，默认翻译失败返回原值
     */
    public static ColumnOpt buildCodeOpt(String name, String codeTable) {
        ColumnOpt opt = new ColumnOpt();
        opt.setName(name);
        opt.setType(ColumnOpt.TYPE_CODE);
        opt.setCodeTable(codeTable);
        return opt;
    }

    /**
     * 生成一个Date字段格式化配置，默认使用yyyy-MM-dd hh:mm:ss
     */
    public static ColumnOpt buildDateOpt(String name) {
        return buildDateOpt(name, RowOpt.TIME_FORMAT);
    }

    /**
     * 生成一个Date字段格式化配置，指定格式化格式
     */
    public static ColumnOpt buildDateOpt(String name, String format) {
        ColumnOpt opt = new ColumnOpt();
        opt.setName(name);
        opt.setType(RowOpt.DATE_FORMAT);
        opt.setFormat(format);
        return opt;
    }

}
