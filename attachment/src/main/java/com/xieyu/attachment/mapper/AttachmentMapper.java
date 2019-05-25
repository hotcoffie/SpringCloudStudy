package com.xieyu.attachment.mapper;

import com.xieyu.attachment.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description: 附件DAO接口

 * @author 谢宇
 * Date: 2019/04/11 04:07:59
 */

public interface AttachmentMapper {
	/** 附件列表查询 */
	List<Attachment> search(@Param("keyword") String keyword);

	/** 用ID查询附件详情 */
	Attachment findById(@Param("id") String id);

	/** 用MD5查询附件详情 */
	List<Attachment> findByMd5(@Param("md5") String md5);

	/** 新增一条附件 */
	int add(Attachment attachment);

	/** 更新一条附件 */
	int update(Attachment attachment);

	/** 按照ID删除一条附件 */
	int deleteById(@Param("id") String id);
}