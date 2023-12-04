<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
</head>
<body>
    <div id="tmp">
        ddd
    </div>

    <button id="btn" >시작</button>

    <script>

$(document).ready(function(){

    $('#btn').click(function(){

        $.ajax({
            type:'POST',
            url: '/app/test',
            // dataType : 'json',
            data : {bno:684},
            success : function(result){
                console.log("result: " + result)

                let list = "";
                result.forEach(function(item) {
                    list += `
                        <h1>cno: ${'${item.cno}'}</h1>
                        <h1>comment: ${'${item.comment}'}</h1>
                        <h1>commenter: ${'${item.commenter}'}</h1>
                    `;
                })

                console.log("list: " + list)

                $('#tmp').html(list);
            },
            error   : function(){ alert("error 댓글 다 불러오기") }
        });
    })

})



    </script>

</body>
</html>
