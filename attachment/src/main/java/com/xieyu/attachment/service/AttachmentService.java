package com.xieyu.attachment.service;

import com.xieyu.attachment.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: 附件服务层接口

 * @author 谢宇
 * Date: 2019/04/11 04:07:59
 */

public interface AttachmentService {
    Attachment save(MultipartFile file,String userId);

    Attachment get(String fileId);
}