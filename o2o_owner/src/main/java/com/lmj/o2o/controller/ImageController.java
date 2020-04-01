package com.lmj.o2o.controller;

import com.lmj.o2o.dto.ImageTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ImageService;
import com.lmj.o2o.utils.ImageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ImageController
 * Description:
 * date: 2020/3/8 10:11
 *
 * @author MJ
 */
@Controller
@RequestMapping("/image")
public class ImageController {



    @PostMapping("/uploadImage")
    @ResponseBody
    public Result uploadImage(MultipartFile[] files) {
        Result result=null;
        int length = files.length;
        if (length==0) {
            result=new Result(OperationEnum.NULL_PARAM);
            return result;
        }
        if (length > 9) {
            result=new Result(OperationEnum.TOO_MUCH_FILES);
            return result;
        }
        List<String> imgSavePaths=new ArrayList<String>();
        try {
            for (int i = 0; i < length; i++) {
                String imageSavePath = ImageUtil.uploadImage(files[i].getName(), files[i].getOriginalFilename(), files[i].getBytes());
                imgSavePaths.add(imageSavePath);
            }
            //保存图片后执行更新数据库操作



             result = new Result(OperationEnum.SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Autowired
    private ImageService imageService;

    @GetMapping("/getImgList")
    @ResponseBody
    public Map<String,Object> getProductImgListByProductId(HttpServletRequest request) {
        Long productId=(Long)request.getSession().getAttribute("currentProductId");
        Product product = new Product();
        product.setProductId(productId);
        Map<String,Object> map=new HashMap<>();
        if (product.getProductId()==null) {
            map.put("result",new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        ImageTO imgTO = imageService.getImgListByProductId(product);
        if (imgTO.getState()==1) {
            map.put("result",new Result(OperationEnum.SUCCESS));
            map.put("imgList",imgTO.getImgList());
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;
    }
}
