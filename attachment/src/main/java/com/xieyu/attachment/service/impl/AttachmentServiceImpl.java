package com.xieyu.attachment.service.impl;

import com.xieyu.attachment.conf.exception.ApplicationException;
import com.xieyu.attachment.entity.Attachment;
import com.xieyu.attachment.mapper.AttachmentMapper;
import com.xieyu.attachment.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Description: 附件服务层实现
 *
 * @author 谢宇
 * Date: 2019/04/11 04:07:59
 */
@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {
    @Value("${attachment.path}")
    private String filePath;

    @Resource
    private AttachmentMapper attachmentMapper;

    @Override
    @Transactional
    public Attachment save(MultipartFile file, String userId) {
        //1.必填项校验
        if (file == null || file.isEmpty()) {
            throw new ApplicationException("附件上传失败，附件为空！");
        }
        if (userId == null || userId.isEmpty() || userId.trim().isEmpty()) {
            throw new ApplicationException("附件上传失败，操作用户ID为空！");
        }
        //2.获取文件信息
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty() || fileName.trim().isEmpty()) {
            throw new ApplicationException("附件上传失败，附件名为空！");
        }
        String fileId = UUID.randomUUID().toString().replace("-", "");
        String path = filePath + File.separator + fileId;
        String md5;
        //3.文件写入
        byte[] bytes;
        Attachment attachment;
        try {
            md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            List<Attachment> checkList = attachmentMapper.findByMd5(md5);
            if (checkList != null && !checkList.isEmpty()) {
                //已存在同md5的文件，则不保存文件只记录上传信息
                attachment = checkList.get(0);
                attachment.setId(fileId);
                attachment.setFileName(fileName);
                attachment.setCreatorId(userId);
            } else {
                //不存在同md5的文件，保存文件并记录上传信息
                File file2Save = new File(filePath, fileId);
                if (!file2Save.exists()) {
                    file2Save.createNewFile();
                }
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(file2Save));
                attachment = new Attachment();
                attachment.setId(fileId);
                attachment.setFileName(fileName);
                attachment.setFileSize(file.getSize());
                attachment.setPath(path);
                attachment.setMd5(md5);
                attachment.setCreatorId(userId);
            }

        } catch (IOException e) {
            throw new ApplicationException("附件上传失败！", e);
        }

        //4.数据库记录附件信息
        attachmentMapper.add(attachment);
        return attachment;
    }

    @Override
    public Attachment get(String fileId) {
        if (fileId == null || fileId.isEmpty() || fileId.trim().isEmpty()) {
            throw new ApplicationException("附件下载失败，附件ID有误！");
        }
        Attachment attachment = attachmentMapper.findById(fileId.substring(0, 32));
        if (attachment == null) {
            throw new ApplicationException("指定附件不存在");
        }

        return attachment;
    }
}