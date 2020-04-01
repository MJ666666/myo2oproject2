$(function () {

    function getProductInfo() {
        $.ajax({
            url : "/myo2oproject/product/getProduct",
            type : "GET",
            success : function(data) {
                if (data.result.statusCode==1) {

                    console.log(data.product);
                    var productData=data.product;
                    $("#productName").html("<span>"+productData.productName+"</span>");
                    $("#productCategory").html("<span>"+productData.productCategory.productCategoryName+"</span>");

                }
            }
        });
    }

    getProductInfo();







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
