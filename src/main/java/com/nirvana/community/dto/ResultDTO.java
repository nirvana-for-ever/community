package com.nirvana.community.dto;

import com.nirvana.community.enums.CustomizeErrorCode;
import lombok.Data;

/**
 * @program: community
 * @description: 自定义返回操作的结果，包括状态码及信息
 * @author: Nirvana
 * @create: 2019--12--24--14:01
 **/
@Data
public class ResultDTO {
    private Integer code;
    private String msg;

    public static ResultDTO getResult(Integer code,String msg){

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);

        return resultDTO;
    }

    public static ResultDTO getResult(CustomizeErrorCode customizeErrorCode){

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(customizeErrorCode.getCode());
        resultDTO.setMsg(customizeErrorCode.getMsg());

        return resultDTO;
    }

}
