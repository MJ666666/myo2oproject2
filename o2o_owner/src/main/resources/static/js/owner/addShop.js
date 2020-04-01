$(function () {

	function getAreas() {
		$.ajax({
			url : "/myo2oproject/area/getAreas",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {

                    console.log(data.areaList);
                    var areaData=data.areaList;
                    var areaHtml="<option value='0'>请选择商店所属区域</option>";
                    for (var i=0;i<areaData.length;i++){
                    	areaHtml+="<option value='"+areaData[i].areaId+"'>"+areaData[i].areaName+"</option>"
					}
                    $("#sel_area").html(areaHtml);

				}
			}
		});
	}
    function getShopCategorys() {
        $.ajax({
            url : "/myo2oproject/shopCategory/getShopParentCategoryList",
            type : "GET",
            success : function(data) {
                if (data.result.statusCode==1) {

                    console.log(data.shopCategoryList);
                    var shopCateData=data.shopCategoryList;
                    var shopCateHtml="<option value='0'>请选择商店所属分类</option>";
                    for (var i=0;i<shopCateData.length;i++){
                        shopCateHtml+="<option value='"+shopCateData[i].shopCategoryId+"'>"+shopCateData[i].shopCategoryName+"</option>"
                    }
                    $("#sel_category").html(shopCateHtml);

                }
            }
        });
    }
    function getShopSubCategorys() {
        $.ajax({
            url : "/myo2oproject/shopCategory/getShopCategoryList",
            type : "GET",
            success : function(data) {
                if (data.result.statusCode==1) {

                    console.log(data.shopCategoryList);
                    var shopCateData=data.shopCategoryList;
                    var shopCateHtml="<option value='0'>请选择商店所属分类</option>";
                    for (var i=0;i<shopCateData.length;i++){
                        shopCateHtml+="<option value='"+shopCateData[i].shopCategoryId+"'>"+shopCateData[i].shopCategoryName+"</option>"
                    }
                    $("#sel_subcategory").html(shopCateHtml);

                }
            }
        });
    }

    getAreas();
    getShopCategorys();
    getShopSubCategorys();

/*<option value="0">请选择商店所属分类</option>
        <option value="10">分类一级</option>
        <option value="101">&nbsp;&nbsp;├ 分类二级</option>
    <option value="102">&nbsp;&nbsp;├ 分类二级</option>
    <option value="20">分类一级</option>
        <option value="200">&nbsp;&nbsp;├ 分类二级</option>*/






});
