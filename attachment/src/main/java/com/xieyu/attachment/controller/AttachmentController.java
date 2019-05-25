package com.xieyu.attachment.controller;

import com.xieyu.attachment.conf.exception.ApplicationException;
import com.xieyu.attachment.entity.Attachment;
import com.xieyu.attachment.entity.RestMessage;
import com.xieyu.attachment.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Description: 附件上传下载
 *
 * @author 谢宇
 * Date: 2019/4/30 11:52
 */
@Api(tags = {"附件上传下载"})
@Controller
@Slf4j
public class AttachmentController {

    private static final int RANGE_HAS_FROMPOS = 2;

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @ApiOperation(value = "上传单个附件")
    @PostMapping("/upload")
    @ResponseBody
    public RestMessage uploadHandler(@ApiParam("附件") @RequestParam MultipartFile file,
                                     @ApiParam("上传用户ID") @RequestParam String userId) {
        Attachment attachment = attachmentService.save(file, userId);
        return RestMessage.success(attachment);
    }

    @ApiOperation(value = "下载单个附件", notes = "支持断点续传")
    @GetMapping("/download/{fileId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadAttachment(@ApiParam("附件ID") @PathVariable("fileId") String fileId) throws IOException {
        return downloadHandler(fileId, false);
    }

    @ApiOperation(value = "展示附件", notes = "支持断点续传")
    @GetMapping("/show/{fileId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> showAttachment(@ApiParam("附件ID") @PathVariable("fileId") String fileId) throws IOException {
        return downloadHandler(fileId, true);
    }

    private ResponseEntity<InputStreamResource> downloadHandler(String fileId, boolean showOnBrower) throws IOException {
        //1.查询附件信息
        Attachment attachment = attachmentService.get(fileId);
        //2.加载对应文件
        Resource file = new FileSystemResource(attachment.getPath());
        if (!file.exists() || !file.isFile()) {
            throw new ApplicationException("指定附件不存在");
        }

        //3.设置header
        HttpHeaders headers = new HttpHeaders();
        //禁用缓存
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        //设置文件名
        String fileName = attachment.getFileName();
        if (!showOnBrower) {
            String fileNameEncode = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            headers.add("Content-Disposition", "attachment; filename=" + fileNameEncode);
        }

        //4.返回ResponseEntity对象
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(getContentTypeWithFileSuffix(fileName))
                .body(new InputStreamResource(file.getInputStream()));
    }


    /**
     * 根据文件名的文件后缀判断返回的连接类型
     *
     * @param fileName 文件名，带格式后缀
     * @return MediaType
     */
    private MediaType getContentTypeWithFileSuffix(String fileName) {
        if (endWith(fileName, "jpg", "jpeg", "tif", "bmp")) {
            return MediaType.IMAGE_JPEG;
        } else if (endWith(fileName, "gif")) {
            return MediaType.IMAGE_GIF;
        } else if (endWith(fileName, "png")) {
            return MediaType.IMAGE_PNG;
        } else if (endWith(fileName, "html", "htm")) {
            return MediaType.TEXT_HTML;
        } else if (endWith(fileName, "pdf")) {
            return MediaType.APPLICATION_PDF;
        } else {
            return MediaType.MULTIPART_FORM_DATA;
        }
    }

    /**
     * 判断文件名是否有指定后缀
     *
     * @param str     文件名
     * @param suffixs 后缀
     * @return 是否有指定后缀
     */
    private boolean endWith(String str, String... suffixs) {
        for (String suffix : suffixs) {
            if (str.endsWith("." + suffix.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
