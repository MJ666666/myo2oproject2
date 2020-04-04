package com.lmj.o2o.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: TbPermission
 * Description:
 * date: 2020/4/3 19:57
 *
 * @author MJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbPermission {

    private Long permissionId;
    private Long parentId;
    private String name;
    private String enname;
    private String url;
    private String description;
    private Date created;
    private Date updated;

}
