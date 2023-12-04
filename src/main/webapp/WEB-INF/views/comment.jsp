<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>commentTest</h2>

<%--<form action="" id="reg_comment" onsubmit="return false">--%>
<div>
  <input type="text" id="txt_comment" name="comment" placeholder="댓글 추가" >
  <button type="button" id="btn_comment_submit">등록</button>
</div>
<%--</form>--%>
<div id="list_comment">

  <%--  ajax로 댓글 가져와서 넣는 부분 --%>
  <div id="commentList"></div>
</div>
<script>

  let bno = ${param.bno};

  /* 댓글 다 불러오기 */
  let showList = function(bno) {
    $.ajax({
      type:'GET',       // 요청 메서드
      url: '/app/comments?bno='+bno,  // 요청 URI
      /*dataType : 'json',*/   //생력하면 기본값(json) 이 들어간다.// 전송받을 데이터의 타입
      /*data : JSON.stringify(person),*/ // 위에꺼 생략하면 변환이 필요 없다.  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
      success : function(result){
        //person2 = JSON.parse(result);  // 서버로부터 응답이 도착하면 호출될 함수
                                        // 위에 dataType 에서 변환 안했으니까 받을때도 필요 없음. 생략 가능.
        console.log("received="+result);       // result는 서버가 전송한 데이터

        console.log("toHtml: " + toHtml(result))
        $("#commentList").html(toHtml(result));
        // 배열로 들어온걸(result) 함수를 이용해서 ul에 넣어서 가져온다.
      },
      error   : function(){ alert("error 댓글 다 불러오기") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()
  }

  /* 댓글 다 불러오기 할때 쓰는 포멧 */
  let toHtml = function(comments) {
    let tmp2 ="";
    comments.forEach(function(comment) {
      let dt = new Date(comment.reg_date);
      let year=dt.getFullYear();
      let mon=dt.getMonth()+1;
      let date=dt.getDate();
      let hour=dt.getHours();
      let min =dt.getMinutes();
      let sec =dt.getSeconds();

      tmp2 +='<div class="list_item">';
      tmp2 +='  <div class="comment_detail">';
      tmp2 +='    <div class="item_head">';
      tmp2 +='      <span>'+comment.commenter+'</span>';
      tmp2 +='      <span>'+(year+"-"+mon+"-"+date+" "+hour+":"+min+":"+sec)+'</span>';
      tmp2 +='    </div>';
      tmp2 +='    <div class="item_body">';
      tmp2 +='      <input type="text" class="comment comment_title" name="comment_title" value="'+comment.comment+'" readonly />';
      tmp2 +='    </div>';
      tmp2 +='  </div>';
      tmp2 +='  <div data-cno='+comment.cno +' data-bno='+comment.bno +'>';
      tmp2 +='    <button type="button" class="btn_comment btn_comment_modify">수정</button>';

      tmp2 +='    <button type="button" class="btn_comment btn_comment_del">삭제</button>';

      tmp2 +='    <button type="button" class="btn_comment btn_comment_edit_submit displayNone">등록</button>';
      tmp2 +='    <button type="button" class="btn_comment btn_comment_edit_cancel displayNone">취소</button>';

      tmp2 +='  </div>';
      tmp2 +='</div>';
    })
    return tmp2;

  }

  $(document).ready(function(){
    showList(bno); // 일단 리스트 다 불러오기


/* 새 댓글 쓰기 */
    $(document).on('click','#btn_comment_submit',function(){
      console.log("새 댓글 쓰기");

      let comment = $('#txt_comment').val().trim(); // 입력한 댓글을 가지고 와서 ajax의 data에 보냄.

      if(comment=="") {
        alert("댓글 입력하세요");
        // $('input[name="comment"]').focus();
        $('#txt_comment').focus();
        return;
      }

      // alert("="+'/board/comments?bno='+bno+"=");
      $.ajax({
        type:'POST',       // 요청 메서드
        url: '/app/comments?bno='+bno,  // 요청 URI - Ex17_CommentController 의 맵핑 "/comments"가 받음
        headers : { "content-type": "application/json" }, // 요청 헤더
        //dataType : 'json', // 전송받을 데이터의 타입
        data : JSON.stringify({bno:bno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(result){
// console.log(result);
// alert("received="+result);       // result는 서버가 전송한 데이터

          $('#txt_comment').val(""); // 댓글 쓴 부분 빈칸으로 초기화 하기
          showList(bno); /* 컨트롤러에 보면 댓글 가져오는거랑 등록하는거랑 분리되어 있기 때문에 저 위에 만든 댓글 불러오는 showList() 를 한번 더 실행 시켜야함 */
        },
        error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
      });
    });

/* '삭제'버튼 - 댓글 하나 삭제 */
    $(document).on('click','.btn_del, .btn_comment_del',function(){
      let cno = $(this).parent().attr("data-cno"); // document.getElementById().dataset.cno
      let bno = $(this).parent().attr("data-bno");

      // alert("="+cno+","+bno+"=");
      console.log('/app/comments/'+cno+'?bno='+bno);

      $.ajax({
        type:'DELETE',
        url: '/app/comments/'+cno+'?bno='+bno,
        success : function(result){
          alert(result)
          showList(bno)
        },
        error   : function(){ alert("error 댓글 하나 삭제") } // 에러가 발생했을 때, 호출될 함수
      }); // $.ajax()
    });

/* '수정'버튼 - 댓글 수정 할 수 있게 활성화 */
    let currComment =""; // 댓글 수정 버튼 누르면 기존값 저장해놨다가, 취소 눌렀을때 원상복귀 시킬때 쓸 변수
    $(document).on('click','.btn_comment_modify',function(){
      console.log("수정 버튼")

      // 해당 댓글 수정 가능하도록 활성화 - 등록, 취소 버튼 나타남
      console.log("이거: " + $(this).prev().find('.comment'))
      let el = $(this).parent().prev().find('.comment');
      currComment = el.val(); // 나중에 취소 누르면 currComment 값으로 다시 복귀 시킬거임
      el.attr("readonly", false);
      el.addClass('active_comment_edit');
      el.select();

      $(this).parent().children('.btn_comment').toggleClass('displayNone');
    });

/* '취소'버튼 - 수정 취소 버튼 */
    $(document).on('click','.btn_comment_edit_cancel',function() {
        let el = $(this).parent().prev().find('.comment');
        el.attr("readonly", true);
        el.removeClass('active_comment_edit');
        el.val(currComment); // 원래 값으로 복원 시킴

        $(this).parent().children('.btn_comment').toggleClass('displayNone');
      });

/* '등록'버튼 - 댓글 수정 */
    $(document).on('click','.btn_comment_edit_submit',function() {

      let cno = $(this).parent().attr("data-cno");
      let bno = $(this).parent().attr("data-bno");
      let el = $(this).parent().prev().find('.comment');
      let comment = el.val();


      console.log("cno: "+cno+", bno: "+bno+", comment: "+ comment);

      $.ajax({
        type:'PATCH',       // 요청 메서드
        url: '/app/comments/'+cno,  // 요청 URI - Ex17_CommentController 의 맵핑 "/comments/{cno}"가 받음
        headers : { "content-type": "application/json" }, // 요청 헤더
        //dataType : 'json', // 전송받을 데이터의 타입
        data : JSON.stringify({cno:cno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(result){
          alert("received="+result);       // result는 서버가 전송한 데이터
          showList(bno);
        },
        error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
      });
    });

  });

</script>
</body>
</html>