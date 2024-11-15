package com.ezen.spring.handler;

import com.ezen.spring.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileRemoveHandler {
    private final String BASE_PATH = "D:\\_myProject\\_java\\_fileUpload\\";

    public boolean deleteFile(FileVO fvo) {
        File delFile = new File(BASE_PATH, fvo.getSaveDir());
        boolean isDel = false;
        String delete = fvo.getUuid()+"_"+fvo.getFileName();

        try{
            File deleteFile = new File(delFile, delete);
            log.info(">>>> deleteFile {}", deleteFile);
            if(deleteFile.exists()){
                isDel = deleteFile.delete();
            }
            if(fvo.getFileType()>0){
                String deleteThumb = fvo.getUuid()+"_th_"+fvo.getFileName();
                File deleteThumbFile = new File(delFile, deleteThumb);
                log.info(">>>> deleteThumbFile {}", deleteThumbFile);
                if (deleteThumbFile.exists()) {
                    deleteThumbFile.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDel;
    }
}
