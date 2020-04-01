$(function () {

    /*
    	<li class="item">
				<div class="portfoliobox">
					<input class="checkbox" name="" type="checkbox" value="">
					<div class="picbox"><a href="temp/big/keting.jpg" data-lightbox="gallery" data-title="客厅1"><img src="temp/Thumb/keting.jpg"></a></div>
					<div class="textbox">客厅 </div>
				</div>
			</li>

     */
    function getImgList() {

        $.ajax(
            {
                url:"/myo2oproject/image/getImgList",
                method:"GET",
                success:function (data) {
                    if (data.result.statusCode==1) {
                        var imgList=data.imgList;
                        console.log(imgList);
                        handleList(imgList);
                    }

                }
            }
        )
    }




    function handleList(imgList) {
        var html="";
         imgList.map(function (item,index) {
             var imgPath="http://192.168.174.128"+item.imgAddr;
             html+='\t<li class="item">\n' +
                 '\t\t\t\t<div class="portfoliobox">\n' +
                 '\t\t\t\t\t<input class="checkbox" name="" type="checkbox" value="">\n' +
                 '\t\t\t\t\t<div class="picbox"><a href="'+imgPath+'" data-lightbox="gallery" data-title=图片"'+index+'"><img src="'+imgPath+'"></a></div>\n' +
                 '\t\t\t\t\t<div class="textbox">详情图 </div>\n' +
                 '\t\t\t\t</div>\n' +
                 '\t\t\t</li>'

         })






        $("#imgShowList").html(html)


    }




    getImgList();

});