$(function () {


    /*
    	<div class="row cl" id="imageArea">
					<label class="form-label col-xs-4 col-sm-3">
						<span class="c-red">*</span>详情图片：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="file" name="images">
					</div>
					<div class="col-3">
					</div>
				</div>
    * */




    function getShopCategorys() {
        $.ajax({
            url : "/myo2oproject/productCategory/getList",
            type : "GET",
            success : function(data) {
                if (data.result.statusCode==1) {

                    console.log(data.productCategoryList);
                    var productCategoryListData=data.productCategoryList;
                    var productCategoryListHtml="<option value='0'>请选择商店所属分类</option>";
                    for (var i=0;i<productCategoryListData.length;i++){
                        productCategoryListHtml+="<option value='"+productCategoryListData[i].productCategoryId+"'>"+productCategoryListData[i].productCategoryName+"</option>"
                    }
                    $("#sel_category").html(productCategoryListHtml);

                }
            }
        });
    }


    getShopCategorys();







});
var count=8;
var currentObject=$("#imgSelect");
function addImageArea() {
    $(currentObject).attr("onclick","");
    var addHtml='<input type="file" name="images"  onclick="addImageArea()">';
    if (count>0) {
        $("#imgBoxs").append(addHtml)
        count--;
    }
    currentObject=$(currentObject).next();

}