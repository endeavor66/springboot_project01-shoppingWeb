
function ClickCheckBox(element){
    var checkbox=document.getElementsByClassName("address-checkBox");
    console.log(checkbox);
    console.log(element);
        for(var i=0;i<checkbox.length;i++)  {
            if(checkbox[i]!==element){
                checkbox[i].checked=false;
                console.log(i);
                console.log(checkbox[i].checked);
            }
        }
}