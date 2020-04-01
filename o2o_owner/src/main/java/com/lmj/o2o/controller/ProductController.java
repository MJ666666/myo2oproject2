package com.lmj.o2o.controller;


import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.ProductImg;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.enums.ShopStateEnum;
import com.lmj.o2o.exception.ProductExecuteException;
import com.lmj.o2o.service.ImageService;
import com.lmj.o2o.service.ProductService;
import com.lmj.o2o.utils.HttpSessionAttributeUtil;
import com.lmj.o2o.utils.ImageUtil;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * ClassName: productController
 * Description:
 * date: 2020/3/7 16:48
 *
 * @author MJ
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/7 17:04
     * @param: 从session中获取当前商铺id
     * @return:
     */
    @PostMapping("/getList")
    @ResponseBody
    public Map<String,Object> getproductByShopId(@RequestBody PageVO pageVO, HttpServletRequest request) {
        Result result;
        Map<String,Object> map=new HashMap<String,Object>();
        Long currentShopId =(Long) HttpSessionAttributeUtil.getAttribute(request,"currentShopId");
        pageVO.setShopId(currentShopId);
        PageUtil.convertPage(pageVO);
        if (currentShopId==null) {
            map.put("result", new Result(ShopStateEnum.NULL_SHOPID));
            return map;
        }
        Shop shop = new Shop();
        shop.setShopId(currentShopId);
        ProductTO productTO = productService.getProductByShopId(pageVO);
        if (productTO.getState()==1) {
            map=productTO.getMap();
        }
        result=new Result(productTO);
        map.put("result",result);
        return map;
    }


    @PostMapping("/addNewProduct")
    public ModelAndView addNewProduct(MultipartFile thumbnail, MultipartFile[] images, Product product, String validateCode, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String verifyCode = (String) request.getSession().getAttribute("validateCode");
        if (!verifyCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        /*
        要获取商店id，set到product里
        */
        Long currentShopId = (Long) HttpSessionAttributeUtil.getAttribute(request, "currentShopId");
        product.setShopId(currentShopId);
        int length = images.length;
        if (length==0 ||thumbnail==null) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        List<ProductImg> imgs=new ArrayList<ProductImg>();

        try {
            //存储缩略图
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String thumbnailImgPath = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            product.setImgAddr(thumbnailImgPath);
            Date date = new Date();
            //存储描述图
            for (int i = 0; i < length; i++) {
                ProductImg productImg = new ProductImg();
                if (images[i].getOriginalFilename()!="") {
                    String imageSavePath = ImageUtil.uploadImage(images[i].getName(), images[i].getOriginalFilename(), images[i].getBytes());
                    productImg.setImgAddr(imageSavePath);
                    productImg.setCreateTime(date);
                    imgs.add(productImg);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProductExecuteException("保存图片失败");
        }
        //  存储描述图到数据库
        ProductTO productTO = productService.addNewProduct(product, imgs);
        if (productTO.getState()==1) {
            modelAndView.setViewName("redirect:/productList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @Autowired
    private ImageService imageService;

    @PostMapping("/updateProduct")
    public ModelAndView updateProduct(MultipartFile thumbnail, MultipartFile[] images, Product product, String validateCode, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String verifyCode = (String) request.getSession().getAttribute("validateCode");
        if (!verifyCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        Long currentShopId = (Long)HttpSessionAttributeUtil.getAttribute(request, "currentShopId");
        product.setShopId(currentShopId);
        Long currentProductId=(Long)HttpSessionAttributeUtil.getAttribute(request,"currentProductId");
        product.setProductId(currentProductId);
        Date date = new Date();
        product.setLastEditTime(date);
        ProductTO productTO;
        //存储缩略图
        if (thumbnail !=null) {
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String thumbnailImgPath = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            product.setImgAddr(thumbnailImgPath);
        }
        //有图片
        if (images !=null) {
            int length = images.length;

            List<ProductImg> imgs=new ArrayList<ProductImg>();
            //存储描述图
            try {
                for (int i = 0; i < length; i++) {
                    if (!StringUtils.isEmpty(images[i].getOriginalFilename())) {
                        ProductImg productImg = new ProductImg();
                        String path = ImageUtil.uploadImage(images[i].getName(), images[i].getOriginalFilename(), images[i].getBytes());
                        productImg.setImgAddr(path);
                        productImg.setCreateTime(date);
                        productImg.setProductId(currentProductId);
                        imgs.add(productImg);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            productTO = productService.updateProduct(product, imgs);
        }else {
            productTO = productService.updateProduct(product, null);
        }

        if (productTO.getState()==1) {
            modelAndView.setViewName("redirect:/productList");
            return modelAndView;
        }else {
            modelAndView.setViewName("error");
            return modelAndView;
        }

    }

    @GetMapping("/changeStatus")
    public ModelAndView changeProductStatus(Product product, HttpServletRequest request) {
        Long currentShopId = (Long)request.getSession().getAttribute("currentShopId");
        product.setShopId(currentShopId);
        ModelAndView modelAndView = new ModelAndView();
        ProductTO productTO = productService.updateProductStatus(product);
        if (productTO.getState()==1) {
            modelAndView.setViewName("redirect:/productList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;

    }


    @GetMapping("/getProduct")
    @ResponseBody
    public Map<String, Object> getProductByProductId(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Long currentProductId = (Long) request.getSession().getAttribute("currentProductId");
        Product product = new Product();
        product.setProductId(currentProductId);
        ProductTO productTO = productService.getProductInfoById(product);
        if (productTO.getState()==1) {
            map.put("result",new Result(OperationEnum.SUCCESS));
            map.put("product",productTO.getProduct());
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;

    }
}
