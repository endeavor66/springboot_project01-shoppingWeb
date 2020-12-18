window.onload=function (){
    update();
}

function isCheck(check) {
    update();
}

function update (){
    var table = document.getElementById("unPayedList");
    var totalPrice = document.getElementById("totalPrice");

    var curPrice = 0;
    var total = 0;
    for(var i = 1; i < table.rows.length; ++i){
        let id = "#selCheck"+i;
        if($(id).is(':checked')){
            id = '#totalPrice'+i;
            curPrice = parseFloat($(id).val());
            total = (total + curPrice);
        }
    }
    totalPrice.textContent = total.toFixed(1);
}
function ClickAllCheckBox(element){
    var checkBox= document.getElementsByClassName("checkBox");
    console.log(element);
    if(element.checked==true){
        for(var i=0;i<checkBox.length;i++)  {
            checkBox[i].checked = "checked";
        }
    }
    else{
        for(var i=0;i<checkBox.length;i++)  {
            checkBox[i].checked = false;
        }
    }
    update();
}
function addOne(element,price,totalPrice, number) {
    let id = '#stock' + number;
    let stock =  parseInt($(id).html());
    var element_var = document.getElementById(element);
    var totalPrice_var = document.getElementById(totalPrice);
    let curSelCount = parseInt(element_var.value) + 1;
    if(stock < curSelCount){
        alert("超过商品容量");
        return;
    }
    element_var.value = curSelCount;
    totalPrice_var.value=(parseFloat(price)* element_var.value).toFixed(1);
    update ();

}

function delOne(element,price,totalPrice){
    var element_var = document.getElementById(element);
    var totalPrice_var = document.getElementById(totalPrice);
    var num = parseInt(element_var.value);
    if(num==1){
        alert("操作失败！当前商品数量不能再减少！")
    }
    else{
        element_var.value = parseInt(element_var.value) - 1;
        totalPrice_var.value= (parseFloat(price) * element_var.value).toFixed(1);
        update ()
    }
}

function checkInput(number) {
    let id = '#stock' + number;
    let stock = parseInt($(id).text());
    id = '#count' + number;
    let inputNum = $(id).val();
    if(inputNum > stock){
        alert("超过商品容量");
        $(id).val(1);
    }
}

function deleteSel() {
    var checkedNum=$("input[name='ids']:checked").length;
    if(checkedNum==0){
        alert("请至少选择一个进行删除");
        return;
    }
    if(confirm("确认要将这些商品移出购物车吗？")){
        var orderList = new Array();
        $("input[name='ids']:checked").each(
                function () {
                    orderList.push($(this).val());
                }
        );
        $.ajax({
            type:"post",
            url:"/order/deleteOrder",
            data:{ids:orderList.toString()},
            success:function () {
                alert("删除成功");
                location.reload();
            },
            error:function () {
                alert("删除失败");
            }
        });
    }
}

function deleteAll() {
    if(confirm("确认要将这些商品移出购物车吗？")){
        var orderList = new Array();
        $("input[name='ids']").each(
                function () {
                    orderList.push($(this).val());
                }
        );
        $.ajax({
            type:"post",
            url:"/order/deleteOrder",
            data:{ids:orderList.toString()},
            success:function () {
                alert("删除成功");
                location.reload();
            },
            error:function () {
                alert("删除失败");
            }
        });
    }
}

function toPay() {
    var checkedNum=$("input[name='ids']:checked").length;
    if(checkedNum==0){
        alert("请至少选择一个");
        return;
    }
    if(confirm("确认购买这些商品吗？")){
        var table = document.getElementById("unPayedList");
        var orders = new Array();
        for(var i = 1; i < table.rows.length; ++i){
            let element_id = "selCheck" + i;
            let item = document.getElementById(element_id);
            if(item.checked){
                let order = new Object();   //待封装的对象
                let item_id = '#selCheck'+i;
                order['id']=$(item_id).val();
                item_id = '#count'+i;
                order['purchase']=$(item_id).val();
                item_id = '#totalPrice'+i;
                let tt = $(item_id);
                order['payment']=$(item_id).val();

                orders.push(order);
            }
        };
        var totalPrice = $("#totalPrice").text();
        $.ajax({
                type:"post",
                url:"/order/toPay",
                dataType: 'json',
                data: {orders:JSON.stringify(orders)},
                success:function (data) {
                    location.href="/order/toSettle?orderIds="+data.orderIds+"&totalPrice="+totalPrice;
                },
                error:function () {
                    alert("操作失败");
                }
        });
    }
}

