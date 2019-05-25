package com.xieyu.attachment.entity;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 分页信息
 *
 * @author 谢宇
 * Date: 2019/4/11 20:53
 */
@ApiModel("分页信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码，从1开始
     */
    @ApiModelProperty(value = "页码，从1开始", required = true)
    private int pageNum;
    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小", required = true)
    private int pageSize;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String orderBy;
    /**
     * 返回信息配置
     */
    @ApiModelProperty(value = "返回信息配置")
    private RowOpt rowOpt;


    /**
     * 当前页的数量
     */
    @ApiModelProperty(value = "当前页的数量", hidden = true)
    private int size;
    //当前页面第一个元素在数据库中的行号
    @ApiModelProperty(value = "当前页面第一个元素在数据库中的行号", hidden = true)
    private int startRow;
    //当前页面最后一个元素在数据库中的行号
    @ApiModelProperty(value = "当前页面最后一个元素在数据库中的行号", hidden = true)
    private int endRow;
    //总记录数
    @ApiModelProperty(value = "总记录数", hidden = true)
    private long total;
    //总页数
    @ApiModelProperty(value = "总页数", hidden = true)
    private int pages;
    //结果集
    @ApiModelProperty(value = "结果集", hidden = true)
    private List list;

    //第一页
    @ApiModelProperty(value = "第一页", hidden = true)
    private int firstPage;
    //前一页
    @ApiModelProperty(value = "前一页", hidden = true)
    private int prePage;
    //下一页
    @ApiModelProperty(value = "下一页", hidden = true)
    private int nextPage;
    //最后一页
    @ApiModelProperty(value = "最后一页", hidden = true)
    private int lastPage;

    //是否为第一页
    @ApiModelProperty(value = "是否为第一页", hidden = true)
    private boolean isFirstPage = false;
    //是否为最后一页
    @ApiModelProperty(value = "是否为最后一页", hidden = true)
    private boolean isLastPage = false;
    //是否有前一页
    @ApiModelProperty(value = "是否有前一页", hidden = true)
    private boolean hasPreviousPage = false;
    //是否有下一页
    @ApiModelProperty(value = "是否有下一页", hidden = true)
    private boolean hasNextPage = false;
    //导航页码数
    @ApiModelProperty(value = "导航页码数", hidden = true)
    private int navigatePages;
    //所有导航页号
    @ApiModelProperty(value = "所有导航页号", hidden = true)
    private int[] navigatepageNums;

    /**
     * 包装Page对象
     */
    public PageInfo(List list) {
        this(list, 8);
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public PageInfo(List list, int navigatePages) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.orderBy = page.getOrderBy();

            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
            this.total = page.getTotal();
            //由于结果是>startRow的，所以实际的需要+1
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                //计算实际的endRow（最后一页的时候特殊）
                this.endRow = this.startRow - 1 + this.size;
            }
        } else if (list != null) {
            this.pageNum = 1;
            this.pageSize = list.size();

            this.pages = 1;
            this.list = list;
            this.size = list.size();
            this.total = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }
        if (list != null) {
            this.navigatePages = navigatePages;
            //计算导航页
            calcNavigatepageNums();
            //计算前后页，第一页，最后一页
            calcPage();
            //判断页面边界
            judgePageBoudary();
        }
    }

    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            firstPage = navigatepageNums[0];
            lastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }
}
