window.onload=function(){
    document.getElementById("zh").onclick = function () {
        this.style.backgroundColor="#00e58b";
        document.getElementById('xl').style.backgroundColor="#fff";
        document.getElementById('lll').style.backgroundColor="#fff";
        document.getElementById('scl').style.backgroundColor="#fff";
        document.getElementById('jg').style.backgroundColor="#fff";
        document.getElementById('xl').value="down";
        document.getElementById('lll').value="down";
        document.getElementById('scl').value="down";
        document.getElementById('jg').value="down";
        $('#xl').removeClass('up').addClass('down');
        $('#lll').removeClass('up').addClass('down');
        $('#scl').removeClass('up').addClass('down');
        $('#jg').removeClass('up').addClass('down');

        if (this.value === "down") {
            $('#zh').removeClass('down').addClass('up');
            this.value = "up";

        } else if (this.value === "up") {
            $('#zh').removeClass('up').addClass('down');
            this.value = "down";
        }
    }
    document.getElementById("xl").onclick = function () {
        this.style.backgroundColor="#00e58b";
        document.getElementById('zh').style.backgroundColor="#fff";
        document.getElementById('lll').style.backgroundColor="#fff";
        document.getElementById('scl').style.backgroundColor="#fff";
        document.getElementById('jg').style.backgroundColor="#fff";

        document.getElementById('zh').value="down";
        document.getElementById('lll').value="down";
        document.getElementById('scl').value="down";
        document.getElementById('jg').value="down";

        $('#zh').removeClass('up').addClass('down');
        $('#lll').removeClass('up').addClass('down');
        $('#scl').removeClass('up').addClass('down');
        $('#jg').removeClass('up').addClass('down');


        if (this.value === "down") {
            $('#xl').removeClass('down').addClass('up');
            this.value = "up";

        } else if (this.value === "up") {
            $('#xl').removeClass('up').addClass('down');
            this.value = "down";
        }
    }
    document.getElementById("lll").onclick = function () {
        this.style.backgroundColor="#00e58b";
        document.getElementById('xl').style.backgroundColor="#fff";
        document.getElementById('zh').style.backgroundColor="#fff";
        document.getElementById('scl').style.backgroundColor="#fff";
        document.getElementById('jg').style.backgroundColor="#fff";

        document.getElementById('xl').value="down";
        document.getElementById('zh').value="down";
        document.getElementById('scl').value="down";
        document.getElementById('jg').value="down";

        $('#xl').removeClass('up').addClass('down');
        $('#zh').removeClass('up').addClass('down');
        $('#scl').removeClass('up').addClass('down');
        $('#jg').removeClass('up').addClass('down');

        if (this.value === "down") {
            $('#lll').removeClass('down').addClass('up');
            this.value = "up";

        } else if (this.value === "up") {
            $('#lll').removeClass('up').addClass('down');
            this.value = "down";
        }
    }
    document.getElementById("scl").onclick = function () {
        this.style.backgroundColor="#00e58b";
        document.getElementById('xl').style.backgroundColor="#fff";
        document.getElementById('lll').style.backgroundColor="#fff";
        document.getElementById('zh').style.backgroundColor="#fff";
        document.getElementById('jg').style.backgroundColor="#fff";

        document.getElementById('xl').value="down";
        document.getElementById('lll').value="down";
        document.getElementById('zh').value="down";
        document.getElementById('jg').value="down";

        $('#xl').removeClass('up').addClass('down');
        $('#lll').removeClass('up').addClass('down');
        $('#zh').removeClass('up').addClass('down');
        $('#jg').removeClass('up').addClass('down');

        if (this.value === "down") {
            $('#scl').removeClass('down').addClass('up');
            this.value = "up";

        } else if (this.value === "up") {
            $('#scl').removeClass('up').addClass('down');
            this.value = "down";
        }
    }
    document.getElementById("jg").onclick = function () {
        this.style.backgroundColor="#00e58b";
        document.getElementById('xl').style.backgroundColor="#fff";
        document.getElementById('lll').style.backgroundColor="#fff";
        document.getElementById('scl').style.backgroundColor="#fff";
        document.getElementById('zh').style.backgroundColor="#fff";

        document.getElementById('xl').value="down";
        document.getElementById('lll').value="down";
        document.getElementById('scl').value="down";
        document.getElementById('zh').value="down";

        $('#xl').removeClass('up').addClass('down');
        $('#lll').removeClass('up').addClass('down');
        $('#scl').removeClass('up').addClass('down');
        $('#zh').removeClass('up').addClass('down');

        if (this.value === "down") {
            $('#jg').removeClass('down').addClass('up');
            this.value = "up";

        } else if (this.value === "up") {
            $('#jg').removeClass('up').addClass('down');
            this.value = "down";
        }
    }
}

